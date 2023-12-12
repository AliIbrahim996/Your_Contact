package com.example.your_contact.API;

import com.example.your_contact.Model.User;
import com.google.gson.annotations.SerializedName;

public class LogInResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("message")
    private String msg;
    @SerializedName("user")
    private User user;

    public LogInResponse(boolean err, String msg, User user) {
        this.err = err;
        this.msg = msg;
        this.user = user;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
