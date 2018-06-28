package org.tyaa.ctfinder.common;

public class KeyGen {

	public static String text2KeyText(String in){
        
        //To translit
        String [][] trans = {
            {"Й", "J"}, {"Ц", "TS"}, {"У", "U"},  {"К", "K"},   {"Е", "E"},
            {"Н", "N"}, {"Г", "G"},  {"Ш", "SH"}, {"Щ", "SCH"}, {"З", "Z"},
            {"Х", "H"}, {"Ъ", ""},  {"Ф", "F"},  {"Ы", "Y"},   {"В", "V"},
            {"А", "A"}, {"П", "P"},  {"Р", "R"},  {"О", "O"},   {"Л", "L"},
            {"Д", "D"}, {"Ж", "ZH"}, {"Э", "E"},  {"Я", "YA"},  {"Ч", "CH"},
            {"С", "S"}, {"М", "M"},  {"И", "I"},  {"Т", "T"},   {"Ь", ""},
            {"Б", "B"}, {"Ю", "YU"},

            {"й", "j"}, {"ц", "ts"}, {"у", "u"},  {"к", "k"},   {"е", "e"},
            {"н", "n"}, {"г", "g"},  {"ш", "sh"}, {"щ", "sch"}, {"з", "z"},
            {"х", "h"}, {"ъ", ""},  {"ф", "f"},  {"ы", "y"},   {"в", "v"},
            {"а", "a"}, {"п", "p"},  {"р", "r"},  {"о", "o"},   {"л", "l"},
            {"д", "d"}, {"ж", "zh"}, {"э", "e"},  {"я", "ya"},  {"ч", "ch"},
            {"с", "s"}, {"м", "m"},  {"и", "i"},  {"т", "t"},   {"ь", ""},
            {"б", "b"}, {"ю", "yu"}
        };
        
        String out = "";
        if(in != null && in.length()>0){
            for(int i=0; i < in.length(); i++){
                String c = "" + in.charAt(i);
                for(int j=0; j < trans.length; j++){
                    if(c.equals(trans[j][0])){
                        c = trans[j][1];
                        break;
                    }
                }
                out += c;
            }
        }
        
        //To lowercase
        out = out.toLowerCase();
        
        //Remove some characters
        out = out.replaceAll("[^-a-z0-9]+", "_");
        out = out.replace("-", "");
        out = out.trim();
        
        //Prevent too long result
        if(out.length() > 25) {
        	
        	out = out.substring(0, 25);
        }
        
        return out;
    }
}
