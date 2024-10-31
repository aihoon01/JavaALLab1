package org.exercises.customClassLoader;

import java.io.*;

public class CustomClassloader extends ClassLoader {
    private String directory;
    public CustomClassloader(String directory) {
        this.directory = directory;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = directory + name.replace('.', File.separatorChar) + ".class";
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] classBytes = inputStream.readAllBytes();
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }
}
