package com.example.hpsus.parking;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hpsus.parking.service.MyService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton iBfl1,
                        iBfl2,
                        iBfl3,
                        iBfl4,
                        iBfl5,
                        iBfl6,
                        iBfl7,
                        iBfl8,
                        iBfl9,
                        iBfl10;
    private static Integer[] nameImageOff={R.drawable.fl1,
                                            R.drawable.fl2,
                                            R.drawable.fl3,
                                            R.drawable.fl4,
                                            R.drawable.fl5,
                                            R.drawable.fl6,
                                            R.drawable.fl7,
                                            R.drawable.fl8,
                                            R.drawable.fl9,
                                            R.drawable.fl10};
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
    private static String[] caronoff={"caronoff1",
                                        "caronoff2",
                                        "caronoff3",
                                        "caronoff4",
                                        "caronoff5",
                                        "caronoff6",
                                        "caronoff7",
                                        "caronoff8",
                                        "caronoff9",
                                        "caronoff10"};
    public final String SAVED_TEXT = "saved_nickName";
    public final static String PARAM_NICK_NAME = "nickName";
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String PARAM_KEY = "param_key";
    public final static int TASK1_CODE = 1;

    private String nickName;
    private Animation animScale;
    private FirebaseDatabase database;
    private DatabaseReference myRef,mNames;
    private Car car;
    private Intent intent;
    private ImageView imageView;
    private SharedPreferences sPref;
    private EditText userInput;
    private PendingIntent pi;
    private  Boolean flKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        flKey=true;
        //мини авторизация
        if (!loadNickName()) {
            showDialog();
        }
        StartService();

