package org.frost.util;

import javax.security.auth.login.Configuration;
import javax.swing.plaf.IconUIResource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.sql.ParameterMetaData;
import java.util.*;

public class ComponentContainer {

    private Map<Class<?>, Object> mappedObjects = new HashMap<>();

    public Map mappedObjects() {
        return this.mappedObjects;
    }

    public void startApplication(Class mainClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ComponentScanner componentScanner = new ComponentScanner();
        Set<Class> classSet = componentScanner.pathScanner(mainClass);

        for (Class cl : classSet) {

                if(cl.isAnnotationPresent(MyAnnotation.class)) {
                    Constructor<?>[] constructors = cl.getConstructors();
                    for(Constructor constructor : constructors){
                        if(constructor.isAnnotationPresent(InjectHere.class)){
                            Class<?>[] parameters = constructor.getParameterTypes();
                            for(Class parameter : parameters){
                                Constructor<?> paramConst = parameter.getConstructor();
                                Object paramOB = paramConst.newInstance();
                                Object currentOb = constructor.newInstance(paramOB);
                                mappedObjects.put(cl,currentOb);

                            }
                        }
                    }


                }








        }


    }
}






