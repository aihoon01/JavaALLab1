package org.exercises.reflectionAndAnnotation;

import java.lang.reflect.Method;

public class LogExecutionTimeProcessor {

    public static void process(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();

        //Iterate over all Methods of the class
        for (Method method : clazz.getDeclaredMethods()) {
            //Check for @logExecutionTime annotation on method
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                //Log the initial(current) time before method execution
                long start = System.currentTimeMillis();

                //Execute the method
                method.invoke(obj);

                //Log the final time after method execution
                long end = System.currentTimeMillis();

                System.out.println("Execution time: " + (end - start)/1000 + "seconds");
            }
        }
    }

    public static void main(String[] args)  throws Exception {
        SampleService service = new SampleService();
        service.serve();
        LogExecutionTimeProcessor.process(service);
    }
}
