package com.example.juan_.practicafirebase.models;


import java.util.ArrayList;
import java.util.List;

public class MessageList {

    private List<Message> messageList = new ArrayList<>();

    public MessageList() {
        this.messageList = new ArrayList<>();
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
