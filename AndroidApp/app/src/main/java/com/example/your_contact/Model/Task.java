package com.example.your_contact.Model;

public class Task {

    private String user_id, task, content;


    public Task(String user_id, String task, String content) {
        this.user_id = user_id;
        this.task = task;
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
