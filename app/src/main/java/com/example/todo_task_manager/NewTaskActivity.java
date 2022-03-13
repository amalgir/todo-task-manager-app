package com.example.todo_task_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NewTaskActivity extends AppCompatActivity {
    private TextView newCategoryTextView;
    private EditText newCategoryEditText;
    private boolean editVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Toolbar toolbar = findViewById(R.id.newTaskToolbar);
        setSupportActionBar(toolbar);

        newCategoryEditText = findViewById(R.id.taskCategoryEditText);
        newCategoryTextView = findViewById(R.id.taskCategoryTextView);

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
            newCategoryTextView.setVisibility(View.GONE);
            editVisible = false;
            invalidateOptionsMenu();
        }
        else if (itemId == R.id.saveMenu){
            newCategoryEditText.setVisibility(View.GONE);
            newCategoryTextView.setVisibility(View.VISIBLE);
            editVisible = true;
            invalidateOptionsMenu();
        }
        return true;
    }

    public void initializeElementVisibility(){
        newCategoryEditText.setVisibility(View.VISIBLE);
        newCategoryTextView.setVisibility(View.GONE);
    }
}