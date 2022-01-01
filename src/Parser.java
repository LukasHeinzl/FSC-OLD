package com.heiTech.main;

import java.util.List;

public class Parser
{
  public Parser() {}
  
  public static void startParse(java.io.File file) throws Exception {
    java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));
    String fileContent = "";String tempString = br.readLine();
    while (tempString != null) {
      fileContent = fileContent + tempString;
      tempString = br.readLine();
    }
    fileContent = fileContent + " ";
    
    String token = "";
    String string = "";
    String varName = "";
    String expr = "";
    String vvar = "";
    String read = "";
    
    int isExpr = 0;
    int commentState = 0;
    int isRead = 0;
    int isVPrint = 0;
    int stringState = 0;
    int isVar = 0;
    
    for (int i = 0; i < fileContent.length(); i++) {
      String currentChar = String.valueOf(fileContent.charAt(i));
      token = token + currentChar;
      if (token.equalsIgnoreCase(" ")) {
        if (isVar == 2) {
          Main.parsed.add("VAR:" + varName);
          isVar = 0;
          varName = "";
        }
        if (isVar == 1) {
          isVar++;
        }
        if (isVPrint == 2) {
          Main.parsed.add("VOUT:" + vvar);
          isVPrint = 0;
          vvar = "";
        }
        if (isVPrint == 1) {
          isVPrint++;
        }
        if (isRead == 2) {
          Main.parsed.add("IN:" + read);
          isRead = 0;
          read = "";
        }
        if (isRead == 1) {
          isRead++;
        }
        token = "";
      }
      if ((token.equalsIgnoreCase("\t")) && (stringState != 1)) {
        token = "";
      }
      if (token.equalsIgnoreCase("\"")) {
        if (stringState == 0) {
          stringState = 1;
        } else {
          Main.parsed.add("String:" + string);
          string = "";
          token = "";
          stringState = 0;
        }
      }
      if ((token.equalsIgnoreCase("#")) && (stringState != 1)) {
        if (commentState == 0) {
          commentState = 1;
        } else {
          commentState = 0;
          token = "";
        }
      }
      if ((stringState != 1) && (token.equalsIgnoreCase("$"))) {
        Main.parsed.add("VAR");
        isVar = 2;
        token = "";
      }
      if (commentState == 1) {
        token = "";
      }
      if (stringState == 1) {
        string = string + currentChar;
        token = "";
      }
      if ((token.equalsIgnoreCase("print")) && (stringState != 1)) {
        Main.parsed.add("print");
        token = "";
      }
      if ((token.equalsIgnoreCase("vprint")) && (stringState != 1)) {
        Main.parsed.add("vprint");
        token = "";
        isVPrint = 1;
      }
      if ((token.equalsIgnoreCase(";")) && (stringState != 1)) {
        if (isVar == 2) {
          isVar = 0;
          Main.parsed.add("VAR:" + varName);
          varName = "";
        }
        if (!expr.equalsIgnoreCase("")) {
          if (isExpr == 1) {
            Main.parsed.add("EXPR:" + expr);
            expr = "";
          } else {
            Main.parsed.add("NUM:" + expr);
            expr = "";
          }
          isExpr = 0;
        }
        if (isVPrint == 2) {
          Main.parsed.add("VOUT:" + vvar);
          isVPrint = 0;
          vvar = "";
        }
        if (isRead == 2) {
          Main.parsed.add("IN:" + read);
          isRead = 0;
          read = "";
        }
        Main.parsed.add("LineEnd");
        token = "";
      }
      if ((token.equalsIgnoreCase("!")) && (stringState != 1)) {
        Main.parsed.add("NOT");
        token = "";
      }
      if (isRead == 2) {
        read = read + token;
        token = "";
      }
      if ((token.equalsIgnoreCase("var")) && (stringState != 1)) {
        Main.parsed.add("VAR");
        isVar = 1;
        token = "";
      }
      if ((token.equalsIgnoreCase(":")) && (stringState != 1)) {
        if (isVar == 2) {
          isVar = 0;
          Main.parsed.add("VAR:" + varName);
          varName = "";
        }
        if (!expr.equalsIgnoreCase("")) {
          if (isExpr == 1) {
            Main.parsed.add("EXPR:" + expr);
            expr = "";
          } else {
            Main.parsed.add("NUM:" + expr);
            expr = "";
          }
          isExpr = 0;
        }
        Main.parsed.add("COLON");
        token = "";
      }
      if (isVar == 2) {
        varName = varName + token;
        token = "";
      }
      if (isVPrint == 2) {
        vvar = vvar + token;
        token = "";
      }
      if (token.equalsIgnoreCase("start")) {
        Main.parsed.add("START");
        token = "";
      }
      if (token.equalsIgnoreCase("end")) {
        Main.parsed.add("END");
        token = "";
      }
      if ((token.equalsIgnoreCase("<")) && (stringState != 1)) {
        Main.parsed.add("LESSTHAN");
        token = "";
      }
      if ((token.equalsIgnoreCase(">")) && (stringState != 1)) {
        Main.parsed.add("GREATERTHAN");
        token = "";
      }
      if ((token.equalsIgnoreCase("=")) && (stringState != 1)) {
        Main.parsed.add("EQUALS");
        token = "";
      }
      if ((stringState != 1) && ((token.equalsIgnoreCase(".")) || (token.equalsIgnoreCase("0")) || (token.equalsIgnoreCase("1")) || (token.equalsIgnoreCase("2")) || (token.equalsIgnoreCase("3")) || (token.equalsIgnoreCase("4")) || (token.equalsIgnoreCase("5")) || (token.equalsIgnoreCase("6")) || (token.equalsIgnoreCase("7")) || (token.equalsIgnoreCase("8")) || (token.equalsIgnoreCase("9")))) {
        expr = expr + token;
        token = "";
      }
      if ((stringState != 1) && ((token.equalsIgnoreCase("+")) || (token.equalsIgnoreCase("-")) || (token.equalsIgnoreCase("*")) || (token.equalsIgnoreCase("/")) || (token.equalsIgnoreCase("%")) || (token.equalsIgnoreCase("(")) || (token.equalsIgnoreCase(")")))) {
        isExpr = 1;
        expr = expr + token;
        token = "";
      }
      if ((token.equalsIgnoreCase("mrand")) && (stringState != 1)) {
        Main.parsed.add("MRAND");
        token = "";
      }
      if ((token.equalsIgnoreCase("mpi")) && (stringState != 1)) {
        Main.parsed.add("MPI");
        token = "";
      }
      if ((token.equalsIgnoreCase("read")) && (stringState != 1)) {
        Main.parsed.add("READ");
        token = "";
        isRead = 1;
      }
      if ((token.equalsIgnoreCase("WHILE")) && (stringState != 1)) {
        Main.parsed.add("WHILE");
        token = "";
      }
      if ((token.equalsIgnoreCase("FOR")) && (stringState != 1)) {
        Main.parsed.add("FOR");
        token = "";
      }
      if ((token.equalsIgnoreCase("IF")) && (stringState != 1)) {
        Main.parsed.add("IF");
        token = "";
      }
      if ((token.equalsIgnoreCase("ELSE")) && (stringState != 1)) {
        Main.parsed.add("ELSE");
        token = "";
      }
    }
    
    br.close();
  }
}
