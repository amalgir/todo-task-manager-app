package com.example.todo_task_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<TaskCategory> taskCategoriesList;

    // Constructor
    public recyclerAdapter(ArrayList<TaskCategory> taskCategoriesList){
        this.taskCategoriesList = taskCategoriesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryText;

        public MyViewHolder(final View view){
            super(view);
            categoryText = view.findViewById(R.id.taskCategoryTextView);
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

    }

    @Override
    public int getItemCount() {
        return taskCategoriesList.size();
    }
}