/*
        for(int i=0 ;i< cars.size();i++) {
            Log.d("XXXX", String.valueOf(i));
            cars.get(i).save();
        }
*/
        imageView=(ImageView) findViewById(R.id.imageView);
        iBfl1=(ImageButton)findViewById(R.id.iBfl1);
        iBfl2=(ImageButton)findViewById(R.id.iBfl2);
        iBfl3=(ImageButton)findViewById(R.id.iBfl3);
        iBfl4=(ImageButton)findViewById(R.id.iBfl4);
        iBfl5=(ImageButton)findViewById(R.id.iBfl5);
        iBfl6=(ImageButton)findViewById(R.id.iBfl6);
        iBfl7=(ImageButton)findViewById(R.id.iBfl7);
        iBfl8=(ImageButton)findViewById(R.id.iBfl8);
        iBfl9=(ImageButton)findViewById(R.id.iBfl9);
        iBfl10=(ImageButton)findViewById(R.id.iBfl10);

        iBfl1.setOnClickListener(this);
        iBfl2.setOnClickListener(this);
        iBfl3.setOnClickListener(this);
        iBfl4.setOnClickListener(this);
        iBfl5.setOnClickListener(this);
        iBfl6.setOnClickListener(this);
        iBfl7.setOnClickListener(this);
        iBfl8.setOnClickListener(this);
        iBfl9.setOnClickListener(this);
        iBfl10.setOnClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference("parking");
        mNames= FirebaseDatabase.getInstance().getReference();
        //mNames.child("logs").push().child("name").setValue("petrowich");
        //mNames.child("logs").child("time").setValue(new Date());
        //init();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                car=dataSnapshot.getValue(Car.class);

                reimage(iBfl1,car.getCaronoff1(),0);
                reimage(iBfl2,car.getCaronoff2(),1);
                reimage(iBfl3,car.getCaronoff3(),2);
                reimage(iBfl4,car.getCaronoff4(),3);
                reimage(iBfl5,car.getCaronoff5(),4);
                reimage(iBfl6,car.getCaronoff6(),5);
                reimage(iBfl7,car.getCaronoff7(),6);
                reimage(iBfl8,car.getCaronoff8(),7);
                reimage(iBfl9,car.getCaronoff9(),8);
                reimage(iBfl10,car.getCaronoff10(),9);
                Log.d("XXXX", String.valueOf(dataSnapshot.getValue()));
                //Car carbase=new Car(car.getCaronoff1(),car.getCaronoff2(),car.getCaronoff3(),car.getCaronoff4(),car.getCaronoff5(),car.getCaronoff6(),car.getCaronoff7(),car.getCaronoff8(),car.getCaronoff9(),car.getCaronoff10() );
                if(Car.count(Car.class)==1){
                    car=Car.findById(Car.class,1);
                    car.save();
                }else{
                    car.save();
                }

                Log.d("XXXX", String.valueOf(Car.count(Car.class)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        animScale = AnimationUtils.loadAnimation(this, R.anim.scale);

    }
    public  void showDialog(){
        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.dialog_signin, null);
        //Создаем AlertDialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);
        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        userInput = (EditText) promptsView.findViewById(R.id.input_text);
        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                saveINFO(userInput.getText().toString().toLowerCase());

                                if (!userInput.getText().equals("")){
                                    Toast.makeText(MainActivity.this,"Добро пожаловать "+userInput.getText(), Toast.LENGTH_SHORT).show();
                                }else {
                                    finish();
                                    dialog.cancel();
                                }
                                loadNickName();
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                finish();
                                dialog.cancel();
                            }
                        });
        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();
        //и отображаем его:
        alertDialog.show();
    }
    private void saveINFO(String nickName) {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, nickName);
        ed.commit();
    }
    private boolean loadNickName() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        nickName=savedText;
        boolean auth=false;

        if (!savedText.equals("")){
            auth=true;
        }
        return auth;
    }
    public void init(){
        myRef.child("caronoff1").setValue(0);
        myRef.child("caronoff2").setValue(0);
        myRef.child("caronoff3").setValue(0);
        myRef.child("caronoff4").setValue(0);
        myRef.child("caronoff5").setValue(0);
        myRef.child("caronoff6").setValue(0);
        myRef.child("caronoff7").setValue(0);
        myRef.child("caronoff8").setValue(0);
        myRef.child("caronoff9").setValue(0);
        myRef.child("caronoff10").setValue(0);
    }
    public void StartService(){
        intent = new Intent(MainActivity.this, MyService.class);
        pi = createPendingResult(TASK1_CODE,intent,0);
        intent.putExtra(PARAM_NICK_NAME, nickName);
        intent.putExtra(PARAM_KEY, flKey);
        intent.putExtra(PARAM_PINTENT, pi);
        Log.d("XXXX","nickName "+ nickName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }else{
            startService(intent);
        }
    }
    private void reimage(ImageButton ib,Integer sost, Integer index){
        if (sost!=1){
            ib.setImageResource(nameImageOff[index]);
        }else{
            ib.setImageResource(nameImageOn[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iBfl1:
                v.startAnimation(animScale);
                writebase(0);
                break;
            case R.id.iBfl2:
                v.startAnimation(animScale);
                writebase(1);
                break;
            case R.id.iBfl3:
                v.startAnimation(animScale);
                writebase(2);
                break;
            case R.id.iBfl4:
                v.startAnimation(animScale);
                writebase(3);
                break;
            case R.id.iBfl5:
                v.startAnimation(animScale);
                writebase(4);
                break;
            case R.id.iBfl6:
                v.startAnimation(animScale);
                writebase(5);
                break;
            case R.id.iBfl7:
                v.startAnimation(animScale);
                writebase(6);
                break;
            case R.id.iBfl8:
                v.startAnimation(animScale);
                writebase(7);
                break;
            case R.id.iBfl9:
                v.startAnimation(animScale);
                writebase(8);
                break;
            case R.id.iBfl10:
                v.startAnimation(animScale);
                writebase(9);
                break;
        }
    }
    private void writebase(Integer id){
        Integer[] coll={0,0,0,0,0,0,0,0,0,0};
        coll[id]=1;
        mNames.child("logs").child("name").setValue(nickName);
        mNames.child("logs").child("carof").setValue(id);
        myRef.child(caronoff[0]).setValue(coll[0]);
        myRef.child(caronoff[1]).setValue(coll[1]);
        myRef.child(caronoff[2]).setValue(coll[2]);
        myRef.child(caronoff[3]).setValue(coll[3]);
        myRef.child(caronoff[4]).setValue(coll[4]);
        myRef.child(caronoff[5]).setValue(coll[5]);
        myRef.child(caronoff[6]).setValue(coll[6]);
        myRef.child(caronoff[7]).setValue(coll[7]);
        myRef.child(caronoff[8]).setValue(coll[8]);
        myRef.child(caronoff[9]).setValue(coll[9]);

    }

    @Override
    protected void onDestroy() {
        flKey=false;

        StartService();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        flKey=false;

        StartService();
        super.onPause();
    }
}
