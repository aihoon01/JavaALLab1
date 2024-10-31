package org.exercises;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NIOFileCopier {

    public static void main(String[] args) throws IOException {
        //source and target file paths
        Path sourceFile = Path.of("source.txt");
        Path targetFile = Path.of("destination.txt");

        try (
        //Open Source file channel in READ mode
        FileChannel sourceChannel = FileChannel.open(sourceFile, StandardOpenOption.READ);

         //Open target file channel in write mode or create file first if it doesn't exist
        FileChannel targetChannel = FileChannel.open(targetFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        ) {


            //Allocate a byte buffer to transfer data
            ByteBuffer buffer = ByteBuffer.allocate(2024);
            int bytesRead ;


            //Read from the source and write to the target file
            while ((bytesRead = sourceChannel.read(buffer)) > 0) {

                //Flip the buffer to switch from read mode to write mode
                buffer.flip();

                //Write the data to the target file
                targetChannel.write(buffer);

                //Clear the buffer for the next read/write cycle
                buffer.clear();
            }
            System.out.println("File copied successfulluy");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
