package org.exercises;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NIOFileWriter {
    public static void main(String[] args) throws IOException {
        // File to write to
        Path filePath = Path.of("output.txt");

        // Data to be written into the file
        String data = "This is some sample data written to the file using NIO.";

        // Open the file channel in write mode (create the file if it doesn't exist)
        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            // Convert the string data to a byte buffer
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());

            // Write data from the buffer into the file
            channel.write(buffer);
            System.out.println("Data written to the file successfully.");
        }
    }
}
