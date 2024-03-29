package com.javarush.task.task31.task3112;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        if (Files.notExists(downloadDirectory)) Files.createDirectories(downloadDirectory);

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Path tempFile = Files.createTempFile("temp",".tmp");
        String fileName = urlString.substring(urlString.lastIndexOf("/"));
        Path downloadFile = Paths.get(downloadDirectory.toString()+fileName);

        Files.copy(inputStream, tempFile,StandardCopyOption.REPLACE_EXISTING);
        return Files.move(tempFile,downloadFile, StandardCopyOption.REPLACE_EXISTING);

    }
}
