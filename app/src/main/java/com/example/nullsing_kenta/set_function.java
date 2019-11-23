package com.example.nullsing_kenta;

import java.io.*;
import java.lang.*;
import java.util.*;

public class set_function{
    String i,j;;
    //----- the function of intersection set -----
    static String[] intersect_set (String[] song1, String[] song2)
    {List<String> unionsong = new ArrayList<String>();

        for (String i : song1) {
            for (String j : song2){
                int comp_val=i.compareToIgnoreCase(j);
                boolean yes_or_no=(comp_val==0);
                if (yes_or_no==true)
                {
                    unionsong.add(i);
                }
            }

        }

        String[] itemsArray = new String[unionsong.size()];
        itemsArray = unionsong.toArray(itemsArray);
        return itemsArray;
    }

    /*
    public static void main(String []args){
        // get song list
        String[] inter_song;
        String[] songs1=  {"sakura","onepiece","iris","GTA","yesoryes"};
        String[] songs2= {"yesoryes","konan","SaKura","gta"};


        // test //
        System.out.println("====songs1 is as follows====  ");
        for (int i = 0;i <songs1.length ; i++) {
            System.out.println(songs1[i]);}

        System.out.println("===songs2 is as follows===  ");
        for (int i = 0;i <songs2.length ; i++) {
            System.out.println(songs2[i]);}

        inter_song=intersect_set(songs1,songs2);
        System.out.println("===intersected songs are as follows===");
        for (int i = 0;i <inter_song.length ; i++) {
            System.out.println(inter_song[i]);


        }
    }
     */
}