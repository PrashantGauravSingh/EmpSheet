package com.andtechno.singh.empsheet;

/**
 * Created by retisense on 27/07/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class sharePreference {


    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    int PRIVATE_MODE = 0;


    // Key

    String Half = "HALF";
    String One = "ONE";
    String MoreThanOne="MORETHANONE";

    private static final String PREF_NAME = "EMPLEAVE";

    public sharePreference(Context pContext) {
        // TODO Auto-generated constructor stub
        _context = pContext;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }


    public int getHalfLeave() {
        return pref.getInt(Half, 0);
    }

    public void setHalfLeave(int halfday) {
        editor = pref.edit();
        editor.putInt(Half, halfday);
        editor.commit();
    }

    public int getOneDay() {
        return pref.getInt(One,0);
    }

    public void setOneDay(int oneday) {
        editor = pref.edit();
        editor.putInt(One, oneday);
        editor.commit();
    }

    public int getMoreDay() {
        return pref.getInt(MoreThanOne,0);
    }

    public void setMoreDay(int moreDay) {
        editor = pref.edit();
        editor.putInt(MoreThanOne, moreDay);
        editor.commit();
    }



    public void clearPrefrence() {
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }




}
