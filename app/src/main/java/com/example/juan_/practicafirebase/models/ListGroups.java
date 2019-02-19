package com.example.juan_.practicafirebase.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListGroups {

    private HashMap<String, Group> groups;

    public ListGroups(){
        groups = new HashMap<>();
    }

    public ListGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public void addGroup (String key, Group g){
        groups.put(key,g);
    }

    public void remove (String key){
        groups.remove(key);
    }

    public Group getGroup(String key){
        return groups.get(key);
    }

    public Set<Map.Entry<String, Group>> entriesGroup(){
        Set<Map.Entry<String, Group>> entries = groups.entrySet();
        return entries;
    }

    public boolean containsValue(Group a){
        return groups.containsValue(a);
    }

    public boolean containsKey(String key){
        return groups.containsKey(key);
    }

    public boolean isEmpty(){
        return groups.isEmpty();
    }

    public Collection<Group> allValue(){
        return groups.values();
    }

    public Set<String> allKeys(){
        return groups.keySet();
    }

    public int getSize(){
        return groups.size();
    }
}
