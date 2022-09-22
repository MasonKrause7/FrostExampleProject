package org.frost.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Scans all packages and sub-packages of the clients Main Class and returns a collection of classes to be
 * maintained by the Application Container.
 */
public class PackageScanner {

    /**
     * Scans all packages and sub-packages of the clients Main Class and returns a collection of classes to be
     * maintained by the Application Container.
     */
    public Set<Class<?>> scanPath(Class<?> mainClass) {
        /*
         * String representation of the classpath for the all the packages and subpackages of the main class.
         * Example: org.frost.util
         */
        String path = mainClass.getPackageName();
        /*
         * String formatted, so it can be passed as a parameter to the classloader.Replaces all "." with "/"
         * Example: (before) org.frost.util -> (after) org/frost/util
         *
         */
        String formattedPackagePath = path.replaceAll("[.]", "/");

        InputStream classStream = loadedClasses(formattedPackagePath);
        /*
         * Wraps classStream in order to be able to read lines at a time.
         */
        BufferedReader read = new BufferedReader(new InputStreamReader(classStream));

        return read.lines()
                /*
                 * filters lines by checking if stream ends with ".class"
                 */
                .filter(streamLine -> streamLine.endsWith(".class"))
                /*
                 * returns transformed stream of the return type applied by the method
                 */
                .map(streamLine -> convertToClass(streamLine,path))
                /*
                 * accumulates stream into a Set
                 */
                .collect(Collectors.toSet());


    }

    /**
     * @param path String value representing the path that should be scanned
     * @return stream of all resources in class path
     */
    private InputStream loadedClasses(String path) {
        InputStream classStream = ClassLoader.getSystemResourceAsStream(path);
        return classStream;
    }

    /*
     * Takes a name of the class as a string and the path for the class and returns a class type object.
     * uses Class.forName() which requires the fully qualified package name and class. Because path only provides the package name, the class name needs
     * to be appended to the path and the ".class" needs to be removed in order for class to be returned.
     * Example: path = org.frost.util      class name = MyClass.class  must be "org.frost.util.MyClasss" in order for Class.forName to return class type.
     *
     * @param clasName String representation of the class type to be converted to a class.
     * @param path     fully package path of the class
     * @return return class type
     */
    private Class<?> convertToClass(String clasName, String path){
        /*
         * gets the path appends a "."then appends the class all the way up to the "." which in not inclusive.
         *
         * Example: path = org.frost.util "." class name = MyClass.class
         *                                 ^(appended)            ^(append up to the ".";not included)
         *       fullyQulifiedName = org.frost.util.MyClass
         */
        String fullyQulifedName = path + "." + clasName.substring(0, clasName.lastIndexOf("."));
        Class classz = null;
        try {
            classz = Class.forName(fullyQulifedName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return classz;


    }


}
