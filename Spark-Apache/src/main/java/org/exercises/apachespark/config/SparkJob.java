package org.exercises.apachespark.config;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Component;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Component
public class SparkJob {

    public JavaPairRDD<String, Integer> runSparkJob() throws IOException {
        SparkSession spark = SparkSession.builder()
                .appName("Word Count")
//                .master("spark://spark-master:7077")
                .master("local[*]")
                .config("spark.hadoop.home", "/opt/bitnami/hadoop") // Use this line to set Hadoop home
                .config("spark.filesystems.defaultFS", "file:///")
                .getOrCreate();

        //Define the input string
        String data = "Only God, Only he is the Greatest!";

        try(JavaSparkContext sc = new JavaSparkContext(spark.sparkContext())) {
            //Read the input file
            JavaRDD<String> input = sc.parallelize(Arrays.asList(data));

            //Perform the word count
            JavaPairRDD<String, Integer> counts = input
                    .flatMap(line -> Arrays.asList(line.toLowerCase().replaceAll("[^a-zA-Z]", "").split(" ")).iterator())
                    .mapToPair(word -> new Tuple2<>(word, 1))
                    .reduceByKey(Integer::sum);

            //Save the output to a text file
            System.out.println(counts.count());
            return counts;
        } finally {
            spark.stop();
        }

    }

//    private void createNewFile() throws IOException {
//        // Define the path to the file
//        Path filePath = Path.of("Spark-Apache/src/main/resources/test.txt");
//
//        // Ensure the directory exists, creating it if necessary
//        Files.createDirectories(filePath.getParent());  // Create directories if they don’t exist
//
//        // Create the file if it doesn’t exist
//        if (Files.notExists(filePath)) {
//            Files.createFile(filePath);
//            System.out.println(filePath + " file created.");
//        } else {
//            System.out.println(filePath + " file already exists.");
//        }
//
//        // Write data to the file
//        String data = "Only God, Only he is the Greatest!";
//        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.WRITE)) {
//            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
//            channel.write(buffer);
//            System.out.println("Data written to the file successfully.");
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to write data to file", e);
//        }
//    }
}
