package com.example.jcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainFragment.onFragmentBtnSelected,AddEvent.onFragmentBtnSelected,EventFragment.onEventLister,DailyEventsFragment.onDailyEventLister{

    public ArrayList<Event> events;
    public HashSet<CalendarDay> dates=new HashSet<>();
    private CalendarDay newDate;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MainFragment mainFragment=new MainFragment();
    RecyclerView recyclerView;
    AddEvent addEvent=new AddEvent();
    Event newEvent=new Event();
    ArrayList<Calendar> tempCal;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        loadDates();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,mainFragment);
        fragmentTransaction.commit();


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){//Hamburget Menudaki tuşları ayarlıyoruz
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId()==R.id.calendar){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,mainFragment);
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId()==R.id.events){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new EventFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId()==R.id.settings){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new SettingsFragment());
            fragmentTransaction.commit();
        }

        return true;
    }

    @Override
    public void onAddMenuAttached(View view) {//Event ekleme ekranı gelirse edittextlerdeki ayarlamalar yapılıyor
        EditText et3=view.findViewById(R.id.editStart);
        et3.setText(newDate.getDate().toString());
    }

    @Override
    public void OnClick(int id){//Event eklemedeki butonların uzantıları yapılıyor
        if(id==0){//Alarm kurma durumu
            Calendar calendar = Calendar.getInstance();

            timePickerDialog = new TimePickerDialog(MainActivity.this,onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.setTitle("Add Alarm");
            timePickerDialog.show();


        }
        else if(id==1) {//Konum ayarlama durumu
        }
        else if(id==2) {//Tekrarlayıcı kurumu
        }
        else{//Ayarlanan eventin kaydedilmesi
                newEvent.setName(addEvent.et1.getText().toString());
                newEvent.setContent(addEvent.et2.getText().toString());
                newEvent.setStart(newDate);
                events.add(newEvent);

                addEvent.et1.setText("");
                addEvent.et1.setHint("Event Name");
                addEvent.et2.setText("");
                addEvent.et2.setHint("Event Content");
                addEvent.et4.setText("");
                addEvent.et4.setHint("Event Ending Date");

                saveData();

                dates.add(newDate);

                saveDates();

                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,mainFragment);
                fragmentTransaction.commit();

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer),"Event Added",Snackbar.LENGTH_LONG);
                mySnackbar.setActionTextColor(Color.RED);
                mySnackbar.show();

        }

    }
    public void OnAttachedToWindowEvent(View view){//Event Listeleme fragmentinin ekrana gelmesi durumu
        recyclerView=findViewById(R.id.recyclerView);
        EventListAdapter eventAdapter=new EventListAdapter(this,events);
        recyclerView.setAdapter(eventAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull final RecyclerView rv, @NonNull MotionEvent e) {
                AlertDialog.Builder builder2= new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Event Actions");
                builder2.setMessage("Select an action");
                builder2.setPositiveButton("Event Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder2.setNeutralButton("Delete Event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog2=builder2.create();
                dialog2.show();
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    public void OnAttachedToWindow(View view){//Takvimimizin ekrana gelmesi durumunda etkinliklerin işaretlenmesi ayarlanıyor
        mainFragment.materialCalendarView.addDecorator(new EventDecorator(0,dates));
    }
    @Override
    public void OnDateLongClick(final CalendarDay date) {//Takvimde herhangi bir güne uzun tıklayarak işlem penceresinden işlemimizi seçebiliyoruz
        newDate=date;
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("JCalendar");
        builder.setMessage("Select an action");
        builder.setPositiveButton("Add Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {//Event ekleme
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,addEvent);
                fragmentTransaction.commit();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {//Alert dialogdan çıkma
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,mainFragment);
                fragmentTransaction.commit();
            }
        });
        builder.setNeutralButton("See Events", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {// Seçlen gündeki eventleri gösterme
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new DailyEventsFragment());
                fragmentTransaction.commit();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void OnAttachedToWindowDailyEvent(View view) {//Seçilen gündeki eventleri gösterme durumu
        ArrayList<Event> tempEvents=new ArrayList<>();

        int i=0;
        while(i<events.size()) {
            if (events.get(i).getStart().equals(newDate)) {
                tempEvents.add(events.get(i));
                i++;
            }
            else
                i++;
        }

        recyclerView=findViewById(R.id.recyclerView);
        EventListAdapter eventAdapter=new EventListAdapter(this,tempEvents);
        recyclerView.setAdapter(eventAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(@NonNull final RecyclerView rv, @NonNull MotionEvent e) {//Recycler Viewden herhangi bir event seçilerek işlem yapılabilir
                AlertDialog.Builder builder2= new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("Event Actions");
                builder2.setMessage("Select an action");
                builder2.setPositiveButton("Event Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {//Seçilen eventin detaylarını gösterme
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {// İşlem yapmama durumu
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder2.setNeutralButton("Delete Event", new DialogInterface.OnClickListener() {// Seçilen eventi silme durumu
                    @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog2=builder2.create();
                dialog2.show();
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }










    public void saveDates(){//Takvimde işaretli günleri kaydetme
        SharedPreferences sharedPreferences2=getSharedPreferences("Marked List",MODE_PRIVATE);
        SharedPreferences.Editor editor2=sharedPreferences2.edit();
        Gson gson2=new Gson();
        String json2=gson2.toJson(dates);
        editor2.putString("MarkedList",json2);
        editor2.apply();
    }
    public void loadDates(){//Takvimde işaretli günleri yükleme
        SharedPreferences sharedPreferences2=getSharedPreferences("Marked List",MODE_PRIVATE);
        Gson gson2=new Gson();
        String json2=sharedPreferences2.getString("MarkedList",null);
        Type type2=new TypeToken<HashSet<CalendarDay>>() {}.getType();
        dates=gson2.fromJson(json2,type2);
        if(dates==null){
            dates=new HashSet<>();
        }
    }

    public void saveData(){// Eventleri kaydetme
        SharedPreferences sharedPreferences=getSharedPreferences("Event List",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(events);
        editor.putString("EventList",json);
        editor.apply();
    }
    public void loadData(){//Eventleri Yükleme
        SharedPreferences sharedPreferences=getSharedPreferences("Event List",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("EventList",null);
        Type type=new TypeToken<ArrayList<Event>>() {}.getType();
        events=gson.fromJson(json,type);
        if(events==null){
            events=new ArrayList<>();
        }

    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {//Alarm için gerekli fonksiyon(çalışmıyor)

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hour);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }
            Toast.makeText(getApplicationContext(),"Alarm Activated!",Toast.LENGTH_SHORT).show();
            tempCal=new ArrayList<Calendar>();
            tempCal=newEvent.getReminders();
            tempCal.add(calSet);
            newEvent.setReminders(tempCal);
        }};



}
