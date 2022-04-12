package com.example.todo_task_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDataBase.db";
    public static final String TASKS_TABLE = "TASKS_TABLE";
    public static final String COLUMN_TASK_CATEGORY = "TASK_CATEGORY";
    public static final String COLUMN_TASK_NAME = "TASK_NAME";
    public static final String COLUMN_TASK_STATUS = "TASK_STATUS";
    public static final String COLUMN_TASK_URGENCY = "TASK_URGENCY";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableString = "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TASK_CATEGORY + " TEXT NOT NULL, " + COLUMN_TASK_NAME + " TEXT UNIQUE, " + COLUMN_TASK_STATUS + " BOOL, " + COLUMN_TASK_URGENCY + " BOOL)";
        sqLiteDatabase.execSQL(createTableString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /****************************************************************
    FunctionName    : insertData
    Description     : Inserts tasks data into database
    InputParameters : TaskModel
    Return          : boolean
    *******************************************************************/

    public boolean insertData(TaskModel taskModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK_NAME, taskModel.getTaskName());
        cv.put(COLUMN_TASK_CATEGORY, taskModel.getTaskCategory());
        cv.put(COLUMN_TASK_STATUS, taskModel.isTaskStatus());
        cv.put(COLUMN_TASK_URGENCY, taskModel.isTaskUrgency());
        long insertStatus;
        try{
            insertStatus = sqLiteDatabase.insertOrThrow(TASKS_TABLE, null, cv);
        }
        catch(Exception e){
            System.out.println("EXCEPTION FROM INSERT TO DB: " + e.toString());
            insertStatus = -1;
        }
        sqLiteDatabase.close();
        return insertStatus != -1;
    }


    /****************************************************************
    FunctionName    : getAllTasksData
    Description     : Get all data from the tasks table
    InputParameters :
    Return          : List<TaskModel>
    ********************************************************************/

    public List<TaskModel> getAllTasksData(){
        List<TaskModel> returnList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TASKS_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String taskCategory = cursor.getString(2);
                boolean taskStatus = cursor.getInt(3) == 1;
                boolean taskUrgency = cursor.getInt(4) == 1;
                TaskModel taskModel = new TaskModel(id, taskName, taskCategory, taskStatus, taskUrgency);
                returnList.add(taskModel);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }


    /****************************************************************
     FunctionName    : getTaskCategories
     Description     : Returns task categories from data base
     InputParameters :
     Return          : List<TaskCategory>
     ********************************************************************/

    public List<TaskCategory> getTaskCategories(){
        List<TaskCategory> taskCategoryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String queryString = String.format("SELECT DISTINCT %s FROM %s", COLUMN_TASK_CATEGORY, TASKS_TABLE);
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do{
                taskCategoryList.add(new TaskCategory(cursor.getString(0)));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return taskCategoryList;
    }
}
