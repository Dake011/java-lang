package kz.edu.nu.cs.se;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyLanguageParser {
    StreamTokenizer inputTokenizer;

    private static final int SEMICOLON = ';';
    private static final int BRACKETS_OPENED = '(';
    private static final int BRACKET_CLOSED = ')';
    private static final int COMMA = ',';

    public MyLanguageParser(String s) {
        inputTokenizer = new StreamTokenizer(new StringReader(s));
    }

    public boolean program() {
        boolean isValidProgram = false;
        try {
            inputTokenizer.nextToken();
            while (isStatement(inputTokenizer)){
                if (inputTokenizer.ttype == StreamTokenizer.TT_EOF){
                    isValidProgram = true;
                }
            }
        } catch (IOException e){
            System.err.println(e);
        }

        return isValidProgram;
    }

    public int currentLevel = 0;
    public static StringBuilder outputHtml = new StringBuilder();

    public boolean isStatement(StreamTokenizer streamTokenizer) throws IOException {
        if(isExpression(streamTokenizer)) {
            if (streamTokenizer.ttype == SEMICOLON){
                outputHtml.append(";\n<br>");
                streamTokenizer.nextToken();
                return true;
            }
        }
        return false;
    }

    public boolean isExpression(StreamTokenizer streamTokenizer) throws  IOException {
        if(terminal(streamTokenizer) || isArray(streamTokenizer)){
            return true;
        }
        return false;
    }

    public boolean isArray(StreamTokenizer streamTokenizer) throws IOException{

        if (streamTokenizer.ttype == BRACKETS_OPENED) {
            outputHtml.append("(");
            outputHtml.append("<span class=\"lv"+currentLevel+"\">");
            currentLevel++;
            streamTokenizer.nextToken();
            while (isExpression(streamTokenizer)){
                if (streamTokenizer.ttype == COMMA){
                    streamTokenizer.nextToken();
                }
            }
            outputHtml.append("</span>");
            if (streamTokenizer.ttype == BRACKET_CLOSED){
                outputHtml.append(")");
                currentLevel--;
                streamTokenizer.nextToken();
                return true;
            }
        }
        return false;
    }

    public boolean terminal(StreamTokenizer streamTokenizer) throws IOException{
        if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER){
            outputHtml.append(streamTokenizer.nval);
            streamTokenizer.nextToken();
            return true;
        }
        if (streamTokenizer.ttype == StreamTokenizer.TT_WORD){
            outputHtml.append(streamTokenizer.sval);
            streamTokenizer.nextToken();
            return true;
        }

        return false;
    }

    public static void getHtml() {
        outputHtml.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head><body><div>");
        MyLanguageParser app = new MyLanguageParser(getInputText());
        app.program();
        outputHtml.append("<br></div></body></html>");
        System.out.println(outputHtml.toString());
        try {
            FileOutputStream out = new FileOutputStream("parsed.html");
            out.write(outputHtml.toString().getBytes());
            out.close();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public static void main(String[] args){
        getHtml();
    }

    public static String getInputText() {
        File file = new File("sampledefinition.txt");

        try {
            Stream<String> stream = Files.lines(file.toPath());
            return stream.collect(Collectors.joining("\n"));
        } catch (Exception e) {
            System.out.println("Error reading from file");
            return null;
        }
    }
}
