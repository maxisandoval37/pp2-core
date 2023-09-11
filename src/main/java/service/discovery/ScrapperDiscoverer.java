package service.discovery;

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
        return this.findClasses(path);
    }

    @SneakyThrows
    private Set<Scrapper> findClasses(String path) {
        Set<Scrapper> result = new HashSet<>();
        for (File f : new File(path).listFiles()) {
            if(f.isDirectory()) {
                return findClasses(f.getPath());
            }
            if (!f.getName().endsWith(".class")) continue;

            String className = f.getName().substring(0, f.getName().lastIndexOf(".class"));
            String fullyQualifiedClassName = FullyQualifiedNameFinder.find(className, path);

            Class<?> cls = Class.forName(fullyQualifiedClassName);

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
}
