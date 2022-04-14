package com.example.todo_task_manager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<TaskCategory> taskCategoriesList;
    private DataBaseHelper dataBaseHelper;

    // Constructor
    public recyclerAdapter(ArrayList<TaskCategory> taskCategoriesList){
        this.taskCategoriesList = taskCategoriesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryText;

        public MyViewHolder(final View view){
            super(view);
            categoryText = view.findViewById(R.id.taskCategoryTextView2);
            dataBaseHelper = new DataBaseHelper(view.getContext());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(view.getContext().toString().contains("MainActivity")){
                        int position = getAdapterPosition();
                        String currentItemName = taskCategoriesList.get(position).getCategoryName();
                        MainActivity.newTaskMode = false;
                        MainActivity.currentCategoryString = currentItemName;
                        Intent intent = new Intent(view.getContext(), NewTaskActivity.class);
                        view.getContext().startActivity(intent);
                    }
                    else if(view.getContext().toString().contains("NewTaskActivity")){
                        // CHANGE TASK STATUS WHEN TASK IS CLICKED
                        int position = getAdapterPosition();
                        String currentTaskItemName = taskCategoriesList.get(position).getCategoryName();
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(view.getContext());
                        dataBaseHelper.toggleTaskStatus(currentTaskItemName);
                        notifyItemChanged(position);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(view.getContext().toString().contains("NewTaskActivity")){
                        // CHANGE TASK URGENCY WHEN TASK IS LONG CLICKED
                        int position = getAdapterPosition();
                        String currentTaskItemName = taskCategoriesList.get(position).getCategoryName();
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(view.getContext());
                        dataBaseHelper.toggleTaskUrgency(currentTaskItemName);
                        notifyItemChanged(position);
                    }
                    return true;
                }
            });

        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = taskCategoriesList.get(position).getCategoryName();
        holder.categoryText.setText(name);

        // RECYCLER VIEW IN TASKS SCREEN
        if(holder.itemView.getContext().toString().contains("NewTaskActivity")){
            try {
                List<String> allTasksWithGivenStatus = dataBaseHelper.getAllTasksWithGivenStatus(1);
                List<String> allTasksWithGivenUrgency = dataBaseHelper.getAllTasksWithGivenUrgency(1);
                String formattedTaskString = name.split(" {4}")[1];

                if (!allTasksWithGivenStatus.contains(formattedTaskString)) {
                    if (allTasksWithGivenUrgency.contains(formattedTaskString)) {
                        holder.categoryText.setTextColor(Color.parseColor("#FF4C30"));
                        holder.categoryText.setPaintFlags(holder.categoryText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    } else {
                        holder.categoryText.setTextColor(Color.WHITE);
                        holder.categoryText.setPaintFlags(holder.categoryText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                } else {
                    holder.categoryText.setTextColor(Color.GRAY);
                    holder.categoryText.setPaintFlags(holder.categoryText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
            catch (Exception e){
                System.out.println("EXCEPTION: " + e.toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return taskCategoriesList.size();
    }
}
