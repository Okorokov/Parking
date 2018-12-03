package com.example.hpsus.parking.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;

import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.hpsus.parking.Car;
import com.example.hpsus.parking.MainActivity;
import com.example.hpsus.parking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    public int NOTIFY_ID = 1;
    public Date dateNow;
    public Context context;
    public PendingIntent contentIntent;
    public Notification.Builder builder;
    public NotificationManager nm;
    public Intent notificationIntent;
    public Notification n;
    private FirebaseDatabase database;
    private DatabaseReference myRef,mNames;
    private  Car car;
    private String nickName;
    private Random random = new Random();
    private Boolean flKey;
    private Resources res;
    private static Integer[] nameImageOn={R.drawable.fl1on,
            R.drawable.fl2on,
            R.drawable.fl3on,
            R.drawable.fl4on,
            R.drawable.fl5on,
            R.drawable.fl6on,
            R.drawable.fl7on,
            R.drawable.fl8on,
            R.drawable.fl9on,
            R.drawable.fl10on};

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("SERVICE","onCreate");
        res = this.getResources();

        dateNow=new Date();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Handbook online")
                .setContentText("who has a birthday?...")
                .setContentIntent(pendingIntent).build();

        startForeground(1037, notification);
        Intent hideIntent = new Intent(this, HideNotification.class);
        startService(hideIntent);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("parking");
        mNames= FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                car=dataSnapshot.getValue(Car.class);

                if(Car.count(Car.class)==1){
                    car=Car.findById(Car.class,1);
                    car.save();
                }else{
                    car.save();
                }
                // name=mNames.;
                mNames.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("logs").child("name").getValue().toString();
                         String id=dataSnapshot.child("logs").child("carof").getValue().toString();
                        if(!name.equals(nickName)&!flKey){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                SendMessage("Машину","припарковал(а) "+name, Integer.valueOf(id),NOTIFY_ID);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SERVICE","onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE","onStartCommand");
        nickName = intent.getStringExtra(MainActivity.PARAM_NICK_NAME);
        flKey = intent.getBooleanExtra(MainActivity.PARAM_KEY,false);
        context = getApplicationContext(); //инициатор - текущая активность
        notificationIntent = new Intent(context, MainActivity.class);
        contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        builder = new Notification.Builder(context);
        return Service.START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void SendMessage(CharSequence title, CharSequence mess, Integer id, Integer NOTIFY_ID) {


        nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        builder.setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setLargeIcon(BitmapFactory.decodeResource(res, nameImageOn[id]))
                .setTicker(mess)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(mess); // Текст уведомления

        n = builder.getNotification();

        n.defaults = Notification.DEFAULT_SOUND |
                Notification.DEFAULT_VIBRATE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(nm.getActiveNotifications().length==0){
                nm.notify(NOTIFY_ID, n);
            }
         }
    };
}
