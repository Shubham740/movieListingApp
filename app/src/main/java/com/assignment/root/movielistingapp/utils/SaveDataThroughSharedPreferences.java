package com.assignment.root.movielistingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.assignment.root.movielistingapp.model.Results;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;

/**
 * Created by root on 25/7/17.
 */

public class SaveDataThroughSharedPreferences {
    private Context context;
   static SaveDataThroughSharedPreferences saveDataThroughSharedPreferences;

  private SaveDataThroughSharedPreferences(Context context)

    {
        this.context = context;
    }

public void savedSharedPreferences(LinkedList<Results> lastViewedLinkedList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.STORE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lastViewedLinkedList);
        editor.putString(Constants.SAVE_LIST, json);
        editor.apply();
    }

    public LinkedList<Results> getSharedPReferenceLinkedList()

    {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.STORE_FILE_NAME, Context.MODE_PRIVATE);
        String jsonSaved = sharedPref.getString(Constants.SAVE_LIST, null);
        Gson gson = new Gson();
      LinkedList<Results> linkedList= gson.fromJson(jsonSaved,new TypeToken<LinkedList<Results>>() {}.getType());
        if(linkedList==null)
        {
            linkedList=new LinkedList();
        }
        return linkedList;
    }

    public static SaveDataThroughSharedPreferences getNewInstance(Context context)

    {
        if(saveDataThroughSharedPreferences==null)
        {
            saveDataThroughSharedPreferences=new SaveDataThroughSharedPreferences(context);
        }
        return saveDataThroughSharedPreferences;


    }


}
