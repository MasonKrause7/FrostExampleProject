package org.frost.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScanner {

    public Set<Class> pathScanner(Class mainClass) {
        String packagePath = mainClass.getPackageName();
        String loaderPath = mainClass.getPackageName().replaceAll("[.]","/");
        InputStream pathClasses = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(loaderPath);
        assert pathClasses != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(pathClasses));
        return reader.lines().filter(classString ->classString.endsWith(".class"))
                .map(classString -> getClass(classString,packagePath))
                .collect(Collectors.toSet());

    }

    private Class getClass(String classString, String packageName) {
            try {
                return Class.forName(packageName + "." + classString.substring(0,classString.lastIndexOf(".")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;

    }


}
