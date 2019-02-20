package com.example.juan_.practicafirebase.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@IgnoreExtraProperties
public class UserList {

    private HashMap<String, User> users;

    public UserList(){
        this.users = new HashMap<>();
    }

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

    public void addUser (String key, User u){
        users.put(key,u);
    }

    public void remove (String key){
        users.remove(key);
    }

    public User getUser(String key){
        return users.get(key);
    }

    public Set<Map.Entry<String, User>> entriesGroup(){
        Set<Map.Entry<String, User>> entries = users.entrySet();
        return entries;
    }

    public boolean containsValue(User a){
        return users.containsValue(a);
    }

    public boolean containsKey(String key){
        return users.containsKey(key);
    }

    public boolean isEmpty(){
        return users.isEmpty();
    }

    public Collection<User> allValue(){
        return users.values();
    }

    public Set<String> allKeys(){
        return users.keySet();
    }

    public int getSize(){
        return users.size();
    }
}
