package com.javarush.task.task19.task1918.GitSol1918.;


/*
Знакомство с тегами
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GitSolution {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String pathToFIle = bufferedReader.readLine();

        FileReader fileReader = new FileReader(pathToFIle);
        Scanner scanner = new Scanner(fileReader);

        StringBuilder stringBuilder = new StringBuilder();

        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next());
            stringBuilder.append(" ");
        }

        String res , answer;

        while (true) {
            res = substr(stringBuilder.toString(), args[0]);
            if (!res.equals("")) {
                answer = res.replaceAll("<\\s*" + args[0], "<" + args[0]);
                answer = answer.replaceAll("<\\s*\\/\\s*" + args[0] + "[^>]*>", "</" + args[0] + ">");
                System.out.println(answer);
                stringBuilder.delete(stringBuilder.indexOf(res), stringBuilder.indexOf(res) + res.length());
            }else break;
        }

        scanner.close();
        fileReader.close();
        bufferedReader.close();
    }

    public static String substr(String stringBuilder, String args) {
        Scanner scannerOfOpenTags = new Scanner(stringBuilder);
        Scanner scannerOfClosingTags = new Scanner(stringBuilder);
        String openingTag = scannerOfOpenTags.findInLine("<\\s*" + args + "[^>]*>");
        int openingTagIndx;
        String closingTag;
        int closingTagIndx;

        if (openingTag != null) {
            openingTagIndx = stringBuilder.indexOf(openingTag);
            closingTag = scannerOfClosingTags.findInLine("<\\s*\\/\\s*" + args + "[^>]*>");
            if (closingTag != null) {
                closingTagIndx = stringBuilder.indexOf(closingTag);
            } else closingTagIndx = stringBuilder.length();
        } else {
            return "";
        }


        int nextOpeningTagIndx = 0;
        String nextOpeningTag;
        int curIndx = openingTagIndx;

        do {

            nextOpeningTag = scannerOfOpenTags.findInLine("<\\s*" + args + "[^>]*>");
            if (nextOpeningTag != null) {
                nextOpeningTagIndx = stringBuilder.indexOf(nextOpeningTag, curIndx + 1);
                curIndx = nextOpeningTagIndx;
            } else nextOpeningTagIndx = stringBuilder.length();
        } while (nextOpeningTagIndx < closingTagIndx);  // для того чтобы понять чей закрывающий тэг



        String nextclosingTag = "";
        int nextclosingTagIndx = 0;
        int currClosingIndx = -1;

        do {
            if (currClosingIndx != -1) {
                closingTag = nextclosingTag;
                closingTagIndx = currClosingIndx;
            }
            nextclosingTag = scannerOfClosingTags.findInLine("<\\s*\\/\\s*" + args + "[^>]*>");
            if (nextclosingTag != null) {
                nextclosingTagIndx = stringBuilder.indexOf(nextclosingTag,currClosingIndx + 1);
            }else break;
            currClosingIndx = nextclosingTagIndx;
        } while (nextOpeningTagIndx > nextclosingTagIndx);

        scannerOfClosingTags.close();
        scannerOfOpenTags.close();

        return         stringBuilder.substring(openingTagIndx,closingTagIndx + closingTag.length());

    }
}