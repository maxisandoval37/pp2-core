package service.discovery;

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
import entities.Scraper;
import entities.criteria.DiscoverCriteria;
import utils.ScraperUrlFinder;

@Slf4j
@NoArgsConstructor
public class ScraperDiscoverer {

    private static final String DIRECTORY_REGEX = "^[^\\s^\\x00-\\x1f\\\\?*:\"\";<>|/,][^\\x00-\\x1f\\\\?*:\"\";<>|/,]*[^,\\s^\\x00-\\x1f\\\\?*:\"\";<>|]+$";

    public Set<Scraper> discover(DiscoverCriteria criteria) throws FileNotFoundException, IllegalArgumentException {
        File directory = new File(criteria.getPath());

        if (!criteria.getPath().matches(DIRECTORY_REGEX)) {
            throw new IllegalArgumentException("Invalid location: " + criteria.getPath());
        }

        if (!directory.exists()) {
            throw new FileNotFoundException("Location does not exist: " + criteria.getPath());
        }

        return criteria.getSelectedShops() == null ? findClasses(criteria.getPath())
            : findSelectedShops(criteria.getPath(), criteria.getSelectedShops());
    }

    private Set<Scraper> findClasses(String path) {
        Set<Scraper> scrapers = new HashSet<>();
        findClassesInPath(new File(path), scrapers);
        return scrapers;
    }

    private Set<Scraper> findSelectedShops(String path, String[] scraperNames) {
        Set<Scraper> scrapers = new HashSet<>();
        findSelectedClassesInPath(new File(path), scrapers, scraperNames);
        return scrapers;
    }

    private void findClassesInPath(File path, Set<Scraper> scrapers) {
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

    private void findSelectedClassesInPath(File path, Set<Scraper> scrapers, String[] scraperNames) {
        if (!path.exists()) {
            return;
        }

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    findSelectedClassesInPath(file, scrapers, scraperNames);
                }
            }
        } else if (path.isFile() && endsWithJarExtension(path) && isScraperInNames(path, scraperNames)) {
            scrapers.addAll(findScrapersInJar(path));
        }
    }

    private boolean endsWithJarExtension(File file) {
        return file.getName().endsWith(".jar");
    }

    private boolean isScraperInNames(File file, String[] scraperNames) {
        String fileName = file.getName().replace(".jar", "");
        for (String name : scraperNames) {
            if (name.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private Set<Scraper> findScrapersInJar(File jarFile) {
        Set<Scraper> scrapers = new HashSet<>();

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

    private void instantiateClassFromJar(File jarFile, JarEntry entry, Set<Scraper> scrapers) {
        try {
            Class<?> cls = loadClassFromJar(jarFile, entry.getName());
            if (cls != null && Scraper.class.isAssignableFrom(cls)) {
                Scraper scraper = (Scraper) cls.getDeclaredConstructor().newInstance();
                scraper.setUrl(ScraperUrlFinder.findUrl(cls.getSimpleName()));

                scrapers.add(scraper);
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
