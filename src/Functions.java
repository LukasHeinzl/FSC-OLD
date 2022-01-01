package com.heiTech.main;

import java.util.List;

public class Functions {
  public Functions() {}
  
  static javax.script.ScriptEngineManager m = new javax.script.ScriptEngineManager();
  static javax.script.ScriptEngine engine = m.getEngineByName("js");
  
  public static String formateString(String in) {
    in = in.replace("$b", "\b");
    in = in.replace("$t", "\t");
    in = in.replace("$n", "\n");
    in = in.replace("$f", "\f");
    in = in.replace("$r", "\r");
    if ((in.contains("{")) && (in.contains("}"))) {
      int start = in.indexOf("{");
      int end = in.indexOf("}");
      String varName = in.substring(start + 1, end);
      String varValue = "ERROR";
      for (int i = 0; i < Main.vars.size(); i++) {
        if (((String)Main.vars.get(i)).contains(varName)) {
          varValue = ((String)Main.vars.get(i)).substring(varName.length() + 1);
        }
      }
      if (!varValue.equalsIgnoreCase("ERROR")) {
        in = in.replace("{" + varName + "}", varValue);
      }
    }
    return in;
  }
  
  public static boolean isRightFileExtension(String fileName) {
    int lastIndexOf = fileName.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return false;
    }
    if (fileName.substring(lastIndexOf + 1).equalsIgnoreCase("fsc")) {
      return true;
    }
    return false;
  }
  
  public static String calcExpr(String expr) {
    try {
      Object result = engine.eval(expr);
      return result.toString();
    } catch (Exception e) {}
    return "";
  }
  
  public static void addSetVaribale(String varName, String varValue)
  {
    int exist = 0;int existNum = 0;
    for (int j = 0; j < Main.vars.size(); j++) {
      if (((String)Main.vars.get(j)).contains(varName)) {
        exist = 1;
        existNum = j;
      }
    }
    if (exist == 1) {
      Main.vars.set(existNum, varName + ":" + varValue);
    } else {
      Main.vars.add(varName + ":" + varValue);
    }
  }
}
