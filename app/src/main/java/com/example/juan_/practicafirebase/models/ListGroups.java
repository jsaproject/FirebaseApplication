package com.example.juan_.practicafirebase.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@IgnoreExtraProperties
public class ListGroups {

    private HashMap<String, Group> grupos;


    public ListGroups(){
        grupos = new HashMap<>();
    }

    public ListGroups(HashMap<String, Group> groups) {
        this.grupos = groups;
    }

    public HashMap<String, Group> getGroups() {
        return grupos;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.grupos = groups;
    }

    public void addGroup (String key, Group g){
        grupos.put(key,g);
    }

    public void remove (String key){
        grupos.remove(key);
    }

    public Group getGroup(String key){
        return grupos.get(key);
    }

    public Set<Map.Entry<String, Group>> entriesGroup(){
        Set<Map.Entry<String, Group>> entries = grupos.entrySet();
        return entries;
    }

    public boolean containsValue(Group a){
        return grupos.containsValue(a);
    }

    public boolean containsKey(String key){
        return grupos.containsKey(key);
    }

    public boolean isEmpty(){
        return grupos.isEmpty();
    }

    public Collection<Group> allValue(){
        return grupos.values();
    }

    public Set<String> allKeys(){
        return grupos.keySet();
    }

    public int getSize(){
        return grupos.size();
    }
}
