package org.exercises;

import org.exercises.reflectionAndAnnotation.SampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ReflectionAndAnnotationApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ReflectionAndAnnotationApplication.class, args);

        //Get SampleService bean from the context
        SampleService service = context.getBean(SampleService.class);

        //call the serve method
        service.serve();
    }

}
