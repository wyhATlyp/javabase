package com.wyh.javabase.io;

import java.io.*;

public class FileOperator {

    private static final String fileName = new File(".").getAbsolutePath().replaceAll("\\.", "") + "/test.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("fileName:" + fileName);
        write();
        read();
    }

    public static void write() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write("123");
        bw.newLine();
        bw.write("4567");
        bw.close();
    }

    public static void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String result = "";
        while((result = br.readLine()) != null) {
            System.out.println(result);
        }
        br.close();
    }

}
