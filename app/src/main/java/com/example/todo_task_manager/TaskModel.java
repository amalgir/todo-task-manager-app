package com.example.todo_task_manager;

public class TaskModel {

    private int id;
    private String taskName;
    private String taskCategory;
    private boolean taskStatus;
    private boolean taskUrgency;

    public TaskModel(int id, String taskName, String taskCategory, boolean taskStatus, boolean taskUrgency) {
        this.id = id;
        this.taskName = taskName;
        this.taskCategory = taskCategory;
        this.taskStatus = taskStatus;
        this.taskUrgency = taskUrgency;
    }


    public TaskModel(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public boolean isTaskUrgency() {
        return taskUrgency;
    }

    public void setTaskUrgency(boolean taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskCategory='" + taskCategory + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskUrgency=" + taskUrgency +
                '}';
    }

}
