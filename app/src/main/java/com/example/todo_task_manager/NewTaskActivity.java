package com.example.todo_task_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class NewTaskActivity extends AppCompatActivity {
    private TextView newCategoryTextView;
    private EditText newCategoryEditText;
    private EditText taskEditText;
    private boolean editVisible = false;
    private ArrayList<TaskCategory> taskList;
    private RecyclerView taskRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Toolbar toolbar = findViewById(R.id.newTaskToolbar);
        setSupportActionBar(toolbar);

        newCategoryEditText = findViewById(R.id.taskCategoryEditText);
        newCategoryTextView = findViewById(R.id.taskCategoryTextView);
        taskEditText = findViewById(R.id.editTextTaskList);
        taskRecyclerView = findViewById(R.id.TaskListRecyclerView);

        initializeElementVisibility();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.taskmenu, menu);

        if(editVisible){
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        else{
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.editMenu){
            newCategoryEditText.setVisibility(View.VISIBLE);
            taskEditText.setVisibility(View.VISIBLE);
            newCategoryTextView.setVisibility(View.GONE);
            taskRecyclerView.setVisibility(View.GONE);
            editVisible = false;
            invalidateOptionsMenu();
        }
        else if (itemId == R.id.saveMenu){
            newCategoryEditText.setVisibility(View.GONE);
            taskEditText.setVisibility(View.GONE);
            newCategoryTextView.setVisibility(View.VISIBLE);
            taskRecyclerView.setVisibility(View.VISIBLE);
            editVisible = true;
            saveTasks();
            invalidateOptionsMenu();
            setAdaptor();
        }
        return true;
    }

    public void initializeElementVisibility(){
        newCategoryEditText.setVisibility(View.VISIBLE);
        newCategoryTextView.setVisibility(View.GONE);
    }

    public void saveTasks(){
        // Getting tasks entered by user
        String taskCategoryText = newCategoryEditText.getText().toString();
        String taskText = taskEditText.getText().toString();
        // Setting text in textView
        System.out.println(taskCategoryText);
        System.out.println(taskText);
        newCategoryTextView.setText(taskCategoryText);
        // Setting tasks in recycler view
        taskList = new ArrayList<>();
        String[] taskArray =  taskText.split("\n",20);
        setTaskInfo(taskArray);

    }

    private void setAdaptor() {
        recyclerAdapter adapter = new recyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerView.setAdapter(adapter);
    }

    private void setTaskInfo(String[] stringArray){
        if (stringArray.length > 0){
            for (String s : stringArray) {
                taskList.add(new TaskCategory('\u23FA' + "    " + s));
            }
        }
    }




}