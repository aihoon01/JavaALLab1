package org.exercises.apachespark;

import org.apache.spark.api.java.JavaPairRDD;
import org.exercises.apachespark.config.SparkJob;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvanceWordCount {
    private final SparkJob sparkJob;

    public AdvanceWordCount(SparkJob sparkJob) {
        this.sparkJob = sparkJob;
    }

    @GetMapping("/count")
    public String countWords() {
        System.out.println("Words about to be counted");
        try {
            JavaPairRDD<String, Integer> counts = sparkJob.runSparkJob();
//            sparkJob.runSparkJob();
            return "Word Counts successful" + counts.toString();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return "Error occurred while running Spark job: " + e.getMessage();
        }
    }
}