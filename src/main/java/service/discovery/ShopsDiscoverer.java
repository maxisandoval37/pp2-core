package service.discovery;

import entities.Shop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ShopsDiscoverer {

    private static final String DIRECTORY_REGEX = "^[^\\s^\\x00-\\x1f\\\\?*:\"\";<>|/,][^\\x00-\\x1f\\\\?*:\"\";<>|/,]*[^,\\s^\\x00-\\x1f\\\\?*:\"\";<>|]+$";

    public Set<Shop> discover(String path) throws FileNotFoundException, IllegalArgumentException {
        File directory = new File(path);

        if (!path.matches(DIRECTORY_REGEX)) {
            throw new IllegalArgumentException("Invalid location: " + path);
        }

        if (!directory.exists()) {
            throw new FileNotFoundException("Location does not exist: " + path);
        }

        return findClasses(path);
    }

    private Set<Shop> findClasses(String path) {
        Set<Shop> shops = new HashSet<>();
        findClassesInPath(new File(path), shops);
        return shops;
    }

    private void findClassesInPath(File path, Set<Shop> shops) {
        if (!path.exists()) {
            return;
        }

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    findClassesInPath(file, shops);
                }
            }
        } else if (path.isFile() && path.getName().endsWith(".jar")) {
            shops.addAll(findShopsInJar(path));
        }
    }

    private Set<Shop> findShopsInJar(File jarFile) {
        Set<Shop> shops = new HashSet<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    instantiateClassFromJar(jarFile, entry, shops);
                }
            }
        } catch (IOException e) {
            log.error("Error reading jar file: " + e.getMessage());
        }

        return shops;
    }

    private void instantiateClassFromJar(File jarFile, JarEntry entry, Set<Shop> shops) {
        try {
            Class<?> cls = loadClassFromJar(jarFile, entry.getName());
            if (cls != null && Shop.class.isAssignableFrom(cls)) {
                shops.add((Shop) cls.getDeclaredConstructor().newInstance());
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            log.error("Error instantiating class: " + e.getMessage());
        }
    }

    private Class<?> loadClassFromJar(File jarFile, String className) {
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{jarFile.toURI().toURL()});
            String canonicalClassName = className.replace("/", ".").replace(".class", "");
            return Class.forName(canonicalClassName, true, classLoader);
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error loading class from jar: " + e.getMessage());
            return null;
        }
    }
}
