package com.example.your_contact.API;

import com.example.your_contact.Model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {

    @SerializedName("error")
    private boolean err;
    @SerializedName("users")
    private List<User> users;

    public UsersResponse(boolean err, List<User> users) {
        this.err = err;
        this.users = users;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
