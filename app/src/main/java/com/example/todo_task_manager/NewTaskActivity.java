package com.example.todo_task_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NewTaskActivity extends AppCompatActivity {
    private TextView newCategoryTextView;
    private EditText newCategoryEditText;
    private EditText taskEditText;
    private boolean editVisible = !MainActivity.newTaskMode;
    private ArrayList<TaskCategory> taskList;
    private RecyclerView taskRecyclerView;
    private DataBaseHelper dataBaseHelper;
    private boolean newTaskMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        newTaskMode = MainActivity.newTaskMode;

        Toolbar toolbar = findViewById(R.id.newTaskToolbar);
        setSupportActionBar(toolbar);

        newCategoryEditText = findViewById(R.id.taskCategoryEditText);
        newCategoryTextView = findViewById(R.id.taskCategoryTextView);
        taskEditText = findViewById(R.id.editTextTaskList);
        taskRecyclerView = findViewById(R.id.TaskListRecyclerView);
        dataBaseHelper = new DataBaseHelper(this);

        setVisibilityMode(newTaskMode);
        if(!newTaskMode){
            setTextForTextViews();
        }
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
            setVisibilityMode(true);
            editVisible = false;
            invalidateOptionsMenu();
        }
        else if (itemId == R.id.saveMenu){
            setVisibilityMode(false);
            editVisible = true;
            saveTasks();
            invalidateOptionsMenu();
            setAdaptor();
        }
        return true;
    }


    public void saveTasks(){
        // Getting tasks entered by user
        String taskCategoryText = newCategoryEditText.getText().toString();
        String taskText = taskEditText.getText().toString();
        // Setting text in textView
        newCategoryTextView.setText(taskCategoryText);
        // Setting tasks in recycler view
        taskList = new ArrayList<>();
        String[] taskArray =  taskText.split("\n",20);

        // INSERTING DATA INTO DATABASE
        if ((taskCategoryText.length() >1) & (taskText.length() > 1)) {
            for (int index=0; index< taskArray.length; index++){
                // TO AVOID CREATION OF EMPTY TASK
                String spaceRemovedString = taskArray[index].replaceAll("\\s", "");
                if(spaceRemovedString.length() > 1){
                    TaskModel taskModel = new TaskModel(-1, taskArray[index], taskCategoryText, false, false);
                    boolean status = dataBaseHelper.insertData(taskModel);
                }
            }
        }

        // FETCHING DATA FROM DATABASE AND DISPLAYING
        List<String> DBTasksList = dataBaseHelper.getTasks(taskCategoryText);
        setTaskInfo(DBTasksList);
    }

    private void setAdaptor() {
        recyclerAdapter adapter = new recyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.setItemAnimator(new DefaultItemAnimator());
        taskRecyclerView.setAdapter(adapter);
    }

    private void setTaskInfo(List<String> DBTaskList){
        taskList = new ArrayList<>();
        for (int index=0; index<DBTaskList.size(); index++){
            taskList.add(new TaskCategory('\u25cf' + "    " + DBTaskList.get(index)));
        }
        setAdaptor();
    }


    private void setVisibilityMode(boolean newTaskMode){
        if(newTaskMode){
            newCategoryEditText.setVisibility(View.VISIBLE);
            taskEditText.setVisibility(View.VISIBLE);
            newCategoryTextView.setVisibility(View.GONE);
            taskRecyclerView.setVisibility(View.GONE);
        }
        else{
            newCategoryEditText.setVisibility(View.GONE);
            taskEditText.setVisibility(View.GONE);
            newCategoryTextView.setVisibility(View.VISIBLE);
            taskRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setTextForTextViews(){
        if (!MainActivity.currentCategoryString.equals(""))  {
            newCategoryEditText.setText(MainActivity.currentCategoryString);
            newCategoryTextView.setText(MainActivity.currentCategoryString);
            List<String> DBTasksList = dataBaseHelper.getTasks(MainActivity.currentCategoryString);
            String editTextString = "";
            for(int index=0;index<DBTasksList.size();index++){
                editTextString = editTextString + DBTasksList.get(index) + "\n";
            }
            taskEditText.setText(editTextString);
            setTaskInfo(DBTasksList);
        }
    }

    // RETURNS TO MAIN ACTIVITY WHEN PRESSED BACK
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}