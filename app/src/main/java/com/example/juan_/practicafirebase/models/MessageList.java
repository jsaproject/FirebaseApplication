package com.example.juan_.practicafirebase.models;


import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@IgnoreExtraProperties
public class MessageList {

    private HashMap<Integer, Message> messageList;

    public MessageList() {
        this.messageList = new HashMap<>();
    }

    public HashMap<Integer, Message> getMessageList() {
        return messageList;
    }

    public void addMessage(Integer key, Message m){
        messageList.put(key,m);
    }

    public void remove (Message m){
        messageList.remove(m);
    }

    public Message getFirstMessageUser(User u){
        boolean not_encontrado = true;
        Message m1 = new Message();
        while (not_encontrado){
            Set<Map.Entry<Integer, Message>> entries = messageList.entrySet();
            Iterator<Map.Entry<Integer, Message>> iterator = entries.iterator();

            while(iterator.hasNext() && not_encontrado){
                Map.Entry<Integer, Message> next = iterator.next();
                Message m = next.getValue();
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
        Set<Map.Entry<Integer, Message>> entries = messageList.entrySet();
        Iterator<Map.Entry<Integer, Message>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Message m = iterator.next().getValue();
            if(m.getAutor().getEmail() == u.getEmail()){
                objects.add(m);
            }
        }


        return objects;
    }

    public List<String> allMessages(){
        List<String> messages = new ArrayList<>();
        Set<Map.Entry<Integer, Message>> entries = messageList.entrySet();
        Iterator<Map.Entry<Integer, Message>> iterator = entries.iterator();
        while (iterator.hasNext()){
            messages.add(iterator.next().getValue().getMensaje());
        }
        return messages;
    }

    public boolean containsValue(User a){
        return getFirstMessageUser(a)!=null;
    }



    public boolean isEmpty(){
        return messageList.isEmpty();
    }


    public Set<User> allUsers(){
        Set<User> a = new HashSet<>();
        Iterator<Message> iterator = messageList.values().iterator();
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
