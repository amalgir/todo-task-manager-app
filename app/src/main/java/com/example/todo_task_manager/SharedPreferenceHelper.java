package com.example.todo_task_manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    public static final String sharedPreferenceName = "ToDoSharedPref";
    public static final String mode = "mode";
    public static final String lastEditedCategory = "lastEditedCategory";


    /****************************************************************
     FunctionName    : editModeData
     Description     : To edit mode value in Shared Preference
     InputParameters : Context, String
     Return          :
     ********************************************************************/

    public static void editModeData(Context context, String modeValue){
        SharedPreferences sp = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(mode, modeValue);
        editor.apply();
    }


    /****************************************************************
     FunctionName    : getModeData
     Description     : To get mode value from Shared Preference
     InputParameters : Context
     Return          : String
     ********************************************************************/

    public static String getModeData(Context context){
        SharedPreferences sp = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        // DEFAULT MODE VALUE IS 'PERSONAL'
        return sp.getString(mode, Constants.PERSONAL);
    }


    /****************************************************************
     FunctionName    : editLastEditedCategory
     Description     : To edit last edited task category name value in Shared Preference
     InputParameters : Context, String
     Return          :
     ********************************************************************/

    public static void editLastEditedCategory(Context context, String categoryName){
        SharedPreferences sp = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(lastEditedCategory, categoryName);
        editor.apply();
    }


    /****************************************************************
     FunctionName    : getLastEditedCategory
     Description     : To get last edited task category name from shared preference
     InputParameters : Context
     Return          : String
     ********************************************************************/

    public static String getLastEditedCategory(Context context){
        SharedPreferences sp = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        return sp.getString(lastEditedCategory, "");
    }
}
