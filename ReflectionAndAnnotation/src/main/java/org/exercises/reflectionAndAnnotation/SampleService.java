package org.exercises.reflectionAndAnnotation;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @LogExecutionTime
    public void serve() {
        //Simulate method operation with sleep
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Service method executed");
    }
}
