package com.example.todo_task_manager;

public class TaskCategory {
    private String categoryName;

    public TaskCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
