package com.example.juan_.practicafirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.juan_.practicafirebase.models.Message;
import com.example.juan_.practicafirebase.models.MessageList;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Message> {

    private ArrayList<Message> dataSet;
    Context mContext;
    private int lastPosition;

    public CustomAdapter(ArrayList<Message> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.lastPosition = -1;

    }


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.fecha);

            //result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //result = convertView;
        }

            lastPosition = position;

            viewHolder.txtName.setText(dataModel.getAutor());
            viewHolder.txtType.setText(dataModel.getMensaje());
            viewHolder.txtVersion.setText(dataModel.getFecha());

            return convertView;
        }

    }

