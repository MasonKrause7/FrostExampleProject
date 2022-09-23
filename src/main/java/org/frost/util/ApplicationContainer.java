package org.frost.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.lang.annotation.*;

public class ApplicationContainer {


    /**
     * /called by the client main method
     * @param mainClass
     */
   public static void start(Class<?> mainClass) {
       PackageScanner packageScanner = new PackageScanner();

       Set<Class<?>> classSet = packageScanner.scanPath(mainClass);

       System.out.println("Working");

            for (Class<?> currentClass : classSet) {//for each class in class set
                Annotation[] classesAnnos = currentClass.getAnnotations(); //get the classes annotations
                if(classesAnnos.length == 0){//no annotation
                    //System.out.printf("Class %s is not annotated\n", currentClass.getName()); //could throw exception? Too many class annos?
                }
                else if(classesAnnos.length > 1 ){//if the class is marked with multiple annotations
                   // System.out.printf("Class %s has more than one annotation\n", currentClass.getName()); //throw exception? Too many class annos?
                }
                else{//this will be if the currentClass had one annotation -> @Component
                    System.out.printf("Class %s has the annotation %s\n", currentClass.getName(), classesAnnos[0].toString());

                    //since the class is a component, get its constructors methods and fields, check them for @inject annotation.
                    //getting the classes properties in an array of its respective type
                    Constructor[] constArr = currentClass.getConstructors();
                    Method[] methArr = currentClass.getMethods();
                    Field[] fieldArr = currentClass.getFields();

                    //check for @inject in constructors
                    for (Constructor c : constArr) { //checks each constructor in the currentClass for an annotation.
                        if(c.getAnnotations().length != 0){//if annotation is present!
                            Class<?>[] dependenciesToBeInjected = c.getParameterTypes();//get the parameter(s) of the constructor, only handling one param for now
                            for (Class clas : dependenciesToBeInjected   ) {
                                //get this dependencies constructor to check it for dependencies and then instantiate the obj
                                Constructor[] dependenciesConstArr = clas.getConstructors();
                                for (int i = 0; i < dependenciesConstArr.length; i++){
                                    System.out.printf("Constructor of %s depends on %s\n", c.getName() ,clas.getName() );//prints the name of the obj and the dependency
                                }
                            }



                        }
                    }

                }
            }





   }



}






