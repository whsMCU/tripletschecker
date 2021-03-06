package com.checker.tripletschecker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.checker.tripletschecker.DBhelper.DbHelper;
import com.checker.tripletschecker.Dialog.DialogFragment;
import com.checker.tripletschecker.Dialog.SettingFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class QuadrupletsActivity extends AppCompatActivity implements SettingFragment.Max_Movement_SetListener, DialogFragment.Save_Listener{

    private AdView mAdView;

    DbHelper db;

    static final String KEY_TIME = "KEY_TIME";
    static final String KEY_NUM1 = "KEY_NUM1";
    static final String KEY_NUM2 = "KEY_NUM2";
    static final String KEY_NUM3 = "KEY_NUM3";
    static final String KEY_NUM4 = "KEY_NUM4";

    static final String KEY_FINISH_TIME1 = "KEY_FINISH_TIME1";
    static final String KEY_FINISH_TIME2 = "KEY_FINISH_TIME2";
    static final String KEY_FINISH_TIME3 = "KEY_FINISH_TIME3";
    static final String KEY_FINISH_TIME4 = "KEY_FINISH_TIME4";

    static final String KEY_TIME_DIFF1 = "KEY_TIME_DIFF1";
    static final String KEY_TIME_DIFF2 = "KEY_TIME_DIFF2";
    static final String KEY_TIME_DIFF3 = "KEY_TIME_DIFF3";
    static final String KEY_TIME_DIFF4 = "KEY_TIME_DIFF4";

    static final String KEY_MAX_MOVEMENT = "KEY_MAX_MOVEMENT";

    long m_start = 0;
    long m_end1 = 0, m_end2 = 0, m_end3 = 0, m_end4 = 0;
    int max_movement = 10;
    boolean add1_max_flag, add2_max_flag, add3_max_flag, add4_max_flag, add_db_flag;

    private long m_backKeyPressedTime = 0;

    TextView num1, num2, num3, num4, start_time, finish_tim1, finish_tim2, finish_tim3, finish_tim4, time_diff1, time_diff2, time_diff3, time_diff4;
    Button num1_add, num2_add, num3_add, num4_add, num1_sub, num2_sub, num3_sub, num4_sub, start_button;

    int m_num1_count, m_num2_count, m_num3_count, m_num4_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quadruplets_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER); //?????? ???????????? ?????? ???????????? ??????
        adView.setAdUnitId("ca-app-pub-9708062265777742/4628892374");

        num1 = findViewById(R.id.number1_count);
        num2 = findViewById(R.id.number2_count);
        num3 = findViewById(R.id.number3_count);
        num4 = findViewById(R.id.number4_count);

        finish_tim1 = findViewById(R.id.finish_time1);
        finish_tim2 = findViewById(R.id.finish_time2);
        finish_tim3 = findViewById(R.id.finish_time3);
        finish_tim4 = findViewById(R.id.finish_time4);

        time_diff1 = findViewById(R.id.time_diff1);
        time_diff2 = findViewById(R.id.time_diff2);
        time_diff3 = findViewById(R.id.time_diff3);
        time_diff4 = findViewById(R.id.time_diff4);

        start_time = findViewById(R.id.start_Time);

        num1_add = (Button) findViewById(R.id.number1_add);
        num2_add = (Button) findViewById(R.id.number2_add);
        num3_add = (Button) findViewById(R.id.number3_add);
        num4_add = (Button) findViewById(R.id.number4_add);

        num1_sub = (Button) findViewById(R.id.number1_sub);
        num2_sub = (Button) findViewById(R.id.number2_sub);
        num3_sub = (Button) findViewById(R.id.number3_sub);
        num4_sub = (Button) findViewById(R.id.number4_sub);

        start_button = (Button) findViewById(R.id.start_Button);

        add1_max_flag = true;
        add2_max_flag = true;
        add3_max_flag = true;
        add4_max_flag = true;
        add_db_flag = true;

        db = new DbHelper(this, "quadruplets");

        if (savedInstanceState != null) {
            String time = savedInstanceState.getString(KEY_TIME);
            String data1 = savedInstanceState.getString(KEY_NUM1);
            String data2 = savedInstanceState.getString(KEY_NUM2);
            String data3 = savedInstanceState.getString(KEY_NUM3);
            String data4 = savedInstanceState.getString(KEY_NUM4);
            m_start = Long.parseLong(time);
            start_time.setText(timeformat(m_start, "HH:mm:ss"));
            m_num1_count = Integer.parseInt(data1);
            m_num2_count = Integer.parseInt(data2);
            m_num3_count = Integer.parseInt(data3);
            m_num4_count = Integer.parseInt(data4);
            num1.setText(data1);
            num2.setText(data2);
            num3.setText(data3);
            num4.setText(data4);

            String finish_time1 = savedInstanceState.getString(KEY_FINISH_TIME1);
            String finish_time2 = savedInstanceState.getString(KEY_FINISH_TIME2);
            String finish_time3 = savedInstanceState.getString(KEY_FINISH_TIME3);
            String finish_time4 = savedInstanceState.getString(KEY_FINISH_TIME4);
            String time_diff_1 = savedInstanceState.getString(KEY_TIME_DIFF1);
            String time_diff_2 = savedInstanceState.getString(KEY_TIME_DIFF2);
            String time_diff_3 = savedInstanceState.getString(KEY_TIME_DIFF3);
            String time_diff_4 = savedInstanceState.getString(KEY_TIME_DIFF4);
            finish_tim1.setText(finish_time1);
            finish_tim2.setText(finish_time2);
            finish_tim3.setText(finish_time3);
            finish_tim4.setText(finish_time4);
            time_diff1.setText(time_diff_1);
            time_diff2.setText(time_diff_2);
            time_diff3.setText(time_diff_3);
            time_diff4.setText(time_diff_4);

            max_movement = savedInstanceState.getInt(KEY_MAX_MOVEMENT, 10);
        }
        restoreData();

        View.OnClickListener Listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start_Button:
                        showMessage_Start();
                        break;

                    case R.id.number1_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num1_count < max_movement) {
                            add1_max_flag = false;
                            add_db_flag = false;
                            m_num1_count += 1;
                        }
                        num1.setText(Integer.toString(m_num1_count));
                        if (m_num1_count == max_movement && add1_max_flag == false) {
                            add1_max_flag = true;
                            m_end1 = System.currentTimeMillis();
                            finish_tim1.setText(timeformat(m_end1, "HH:mm:ss"));
                            time_diff1.setText(time_diff(m_start, m_end1));
                        }
                        if(m_num1_count == max_movement && m_num2_count == max_movement && m_num3_count == max_movement && m_num4_count == max_movement && add_db_flag == false){
                            add_db_flag = true;
                            showMessage_Save();
                        }
                        break;

                    case R.id.number2_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num2_count < max_movement) {
                            add2_max_flag = false;
                            add_db_flag = false;
                            m_num2_count += 1;
                        }
                        num2.setText(Integer.toString(m_num2_count));
                        if (m_num2_count == max_movement && add2_max_flag == false) {
                            add2_max_flag = true;
                            m_end2 = System.currentTimeMillis();
                            finish_tim2.setText(timeformat(m_end2, "HH:mm:ss"));
                            time_diff2.setText(time_diff(m_start, m_end2));
                        }
                        if(m_num1_count == max_movement && m_num2_count == max_movement && m_num3_count == max_movement && m_num4_count == max_movement && add_db_flag == false){
                            add_db_flag = true;
                            showMessage_Save();
                        }
                        break;

                    case R.id.number3_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num3_count < max_movement) {
                            add3_max_flag = false;
                            add_db_flag = false;
                            m_num3_count += 1;
                        }
                        num3.setText(Integer.toString(m_num3_count));
                        if (m_num3_count == max_movement && add3_max_flag == false) {
                            add3_max_flag = true;
                            m_end3 = System.currentTimeMillis();
                            finish_tim3.setText(timeformat(m_end3, "HH:mm:ss"));
                            time_diff3.setText(time_diff(m_start, m_end3));
                        }
                        if(m_num1_count == max_movement && m_num2_count == max_movement && m_num3_count == max_movement && m_num4_count == max_movement && add_db_flag == false){
                            add_db_flag = true;
                            showMessage_Save();
                        }
                        break;

                    case R.id.number4_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num4_count < max_movement) {
                            add4_max_flag = false;
                            add_db_flag = false;
                            m_num4_count += 1;
                        }
                        num4.setText(Integer.toString(m_num4_count));
                        if (m_num4_count == max_movement && add4_max_flag == false) {
                            add4_max_flag = true;
                            m_end4 = System.currentTimeMillis();
                            finish_tim4.setText(timeformat(m_end4, "HH:mm:ss"));
                            time_diff4.setText(time_diff(m_start, m_end4));
                        }
                        if(m_num1_count == max_movement && m_num2_count == max_movement && m_num3_count == max_movement && m_num4_count == max_movement && add_db_flag == false){
                            add_db_flag = true;
                            showMessage_Save();
                        }
                        break;

                    case R.id.number1_sub:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num1_count > 0) {
                            m_num1_count -= 1;
                        }
                        if (m_num1_count < max_movement){
                            finish_tim1.setText("");
                            time_diff1.setText("");
                        }
                        num1.setText(Integer.toString(m_num1_count));
                        break;

                    case R.id.number2_sub:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num2_count > 0) {
                            m_num2_count -= 1;
                        }
                        if (m_num2_count < max_movement){
                            finish_tim2.setText("");
                            time_diff2.setText("");
                        }
                        num2.setText(Integer.toString(m_num2_count));
                        break;

                    case R.id.number3_sub:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num3_count > 0) {
                            m_num3_count -= 1;
                        }
                        if (m_num3_count < max_movement){
                            finish_tim3.setText("");
                            time_diff3.setText("");
                        }
                        num3.setText(Integer.toString(m_num3_count));
                        break;

                    case R.id.number4_sub:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num4_count > 0) {
                            m_num4_count -= 1;
                        }
                        if (m_num4_count < max_movement){
                            finish_tim4.setText("");
                            time_diff4.setText("");
                        }
                        num4.setText(Integer.toString(m_num4_count));
                        break;
                }
            }
        };

        num1_add.setOnClickListener(Listener);
        num2_add.setOnClickListener(Listener);
        num3_add.setOnClickListener(Listener);
        num4_add.setOnClickListener(Listener);
        num1_sub.setOnClickListener(Listener);
        num2_sub.setOnClickListener(Listener);
        num3_sub.setOnClickListener(Listener);
        num4_sub.setOnClickListener(Listener);
        start_button.setOnClickListener(Listener);
    }

    private void restoreData() {
        SharedPreferences sharedPreferences = getSharedPreferences("QuadrupletsActivity", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String time = sharedPreferences.getString(KEY_TIME, "");
            String data1 = sharedPreferences.getString(KEY_NUM1, "");
            String data2 = sharedPreferences.getString(KEY_NUM2, "");
            String data3 = sharedPreferences.getString(KEY_NUM3, "");
            String data4 = sharedPreferences.getString(KEY_NUM4, "");
            if (time==""||data1==""||data2==""||data3==""||data4=="") {
                m_start = 0;
                m_num1_count = 0;
                m_num2_count = 0;
                m_num3_count = 0;
                m_num4_count = 0;
            } else {
                m_start = Long.parseLong(time);
                start_time.setText(timeformat(m_start, "HH:mm:ss"));
                m_num1_count = Integer.parseInt(data1);
                m_num2_count = Integer.parseInt(data2);
                m_num3_count = Integer.parseInt(data3);
                m_num4_count = Integer.parseInt(data4);
                num1.setText(data1);
                num2.setText(data2);
                num3.setText(data3);
                num4.setText(data4);
            }
            String finish_time1 = sharedPreferences.getString(KEY_FINISH_TIME1, "");
            String finish_time2 = sharedPreferences.getString(KEY_FINISH_TIME2, "");
            String finish_time3 = sharedPreferences.getString(KEY_FINISH_TIME3, "");
            String finish_time4 = sharedPreferences.getString(KEY_FINISH_TIME4, "");
            String time_diff_1 = sharedPreferences.getString(KEY_TIME_DIFF1, "");
            String time_diff_2 = sharedPreferences.getString(KEY_TIME_DIFF2, "");
            String time_diff_3 = sharedPreferences.getString(KEY_TIME_DIFF3, "");
            String time_diff_4 = sharedPreferences.getString(KEY_TIME_DIFF4, "");

            finish_tim1.setText(finish_time1);
            finish_tim2.setText(finish_time2);
            finish_tim3.setText(finish_time3);
            finish_tim4.setText(finish_time4);
            time_diff1.setText(time_diff_1);
            time_diff2.setText(time_diff_2);
            time_diff3.setText(time_diff_3);
            time_diff4.setText(time_diff_4);

            max_movement = sharedPreferences.getInt(KEY_MAX_MOVEMENT, 10);
        }
    }

    @Override
    public void onMax_Movement_Set(int max_movement) {
        this.max_movement = max_movement;
        m_start = 0;
        m_num1_count = 0;
        m_num2_count = 0;
        m_num3_count = 0;
        m_num4_count = 0;

        start_time.setText("");
        num1.setText(Integer.toString(m_num1_count));
        num2.setText(Integer.toString(m_num2_count));
        num3.setText(Integer.toString(m_num3_count));
        num4.setText(Integer.toString(m_num4_count));

        finish_tim1.setText("");
        finish_tim2.setText("");
        finish_tim3.setText("");
        finish_tim4.setText("");
        time_diff1.setText("");
        time_diff2.setText("");
        time_diff3.setText("");
        time_diff4.setText("");
    }

    @Override
    public void onSave_Set(boolean m_save) {
        if(m_save == true) {
            String date, count1, count2, count3, count4, start_time, end_time1, end_time2, end_time3, end_time4, duration1, duration2, duration3, duration4;
            date = timeformat(m_start, "yy.M.d.");
            count1 = Integer.toString(m_num1_count);
            count2 = Integer.toString(m_num2_count);
            count3 = Integer.toString(m_num3_count);
            count4 = Integer.toString(m_num4_count);
            start_time = timeformat(m_start, "HH:mm:ss");
            end_time1 = timeformat(m_end1, "HH:mm:ss");
            end_time2 = timeformat(m_end2, "HH:mm:ss");
            end_time3 = timeformat(m_end3, "HH:mm:ss");
            end_time4 = timeformat(m_end4, "HH:mm:ss");
            duration1 = time_diff1.getText().toString();
            duration2 = time_diff2.getText().toString();
            duration3 = time_diff3.getText().toString();
            duration4 = time_diff4.getText().toString();
            db.insertData(date, count1, count2, count3, count4, start_time, end_time1, end_time2, end_time3, end_time4, duration1, duration2, duration3, duration4);
            Toast.makeText(QuadrupletsActivity.this, R.string.db_save, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.description:
                intent = new Intent(this, DescriptionActivity.class);
                startActivity(intent);
                break;

            case R.id.trend:
                intent = new Intent(this, TrendActivity.class);
                intent.putExtra("Activity", "QuadrupletsActivity");
                startActivity(intent);
                break;

            case R.id.setting:
                new SettingFragment().show(getSupportFragmentManager(), "SettingFragment");
                break;

            case R.id.logout:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("QuadrupletsActivity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String time = Long.toString(m_start);
        String data1 = num1.getText().toString();
        String data2 = num2.getText().toString();
        String data3 = num3.getText().toString();
        String data4 = num4.getText().toString();
        editor.putString("KEY_TIME", time);
        editor.putString("KEY_NUM1", data1);
        editor.putString("KEY_NUM2", data2);
        editor.putString("KEY_NUM3", data3);
        editor.putString("KEY_NUM4", data4);

        String finish_tim1_data1 = finish_tim1.getText().toString();
        String finish_tim1_data2 = finish_tim2.getText().toString();
        String finish_tim1_data3 = finish_tim3.getText().toString();
        String finish_tim1_data4 = finish_tim4.getText().toString();
        String time_diff1_data1 = time_diff1.getText().toString();
        String time_diff1_data2 = time_diff2.getText().toString();
        String time_diff1_data3 = time_diff3.getText().toString();
        String time_diff1_data4 = time_diff4.getText().toString();
        editor.putString("KEY_FINISH_TIME1", finish_tim1_data1);
        editor.putString("KEY_FINISH_TIME2", finish_tim1_data2);
        editor.putString("KEY_FINISH_TIME3", finish_tim1_data3);
        editor.putString("KEY_FINISH_TIME4", finish_tim1_data4);
        editor.putString("KEY_TIME_DIFF1", time_diff1_data1);
        editor.putString("KEY_TIME_DIFF2", time_diff1_data2);
        editor.putString("KEY_TIME_DIFF3", time_diff1_data3);
        editor.putString("KEY_TIME_DIFF4", time_diff1_data4);

        editor.putInt("KEY_MAX_MOVEMENT", max_movement);

        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String time = Long.toString(m_start);
        String num_data1 = num1.getText().toString();
        String num_data2 = num2.getText().toString();
        String num_data3 = num3.getText().toString();
        String num_data4 = num4.getText().toString();
        outState.putString(KEY_TIME, time);
        outState.putString(KEY_NUM1, num_data1);
        outState.putString(KEY_NUM2, num_data2);
        outState.putString(KEY_NUM3, num_data3);
        outState.putString(KEY_NUM4, num_data4);

        String finish_tim1_data1 = finish_tim1.getText().toString();
        String finish_tim1_data2 = finish_tim2.getText().toString();
        String finish_tim1_data3 = finish_tim3.getText().toString();
        String finish_tim1_data4 = finish_tim4.getText().toString();

        String time_diff1_data1 = time_diff1.getText().toString();
        String time_diff1_data2 = time_diff2.getText().toString();
        String time_diff1_data3 = time_diff3.getText().toString();
        String time_diff1_data4 = time_diff4.getText().toString();
        outState.putString(KEY_FINISH_TIME1, finish_tim1_data1);
        outState.putString(KEY_FINISH_TIME2, finish_tim1_data2);
        outState.putString(KEY_FINISH_TIME3, finish_tim1_data3);
        outState.putString(KEY_FINISH_TIME4, finish_tim1_data4);
        outState.putString(KEY_TIME_DIFF1, time_diff1_data1);
        outState.putString(KEY_TIME_DIFF2, time_diff1_data2);
        outState.putString(KEY_TIME_DIFF3, time_diff1_data3);
        outState.putString(KEY_TIME_DIFF4, time_diff1_data4);

        outState.putInt(KEY_MAX_MOVEMENT, max_movement);
    }

    @Override
    public void onBackPressed() {
        //????????? ???????????? ????????? ?????? ??????
        //super.onBackPressed();

        if (System.currentTimeMillis() > m_backKeyPressedTime + 2000) {
            m_backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.back_button, Toast.LENGTH_SHORT).show();
            return;
        }

        //1??? ????????? ???????????? ????????? ?????? ??? ????????? finish()
        if (System.currentTimeMillis() <= m_backKeyPressedTime + 2000) {
            finish();
        }
    }

    void showMessage_Start() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.alert_message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                m_start = 0;
                m_num1_count = 0;
                m_num2_count = 0;
                m_num3_count = 0;
                m_num4_count = 0;
                num1.setText(Integer.toString(m_num1_count));
                num2.setText(Integer.toString(m_num2_count));
                num3.setText(Integer.toString(m_num3_count));
                num4.setText(Integer.toString(m_num4_count));

                finish_tim1.setText("");
                finish_tim2.setText("");
                finish_tim3.setText("");
                finish_tim4.setText("");
                time_diff1.setText("");
                time_diff2.setText("");
                time_diff3.setText("");
                time_diff4.setText("");

                m_start = System.currentTimeMillis();
                start_time.setText(timeformat(m_start, "HH:mm:ss"));
                Toast.makeText(QuadrupletsActivity.this, R.string.start_message, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(QuadrupletsActivity.this, R.string.reset_cancel, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMessage_Save() {
        new DialogFragment().show(getSupportFragmentManager(),"DialogFragment");
    }

    String time_diff(long start_time, long m_end_time) {
        long diff = (m_end_time - start_time)/1000;

        long hour = diff / 60 / 60;
        long min = (diff / 60) % 60;
        long sec = diff % 60;

        String returnTime = hour + ":" + min + ":" + sec;
        return returnTime;
    }

    String timeformat(long time, String format){
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}