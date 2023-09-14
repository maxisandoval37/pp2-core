package service.discovery;

import java.io.File;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.interfaces.Scrapper;

@Slf4j
@NoArgsConstructor
public class ScrapperDiscoverer {

    @SneakyThrows
    public Set<Scrapper> discover(String path) {
        return this.findClasses(path);
    }

    public Set<Scrapper> findClasses(String path) {
        Set<Scrapper> scrapers = new HashSet<>();
        findClassesInPath(new File(path), scrapers);
        return scrapers;
    }

    private void findClassesInPath(File path, Set<Scrapper> scrapers) {
        if (!path.exists()) {
            return;
        }

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    findClassesInPath(file, scrapers);
                }
            }
        } else if (path.isFile() && path.getName().endsWith(".jar")) {
            scrapers.addAll(findScrapersInJar(path));
        }
    }

    private Set<Scrapper> findScrapersInJar(File jarFile) {
        Set<Scrapper> scrapers = new HashSet<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    instantiateClassFromJar(jarFile, entry, scrapers);
                }
            }
        } catch (IOException e) {
            log.error("Error reading jar file: " + e.getMessage());
        }

        return scrapers;
    }

    private void instantiateClassFromJar(File jarFile, JarEntry entry, Set<Scrapper> scrapers) {
        try {
            Class<?> cls = loadClassFromJar(jarFile, entry.getName());
            if (cls != null && Scrapper.class.isAssignableFrom(cls)) {
                scrapers.add((Scrapper) cls.getDeclaredConstructor().newInstance());
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
