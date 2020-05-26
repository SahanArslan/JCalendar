package com.example.jcalendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventFragment extends Fragment {


    private onEventLister listener;
    ArrayList<Event> events;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_event,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                listener.OnAttachedToWindowEvent(view);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  onEventLister) {
            listener = (onEventLister) context;
        }
        else{
            throw new ClassCastException(context.toString()+"must implement listener");
        }
    }

    public interface onEventLister{
        public void OnAttachedToWindowEvent(View view);
    }

}
