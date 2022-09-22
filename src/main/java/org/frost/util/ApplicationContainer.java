package org.frost.util;

import java.util.Set;

public class ApplicationContainer {


    /**
     * /called by the client main method
     * @param mainClass
     */
   public static void start(Class<?> mainClass) {
       PackageScanner packageScanner = new PackageScanner();

       Set<Class<?>> classSet = packageScanner.scanPath(mainClass);


   }



}






