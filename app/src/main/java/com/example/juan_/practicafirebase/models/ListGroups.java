package com.example.juan_.practicafirebase.models;

import java.util.HashMap;

public class ListGroups {

    private HashMap<String, Groups> groups;

    public ListGroups(HashMap<String, Groups> groups) {
        this.groups = groups;
    }

    public HashMap<String, Groups> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Groups> groups) {
        this.groups = groups;
    }
}
