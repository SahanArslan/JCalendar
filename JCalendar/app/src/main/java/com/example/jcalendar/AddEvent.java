package com.example.jcalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AddEvent extends Fragment {

    private onFragmentBtnSelected listener;
    private ArrayList<Event> events;
    private CalendarDay day;
    Event newEvent=new Event();
    EditText et1,et2,et3,et4;
    Button buttonAlarm,buttonLocation,buttonRepeat,buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addevent,container,false);
        et1=view.findViewById(R.id.editName);
        et2=view.findViewById(R.id.editContent);
        et3=view.findViewById(R.id.editStart);
        et4=view.findViewById(R.id.editEnd);
        buttonAlarm=view.findViewById(R.id.buttonAlarm);
        buttonRepeat=view.findViewById(R.id.buttonRepeat);
        buttonLocation=view.findViewById(R.id.buttonLocation);
        buttonSave=view.findViewById(R.id.buttonSave);

        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                listener.onAddMenuAttached(view);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(0);
            }
        });
        buttonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(1);
            }
        });
        buttonLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnClick(2);
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnClick(3);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onFragmentBtnSelected{
        public void OnClick(int id);
        public void onAddMenuAttached(View view);
    }



}
