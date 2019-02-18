package com.example.juan_.practicafirebase.models;

import java.util.HashMap;

public class UserList {

    private HashMap<String, User> users;

    public UserList(User u) {
        this.users = new HashMap<>();
        users.put(u.getEmail(),u);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(User u) {
        users.put(u.getEmail(),u);
    }
}
