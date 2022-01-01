package com.heiTech.main;

import java.util.List;

public class Analyzer {
  public Analyzer() {}
  
  public static void startAnalyze() {
    int isWhile = 0;
    int startWhile = -1;
    int endWhile = -1;
    int isFor = 0;
    int startFor = -1;
    int endFor = -1;
    String forChange = "";
    String forVarName = "";
    double forVarValue = 0.0D;
    int isIf = 0;
    int ifElse = -1;
    int endIf = -1;
    int ifTrue = 0;
    try {
      int startCount = 0;int startPos = -1;
      int endCount = 0;int endPos = -1;
      for (int i = 0; i < Main.parsed.size(); i++) {
        if ((((String)Main.parsed.get(i)).equalsIgnoreCase("START")) && (((String)Main.parsed.get(i + 1)).equalsIgnoreCase("LineEnd"))) {
          startPos = i;
          startCount++;
        }
        if ((((String)Main.parsed.get(i)).equalsIgnoreCase("END")) && (((String)Main.parsed.get(i + 1)).equalsIgnoreCase("LineEnd"))) {
          endPos = i;
          endCount++;
        }
      }
      if ((startCount != 1) || (endCount != 1))
      {

        System.out.println("Multible or no definement of start; / end;");
        return;
      }
      if ((startPos != 0) || (endPos != Main.parsed.size() - 2))
      {

        System.out.println("start; / end; not at the beginning / end of file!");
        return;
      }
    } catch (Exception e) {
      System.out.println("Something went wrong while analyzing!");
      try
      {
        for (int i = 0; i < Main.parsed.size(); i++) {
          if ((((String)Main.parsed.get(i)).equalsIgnoreCase("READ")) && (((String)Main.parsed.get(i + 1)).contains("IN:")) && 
            (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LineEnd"))) {
            String varName = ((String)Main.parsed.get(i + 1)).substring(3);
            for (int j = 0; j < Main.vars.size(); j++) {
              if (((String)Main.vars.get(j)).contains(varName)) {
                java.util.Scanner s = new java.util.Scanner(System.in);
                String in = "";
                try {
                  in = s.nextLine();
                }
                catch (Exception localException1) {}
                
                Main.vars.set(j, varName + ":" + in);
              }
            }
          }
          
          if (((String)Main.parsed.get(i)).equalsIgnoreCase("print")) {
            if ((((String)Main.parsed.get(i + 1)).contains("String:")) && (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LineEnd"))) {
              String out = ((String)Main.parsed.get(i + 1)).substring(8);
              out = Functions.formateString(out);
              System.out.print(out);
            }
            if ((((String)Main.parsed.get(i + 1)).contains("EXPR:")) && (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LineEnd"))) {
              String out = ((String)Main.parsed.get(i + 1)).substring(5);
              out = Functions.calcExpr(out);
              System.out.print(out);
              i++;
            }
          }
          
          if ((((String)Main.parsed.get(i)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 1)).contains("VAR:"))) {
            if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LineEnd")) {
              String varName = ((String)Main.parsed.get(i + 1)).substring(4);
              Main.vars.add(varName);
            }
            else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("EQUALS")) {
              if ((((String)Main.parsed.get(i + 3)).contains("EXPR:")) && (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("LineEnd"))) {
                String varName = ((String)Main.parsed.get(i + 1)).substring(4);
                String varValue = ((String)Main.parsed.get(i + 3)).substring(5);
                varValue = Functions.calcExpr(varValue);
                Functions.addSetVaribale(varName, varValue);
              }
              if ((((String)Main.parsed.get(i + 3)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 4)).contains("VAR:"))) {
                String varName = ((String)Main.parsed.get(i + 4)).substring(4);
                String varValue = "";
                for (int j = 0; j < Main.vars.size(); j++) {
                  if (((String)Main.vars.get(j)).contains(varName)) {
                    varValue = ((String)Main.vars.get(j)).substring(varName.length() + 1);
                  }
                }
                if (((String)Main.parsed.get(i + 5)).equalsIgnoreCase("LineEnd")) {
                  Functions.addSetVaribale(((String)Main.parsed.get(i + 1)).substring(4), varValue);
                } else if ((((String)Main.parsed.get(i + 5)).contains("EXPR:")) && (((String)Main.parsed.get(i + 6)).equalsIgnoreCase("LineEnd"))) {
                  String expr = ((String)Main.parsed.get(i + 5)).substring(5);
                  String result = Functions.calcExpr(varValue + expr);
                  Functions.addSetVaribale(((String)Main.parsed.get(i + 1)).substring(4), result);
                } else if ((((String)Main.parsed.get(i + 5)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 6)).contains("VAR:")) && 
                  (((String)Main.parsed.get(i + 7)).contains("EXPR:")) && (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("LineEnd"))) {
                  String varName2 = ((String)Main.parsed.get(i + 6)).substring(4);
                  String varValue2 = "";
                  for (int j = 0; j < Main.vars.size(); j++) {
                    if (((String)Main.vars.get(j)).contains(varName2)) {
                      varValue2 = ((String)Main.vars.get(j)).substring(varName2.length() + 1);
                    }
                  }
                  String expr = ((String)Main.parsed.get(i + 7)).substring(5);
                  String result = Functions.calcExpr(varValue + expr + varValue2);
                  Functions.addSetVaribale(((String)Main.parsed.get(i + 1)).substring(4), result);
                }
              }
              


              if ((((String)Main.parsed.get(i + 3)).contains("NUM:")) && (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("LineEnd"))) {
                String varName = ((String)Main.parsed.get(i + 1)).substring(4);
                String varValue = ((String)Main.parsed.get(i + 3)).substring(4);
                Functions.addSetVaribale(varName, varValue);
              }
              if ((((String)Main.parsed.get(i + 3)).contains("String:")) && (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("LineEnd"))) {
                String varName = ((String)Main.parsed.get(i + 1)).substring(4);
                String varValue = ((String)Main.parsed.get(i + 3)).substring(8);
                Functions.addSetVaribale(varName, varValue);
              }
              if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("MRAND")) {
                String varName = ((String)Main.parsed.get(i + 1)).substring(4);
                java.util.Random rand = new java.util.Random();
                if ((((String)Main.parsed.get(i + 4)).contains("EXPR:")) && (((String)Main.parsed.get(i + 5)).equalsIgnoreCase("LineEnd"))) {
                  String expr = ((String)Main.parsed.get(i + 4)).substring(5);
                  int r = rand.nextInt((int)Math.pow(2.0D, 31.0D) - 1);
                  String result = Functions.calcExpr(r + expr);
                  Functions.addSetVaribale(varName, result);
                } else if (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("LineEnd")) {
                  Functions.addSetVaribale(varName, rand.nextInt((int)Math.pow(2.0D, 31.0D) - 1));
                }
              }
              if ((((String)Main.parsed.get(i + 3)).equalsIgnoreCase("MPI")) && (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("LineEnd"))) {
                String varName = ((String)Main.parsed.get(i + 1)).substring(4);
                Functions.addSetVaribale(varName, "3.141592");
              }
            }
          }
          
          if ((((String)Main.parsed.get(i)).equalsIgnoreCase("vprint")) && (((String)Main.parsed.get(i + 1)).contains("VOUT:")) && 
            (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LineEnd"))) {
            String varName = ((String)Main.parsed.get(i + 1)).substring(5);
            if (varName.equalsIgnoreCase("mpi")) {
              System.out.print(3.141592D);
            }
            if (varName.equalsIgnoreCase("mrand")) {
              java.util.Random rand = new java.util.Random();
              System.out.print(rand.nextInt());
            }
            for (int j = 0; j < Main.vars.size(); j++) {
              if (((String)Main.vars.get(j)).contains(varName)) {
                String out = ((String)Main.vars.get(j)).substring(varName.length() + 1);
                out = Functions.formateString(out);
                System.out.print(out);
              }
            }
          }
          
          if (((String)Main.parsed.get(i)).equalsIgnoreCase("WHILE")) {
            if ((isWhile == 1) && (endWhile != -1)) {
              i = startWhile;
            } else {
              startWhile = i;
            }
            endWhile = -1;
            for (int j = 0; j < Main.parsed.size(); j++) {
              if ((((String)Main.parsed.get(j)).equalsIgnoreCase("WHILE")) && (((String)Main.parsed.get(j + 1)).equalsIgnoreCase("LineEnd"))) {
                endWhile = j + 1;
              }
            }
            if (endWhile != -1) {
              if ((((String)Main.parsed.get(i + 1)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 2)).contains("VAR:"))) {
                String varName = ((String)Main.parsed.get(i + 2)).substring(4);
                double varValue = 0.0D;
                for (int j = 0; j < Main.vars.size(); j++) {
                  if (((String)Main.vars.get(j)).contains(varName)) {
                    varValue = Double.parseDouble(((String)Main.vars.get(j)).substring(varName.length() + 1));
                  }
                }
                if ((((String)Main.parsed.get(i + 4)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 5)).contains("VAR:")) && (((String)Main.parsed.get(i + 6)).equalsIgnoreCase("COLON"))) {
                  String varName2 = ((String)Main.parsed.get(i + 5)).substring(4);
                  double varValue2 = 0.0D;
                  for (int j = 0; j < Main.vars.size(); j++) {
                    if (((String)Main.vars.get(j)).contains(varName2)) {
                      varValue2 = Double.parseDouble(((String)Main.vars.get(j)).substring(varName2.length() + 1));
                    }
                  }
                  if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("EQUALS")) {
                    if (varValue == varValue2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("LESSTHAN")) {
                    if (varValue < varValue2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("GREATERTHAN")) {
                    if (varValue > varValue2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("NOT")) {
                    if (varValue != varValue2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  }
                } else if ((((String)Main.parsed.get(i + 4)).contains("NUM:")) && (((String)Main.parsed.get(i + 5)).equalsIgnoreCase("COLON"))) {
                  double num = Double.parseDouble(((String)Main.parsed.get(i + 4)).substring(4));
                  if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("EQUALS")) {
                    if (varValue == num) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("LESSTHAN")) {
                    if (varValue < num) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("GREATERTHAN")) {
                    if (varValue > num) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("NOT")) {
                    if (varValue != num) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  }
                }
              } else if (((String)Main.parsed.get(i + 1)).contains("NUM:")) {
                double num = Double.parseDouble(((String)Main.parsed.get(i + 1)).substring(4));
                if ((((String)Main.parsed.get(i + 3)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 4)).contains("VAR:")) && (((String)Main.parsed.get(i + 5)).equalsIgnoreCase("COLON"))) {
                  String varName = ((String)Main.parsed.get(i + 4)).substring(4);
                  double varValue = 0.0D;
                  for (int j = 0; j < Main.vars.size(); j++) {
                    if (((String)Main.vars.get(j)).contains(varName)) {
                      varValue = Double.parseDouble(((String)Main.vars.get(j)).substring(varName.length() + 1));
                    }
                  }
                  if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("EQUALS")) {
                    if (num == varValue) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LESSTHAN")) {
                    if (num < varValue) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("GREATERTHAN")) {
                    if (num > varValue) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("NOT")) {
                    if (num != varValue) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  }
                } else if ((((String)Main.parsed.get(i + 3)).contains("NUM:")) && (((String)Main.parsed.get(i + 4)).equalsIgnoreCase("COLON"))) {
                  double num2 = Double.parseDouble(((String)Main.parsed.get(i + 3)).substring(4));
                  if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("EQUALS")) {
                    if (num == num2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("LESSTHAN")) {
                    if (num < num2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("GREATERTHAN")) {
                    if (num > num2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  } else if (((String)Main.parsed.get(i + 2)).equalsIgnoreCase("NOT")) {
                    if (num != num2) {
                      isWhile = 1;
                    } else {
                      isWhile = 0;
                    }
                  }
                }
              }
              if (isWhile == 0) {
                i = endWhile;
              }
            } else {
              isWhile = 0;
            }
          }
          if (((String)Main.parsed.get(i)).equalsIgnoreCase("FOR")) {
            if ((isFor == 1) && (endFor != -1)) {
              i = startFor;
              forVarValue = Double.parseDouble(Functions.calcExpr(forVarValue + forChange));
              Functions.addSetVaribale(forVarName, forVarValue);
            } else {
              startFor = i;
            }
            endFor = -1;
            for (int j = 0; j < Main.parsed.size(); j++) {
              if ((((String)Main.parsed.get(j)).equalsIgnoreCase("FOR")) && (((String)Main.parsed.get(j + 1)).contains("EXPR:")) && (((String)Main.parsed.get(j + 2)).equalsIgnoreCase("LineEnd"))) {
                endFor = j + 2;
                forChange = ((String)Main.parsed.get(j + 1)).substring(5);
              }
            }
            if (endFor != -1) {
              if ((isFor == 0) && 
                (((String)Main.parsed.get(i + 1)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 2)).contains("VAR:"))) {
                forVarName = ((String)Main.parsed.get(i + 2)).substring(4);
                if ((((String)Main.parsed.get(i + 3)).equalsIgnoreCase("EQUALS")) && 
                  (((String)Main.parsed.get(i + 4)).contains("NUM:"))) {
                  double num = Double.parseDouble(((String)Main.parsed.get(i + 4)).substring(4));
                  Functions.addSetVaribale(forVarName, num);
                  forVarValue = num;
                }
              }
              

              if ((((String)Main.parsed.get(i + 6)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 7)).equalsIgnoreCase("VAR:" + forVarName))) {
                if (((String)Main.parsed.get(i + 9)).contains("NUM:")) {
                  double num = Double.parseDouble(((String)Main.parsed.get(i + 9)).substring(4));
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("EQUALS")) {
                    if (forVarValue == num) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("LESSTHAN")) {
                    if (forVarValue < num) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("GREATERTHAN")) {
                    if (forVarValue > num) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("NOT")) {
                    if (forVarValue != num) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                } else if ((((String)Main.parsed.get(i + 9)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 10)).contains("VAR:"))) {
                  String varName2 = ((String)Main.parsed.get(i + 10)).substring(4);
                  double varValue2 = 0.0D;
                  for (int j = 0; j < Main.vars.size(); j++) {
                    if (((String)Main.vars.get(j)).contains(varName2)) {
                      varValue2 = Double.parseDouble(((String)Main.vars.get(j)).substring(varName2.length() + 1));
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("EQUALS")) {
                    if (forVarValue == varValue2) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("LESSTHAN")) {
                    if (forVarValue < varValue2) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("GREATERTHAN")) {
                    if (forVarValue > varValue2) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                  if (((String)Main.parsed.get(i + 8)).equalsIgnoreCase("NOT")) {
                    if (forVarValue != varValue2) {
                      isFor = 1;
                    } else {
                      isFor = 0;
                    }
                  }
                }
                if (isFor == 0) {
                  i = endFor;
                }
                i += 2;
              }
            } else {
              isFor = 0;
            }
          }
          if (((String)Main.parsed.get(i)).equalsIgnoreCase("IF")) {
            ifTrue = 0;
            endIf = -1;
            ifElse = -1;
            for (int j = 0; j < Main.parsed.size(); j++) {
              if ((((String)Main.parsed.get(j)).equalsIgnoreCase("IF")) && (((String)Main.parsed.get(j + 1)).equalsIgnoreCase("LineEnd"))) {
                endIf = j + 1;
              }
              if ((((String)Main.parsed.get(j)).equalsIgnoreCase("ELSE")) && (((String)Main.parsed.get(j + 1)).equalsIgnoreCase("LineEnd"))) {
                ifElse = j + 1;
              }
            }
            if ((((String)Main.parsed.get(i + 1)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 2)).contains("VAR:"))) {
              String varName = ((String)Main.parsed.get(i + 2)).substring(4);
              double varValue = 0.0D;
              for (int j = 0; j < Main.vars.size(); j++) {
                if (((String)Main.vars.get(j)).contains(varName)) {
                  varValue = Double.parseDouble(((String)Main.vars.get(j)).substring(varName.length() + 1));
                }
              }
              if (((String)Main.parsed.get(i + 4)).contains("NUM:")) {
                double num = Double.parseDouble(((String)Main.parsed.get(i + 4)).substring(4));
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("EQUALS")) {
                  if (varValue == num) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("LESSTHAN")) {
                  if (varValue < num) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("GREATERTHAN")) {
                  if (varValue > num) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("NOT")) {
                  if (varValue != num) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
              } else if ((((String)Main.parsed.get(i + 4)).equalsIgnoreCase("VAR")) && (((String)Main.parsed.get(i + 5)).contains("VAR:"))) {
                String varName2 = ((String)Main.parsed.get(i + 5)).substring(4);
                double varValue2 = 0.0D;
                for (int j = 0; j < Main.vars.size(); j++) {
                  if (((String)Main.vars.get(j)).contains(varName2)) {
                    varValue2 = Double.parseDouble(((String)Main.vars.get(j)).substring(varName2.length() + 1));
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("EQUALS")) {
                  if (varValue == varValue2) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("LESSTHAN")) {
                  if (varValue < varValue2) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("GREATERTHAN")) {
                  if (varValue > varValue2) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
                if (((String)Main.parsed.get(i + 3)).equalsIgnoreCase("NOT")) {
                  if (varValue != varValue2) {
                    ifTrue = 1;
                  } else {
                    ifTrue = 0;
                  }
                }
              }
              if ((ifTrue == 0) && (ifElse != -1)) {
                i = ifElse;
              } else if ((ifTrue == 0) && (ifElse == -1)) {
                i = endIf;
              }
            }
          }
          if ((((String)Main.parsed.get(i)).equalsIgnoreCase("ELSE")) && (((String)Main.parsed.get(i + 1)).equalsIgnoreCase("LineEnd")) && (ifTrue == 1)) {
            i = endIf;
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
