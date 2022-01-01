package com.heiTech.main;

import java.io.PrintStream;

public class Main {
  private static String fileDir;
  private static java.io.File file;
  
  public Main() {}
  
  public static java.util.List<String> parsed = new java.util.ArrayList();
  public static java.util.List<String> vars = new java.util.ArrayList();
  
  public static void main(String[] args) throws Exception {
    long startTime = System.currentTimeMillis();
    if (args.length < 1) {
      System.out.println("Received no file to interpret!");
    } else {
      fileDir = args[0];
      file = new java.io.File(fileDir);
      if (file.exists()) {
        if (Functions.isRightFileExtension(file.getName())) {
          Parser.startParse(file);
          Analyzer.startAnalyze();
        } else {
          System.out.println("File has wrong fileextension!");
        }
      } else {
        System.out.println("Received file does not exist!");
      }
    }
    long endTime = System.currentTimeMillis();
    long difference = ((endTime - startTime) * 0.001D);
    System.out.println("\n\nFinsished running. Took " + difference + " s to run.");
    System.out.println("Press any key to continue.");
    System.in.read();
    System.in.close();
  }
}
