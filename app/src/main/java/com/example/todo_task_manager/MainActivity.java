package com.example.todo_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TaskCategory> taskCategoryList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView1);
        taskCategoryList = new ArrayList<>();

        setTaskCategoryInfo();
        setAdaptor();
    }

    private void setAdaptor() {
        recyclerAdapter adapter = new recyclerAdapter(taskCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskCategoryInfo(){
        // TODO Dummy data
        for( int i=0;i<20;i++){
            taskCategoryList.add(new TaskCategory("Dummy task: " + String.valueOf(i+1)));
        }

    }
}