package cripto;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

public class FileHandling {

    //read a file
    public String readFile(String fileName) {
        String buffer = "";
        try {
            File fileToRead = new File(fileName);
            Scanner reader = new Scanner(fileToRead);
            StringBuilder readBuffer = new StringBuilder();
            while (reader.hasNextLine()) {
                readBuffer.append(reader.nextLine());
            }
            buffer = readBuffer.toString();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR!");
            e.printStackTrace();
        }
        return buffer;
    }

    //write a file
    public void writeFile(String fileName, String buffer) {
        try {
            FileWriter fileToWrite = new FileWriter(fileName);
            fileToWrite.write(buffer);
            fileToWrite.close();
        } catch (IOException e) {
            System.out.println("ERROR!");
            e.printStackTrace();
        }
    }

    public void printInfo(String param) {
        System.out.println(param);
    }


}
