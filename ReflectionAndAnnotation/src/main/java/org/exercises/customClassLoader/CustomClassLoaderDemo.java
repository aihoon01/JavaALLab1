package org.exercises.customClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoaderDemo {
    public static void main(String[] args) throws Exception {
        try {
            //Specify the directory containing the compiled .class files
            Path directory = Paths.get("C:/Users/StephenAihoon/Desktop/AdvanceLabs/Lab1/ReflectionAndAnnotation/target/classes/org/exercises/customClassLoader");

            //Load class dynamically
            CustomClassloader classloader = new CustomClassloader(directory.toString());
            Class<?> loadedClass = classloader.loadClass("org.exercises.customClassLoader.Data");

            //Create an instance using reflection
            Object instance = loadedClass.getDeclaredConstructor().newInstance();

            //Invoke a method via reflection
            Method method = loadedClass.getMethod("print");
            method.invoke(instance);
        }  catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Method not found: " + e.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Error creating instance: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // General exception fallback
        }
    }
}
