package com.example.receitas.Util;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

public class MakeToast {

    public static void success(String text, Context c){
        TastyToast.makeText(c, text, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }

    public static void error(String text, Context c){
        TastyToast.makeText(c, text, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }

    public static void infor(String text, Context c){
        TastyToast.makeText(c, text, TastyToast.LENGTH_LONG, TastyToast.INFO);
    }

    public static void warning(String text, Context c){
        TastyToast.makeText(c, text, TastyToast.LENGTH_LONG, TastyToast.WARNING);
    }
}
