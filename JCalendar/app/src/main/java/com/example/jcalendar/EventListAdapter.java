package com.example.jcalendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>{
    ArrayList<Event> events;
    LayoutInflater inflater;
    public EventListAdapter(Context context, ArrayList<Event> events){
        inflater=LayoutInflater.from(context);
        this.events=events;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_event_card,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event selectedEvent=events.get(position);
        holder.setData(selectedEvent,position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView eventName;
        TextView eventDate;
        public MyViewHolder(View itemView){
            super(itemView);
            eventName=(TextView)itemView.findViewById(R.id.eventName);
            eventDate=(TextView)itemView.findViewById(R.id.eventDate);
        }
        public void setData(Event selectedEvent,int position){
            this.eventName.setText(selectedEvent.getName());
            this.eventDate.setText(selectedEvent.getStart().toString());
        }

        @Override
        public void onClick(View view) {
        }
    }
}
