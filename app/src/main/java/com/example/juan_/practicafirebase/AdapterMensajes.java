package com.example.juan_.practicafirebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.juan_.practicafirebase.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<Message> listMensaje;
    private Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
        this.listMensaje = new ArrayList<>();
    }

    public void addMensaje(Message m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMensaje onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes, parent, false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(HolderMensaje holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getAutor());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        if (listMensaje.get(position).getFoto().equalsIgnoreCase("1")) {
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            //holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensaje.get(position).getMensaje()).into(holder.getFotoMensaje());
        } else if (listMensaje.get(position).getFoto().equalsIgnoreCase("2")) {
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }
        if (listMensaje.get(position).getFotoPerfil().isEmpty()) {
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        }
        String codigoHora = listMensaje.get(position).getFecha();
        holder.getHora().setText(codigoHora);
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

}