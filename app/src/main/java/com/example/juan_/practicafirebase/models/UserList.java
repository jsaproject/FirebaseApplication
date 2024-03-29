package com.example.juan_.practicafirebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@IgnoreExtraProperties
public class UserList {

    private HashMap<String,Object> users;

    public UserList(){
        this.users = new HashMap<>();
    }

    public UserList(User u) {
        this.users = new HashMap<>();
        users.put(u.getEmail(),u);
    }

    public HashMap<String, Object> getUsers() {
        return users;
    }

    public void setUsers(User u) {
        users.put(u.getEmail(),u);;
    }

    public void addUser (User u){
        setUsers(u);
    }

    public void remove (String key){
        users.remove(key);
    }



    public Set<Map.Entry<String, Object>> entriesGroup(){
        Set<Map.Entry<String, Object>> entries = users.entrySet();
        return entries;
    }

    public boolean containsKey(String key){
        return users.containsKey(key);
    }

    public boolean isEmpty(){
        return users.isEmpty();
    }

    public Set<String> allKeys(){
        return users.keySet();
    }

    public int getSize(){
        return users.size();
    }
}
