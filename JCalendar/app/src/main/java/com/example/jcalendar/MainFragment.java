package com.example.jcalendar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class MainFragment extends Fragment{

    public HashSet<CalendarDay> dates=new HashSet<>();
    private CalendarDay newDate;
    private onFragmentBtnSelected listener;
    public MaterialCalendarView materialCalendarView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        materialCalendarView=view.findViewById(R.id.calendarView);
        materialCalendarView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                listener.OnAttachedToWindow(view);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
        materialCalendarView.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
                listener.OnDateLongClick(date);

            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        }
        else{
            throw new ClassCastException(context.toString()+"must implement listener");
        }
    }

    public interface onFragmentBtnSelected{
        public void OnDateLongClick(CalendarDay date);
        public void OnAttachedToWindow(View view);
    }

}

