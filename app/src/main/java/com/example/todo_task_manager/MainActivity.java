package com.example.todo_task_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.MainToolbar);
        setSupportActionBar(toolbar);
        dataBaseHelper = new DataBaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView1);
        taskCategoryList = new ArrayList<>();
        Button button = findViewById(R.id.changeModeButton);
        button.setText(SharedPreferenceHelper.getModeData(this));

        // SETTING TASK PROGRESS STATS
        int[] progressStatsArray = dataBaseHelper.getTaskProgressStats();
        float percentage = ((float)progressStatsArray[1]/(float)progressStatsArray[0])*100;
        @SuppressLint("DefaultLocale") String progressString = String.format("%d/%d  (%d%s)",progressStatsArray[1], progressStatsArray[0], Math.round(percentage), "%");
        TextView progressText = findViewById(R.id.progressTextView);
        progressText.setText(progressString);

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

    // FOR PLUS BUTTON
    public void launchNewTaskActivity(View view){
        SharedPreferenceHelper.editLastEditedCategory(this, "");
        MainActivity.newTaskMode = true;
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    // APP EXITS WHEN PRESSED BACK
    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    /****************************************************************
     FunctionName    : changeModeButtonClick
     Description     : selects different mode when mode change button clicked
     InputParameters : View
     Return          :
     *******************************************************************/

    public void changeModeButtonClick(View view){
        Button button = findViewById(R.id.changeModeButton);
        String buttonText = button.getText().toString();
        switch(buttonText){
            case Constants.PERSONAL:
                // CAREER MODE
                button.setText(Constants.CAREER);
                SharedPreferenceHelper.editModeData(this, Constants.CAREER);
                break;

            case Constants.CAREER:
                // PERSONAL MODE
                button.setText(Constants.PERSONAL);
                SharedPreferenceHelper.editModeData(this, Constants.PERSONAL);
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /****************************************************************
     FunctionName    : launchUrgentModeActivity
     Description     : Function to launch urgent activity
     InputParameters : View
     Return          :
     *******************************************************************/

    public void launchUrgentModeActivity(View view){
        Intent intent = new Intent(this, UrgentActivity.class);
        startActivity(intent);
    }
}