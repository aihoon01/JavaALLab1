package org.exercises.reflectionAndAnnotation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.exercises.reflectionAndAnnotation.LogExecutionTime;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

//    @Around("execution(public * *(..))")
    @Around("@annotation(org.exercises.reflectionAndAnnotation.LogExecutionTime)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        try {
                //Log the initial(current) time before method execution
                Long start = System.currentTimeMillis();

                //Proceed to method execution
                Object result = joinPoint.proceed();

                //Log the final time after method execution
                Long end = System.currentTimeMillis();

                //Calculate execution time
                Long executionTime = (end - start)/1000;

                System.out.println("Execution method: " + method.getName().toUpperCase() + " in " + executionTime + "seconds");
                return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
