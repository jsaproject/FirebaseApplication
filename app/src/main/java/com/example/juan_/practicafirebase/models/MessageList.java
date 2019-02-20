package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@IgnoreExtraProperties
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

    public void addMessage(Message m){
        messageList.add(m);
    }

    public void remove (Message m){
        messageList.remove(m);
    }

    public Message getFirstMessageUser(User u){
        boolean not_encontrado = true;
        Message m1 = new Message();
        while (not_encontrado){
            Iterator<Message> iterator = messageList.iterator();
            while(iterator.hasNext() && not_encontrado){
                Message m = iterator.next();
                if(m.getAutor().getEmail() == u.getEmail()){
                    not_encontrado = false;
                    m1 = m;
                }
            }

        }

        if (!not_encontrado){
            return m1;
        }else{
            return null;
        }
    }

    public ArrayList<Message> getAllMessageUser(User u){
        ArrayList<Message> objects = new ArrayList<>();
        Iterator<Message> iterator = messageList.iterator();
        while(iterator.hasNext()){
            Message m = iterator.next();
            if(m.getAutor().getEmail() == u.getEmail()){
                objects.add(m);
            }
        }


        return objects;
    }

    public List<String> allMessages(){
        List<String> messages = new ArrayList<>();
        Iterator<Message> iterator = messageList.iterator();
        while (iterator.hasNext()){
            messages.add(iterator.next().getMensaje());
        }
        return messages;
    }

    public boolean containsValue(User a){
        return getFirstMessageUser(a)!=null;
    }

    public boolean containsKey(String key){
        boolean not_encontrado = true;
        while (not_encontrado){
            Iterator<Message> iterator = messageList.iterator();
            while(iterator.hasNext() && not_encontrado){
                Message m = iterator.next();
                if(m.getMensaje().equalsIgnoreCase(key)){
                    not_encontrado = false;

                }
            }

        }
        return not_encontrado;
    }

    public boolean isEmpty(){
        return messageList.isEmpty();
    }


    public Set<User> allUsers(){
        Set<User> a = new HashSet<>();
        Iterator<Message> iterator = messageList.iterator();
        while(iterator.hasNext()){
            Message m = iterator.next();
            a.add(m.getAutor());

        }

        return a;

    }

    public int getSize(){
        return messageList.size();
    }
}
