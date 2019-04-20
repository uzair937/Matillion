package com.uzair.java;

import java.util.ArrayList;

public class Main {

    private static ArrayList<String> arg1 = new ArrayList<>(); //new arraylists to contain the parameters provided in split form
    private static ArrayList<String> arg2 = new ArrayList<>();

    public static void main(String[] args) {
        int diff = 0; //the difference is initialised as 0.
        if(args.length != 0) {
            String [] strs1 = args[0].split(""); //new string array for the split text
            String [] strs2 = args[1].split("");

            for (int i = 0; i < strs1.length; i++) {
                arg1.add(strs1[i]); //adds each split value into the arraylist and increments until it's complete
            }
            for (int j = 0; j < strs2.length; j++) {
                arg2.add(strs2[j]);
            }
            for (int k = 0; k < arg1.size() || k < arg2.size(); k++) {
                if (arg1.size() != arg2.size()) { //if one of the parameters is shorter than the other
                    if (arg1.size() > arg2.size()) {
                        diff = arg1.size() - arg2.size(); //if the first parameter is bigger
                    } else {
                        diff = arg2.size() - arg1.size(); //if the second parameter is bigger
                    }
                } else if (!arg1.get(k).equals(arg2.get(k))) {
                    diff++; //if any of the characters are not equal, the difference increases (incl case differences)
                }
            }
            System.out.println(diff); //prints the difference as a single figure number
        }
    }
}
