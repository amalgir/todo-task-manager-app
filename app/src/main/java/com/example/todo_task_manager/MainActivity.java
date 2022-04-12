package com.example.todo_task_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskCategory> taskCategoryList;
    private RecyclerView recyclerView;
    public static boolean newTaskMode = false;
    public static String currentCategoryString = "";
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.MainToolbar);
        setSupportActionBar(toolbar);
        dataBaseHelper = new DataBaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView1);
        taskCategoryList = new ArrayList<>();

        setTaskCategoryInfo();
        setAdaptor();
        dragAndDropFunctionality();

    }

    private void setAdaptor() {
        recyclerAdapter adapter = new recyclerAdapter(taskCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskCategoryInfo(){
        taskCategoryList = (ArrayList<TaskCategory>) dataBaseHelper.getTaskCategories();
    }

    private void dragAndDropFunctionality(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                Collections.swap(taskCategoryList, fromPosition, toPosition);
                dataBaseHelper.updateCategoryOrder(taskCategoryList);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void launchNewTaskActivity(View view){
        MainActivity.newTaskMode = true;
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void launchDisplayTaskActivity(View view){
        MainActivity.newTaskMode = false;
        currentCategoryString = ((TextView) view).getText().toString();
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }


}