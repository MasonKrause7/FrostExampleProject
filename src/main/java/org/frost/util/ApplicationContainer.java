package org.frost.util;

import java.util.Set;

public class ApplicationContainer {



   public static void Start(Class<?> mainClass) {
       PackageScanner packageScanner = new PackageScanner();

       Set<Class<?>> classSet = packageScanner.scanPath(mainClass);
   }



}






