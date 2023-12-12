package com.example.your_contact.API;

import com.example.your_contact.Model.Task;
import com.example.your_contact.Model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskResponse {

    @SerializedName("error")
    private boolean err;
    @SerializedName("Tasks")
    private List<Task> tasks;

    public TaskResponse(boolean err, List<Task> tasks) {
        this.err = err;
        this.tasks = tasks;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
