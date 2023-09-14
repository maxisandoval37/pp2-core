package service.discovery;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import shoppinator.core.interfaces.Scrapper;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import utils.FullyQualifiedNameFinder;

@Slf4j
public class ScrapperDiscoverer {

    public ScrapperDiscoverer() {
    }

    public Set<Scrapper> discover(String path) {
        //this.v2(path);
        return this.findClasses(path);
    }

    @SneakyThrows
    private Set<Scrapper> findClasses(String path) {
        System.getProperty("java.class.path");
        Set<Scrapper> result = new HashSet<>();
        for (File f : new File(path).listFiles()) {
            if(f.isDirectory()) {
                return findClasses(f.getPath());
            }
            if (!f.getName().endsWith(".class")) continue;

            Class<?> cls = null;
            try
            {
                String fileName = f.getName().substring(0, f.getName().lastIndexOf("."));
                URL[] urls = { new File(path).toURI().toURL() };
                ClassLoader urlClassLoader = new URLClassLoader(urls);
                cls = urlClassLoader.loadClass(fileName);
            }
            catch (ClassNotFoundException | MalformedURLException e)
            {
                log.error(e.getMessage());
            }

            if (!cls.isAssignableFrom(cls)) {
                throw new RuntimeException();
            }

            Scrapper scrapper;
            try {
                scrapper = (Scrapper) cls.newInstance();
                result.add(scrapper);
            } catch (InstantiationException e) {
                log.error(cls.getName() + " is not an implementation of Scrapper");
            }
        }

        return result;
    }

    @SneakyThrows
    public void v2(String path)  {
        //print classpath
        System.out.println(System.getProperty("java.class.path"));
            Class<?> cls = Class.forName("src.main.java.main.Example");
    }
}
