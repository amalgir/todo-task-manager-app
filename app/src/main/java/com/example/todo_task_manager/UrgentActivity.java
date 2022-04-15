package com.example.todo_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class UrgentActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private ArrayList<TaskCategory> taskList;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_urgent);

        dataBaseHelper = new DataBaseHelper(this);
        taskRecyclerView = findViewById(R.id.urgentTaskListRecyclerView);
        setUrgentTasksInfo();
    }

    // RETURNS TO MAIN ACTIVITY WHEN PRESSED BACK
    public void launchMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setAdaptor() {
        recyclerAdapter adapter = new recyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerView.setAdapter(adapter);
    }

    private void setUrgentTasksInfo(){
        List<String> DBTaskList = dataBaseHelper.getUrgentTasks();
        taskList = new ArrayList<>();
        for (int index=0; index<DBTaskList.size(); index++){
            taskList.add(new TaskCategory('\u25cf' + "    " + DBTaskList.get(index)));
        }
        setAdaptor();
    }




}