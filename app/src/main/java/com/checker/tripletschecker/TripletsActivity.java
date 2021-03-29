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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TripletsActivity extends AppCompatActivity implements SettingFragment.Max_Movement_SetListener {

    private AdView mAdView;

    static final String KEY_TIME = "KEY_TIME";
    static final String KEY_NUM1 = "KEY_NUM1";
    static final String KEY_NUM2 = "KEY_NUM2";
    static final String KEY_NUM3 = "KEY_NUM3";

    static final String KEY_FINISH_TIME1 = "KEY_FINISH_TIME1";
    static final String KEY_FINISH_TIME2 = "KEY_FINISH_TIME2";
    static final String KEY_FINISH_TIME3 = "KEY_FINISH_TIME3";
    static final String KEY_TIME_DIFF1 = "KEY_TIME_DIFF1";
    static final String KEY_TIME_DIFF2 = "KEY_TIME_DIFF2";
    static final String KEY_TIME_DIFF3 = "KEY_TIME_DIFF3";

    static final String KEY_MAX_MOVEMENT = "KEY_MAX_MOVEMENT";

    long m_start = 0;
    long m_end1 = 0, m_end2 = 0, m_end3 = 0;
    int max_movement = 10;

    private long m_backKeyPressedTime = 0;

    TextView num1, num2, num3, start_time, finish_tim1, finish_tim2, finish_tim3, time_diff1, time_diff2, time_diff3;
    Button num1_add, num2_add, num3_add, num1_sub, num2_sub, num3_sub, start_button;

    int m_num1_count, m_num2_count, m_num3_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triplets_main);

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
        adView.setAdSize(AdSize.BANNER); //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("ca-app-pub-9708062265777742/4628892374");

        num1 = findViewById(R.id.number1_count);
        num2 = findViewById(R.id.number2_count);
        num3 = findViewById(R.id.number3_count);

        finish_tim1 = findViewById(R.id.finish_time1);
        finish_tim2 = findViewById(R.id.finish_time2);
        finish_tim3 = findViewById(R.id.finish_time3);

        time_diff1 = findViewById(R.id.time_diff1);
        time_diff2 = findViewById(R.id.time_diff2);
        time_diff3 = findViewById(R.id.time_diff3);

        start_time = findViewById(R.id.start_Time);

        num1_add = (Button) findViewById(R.id.number1_add);
        num2_add = (Button) findViewById(R.id.number2_add);
        num3_add = (Button) findViewById(R.id.number3_add);

        num1_sub = (Button) findViewById(R.id.number1_sub);
        num2_sub = (Button) findViewById(R.id.number2_sub);
        num3_sub = (Button) findViewById(R.id.number3_sub);

        start_button = (Button) findViewById(R.id.start_Button);

        if (savedInstanceState != null) {
            String time = savedInstanceState.getString(KEY_TIME);
            String data1 = savedInstanceState.getString(KEY_NUM1);
            String data2 = savedInstanceState.getString(KEY_NUM2);
            String data3 = savedInstanceState.getString(KEY_NUM3);
            m_start = Long.parseLong(time);
            start_time.setText(timeformet(m_start, "HH:mm:ss"));
            m_num1_count = Integer.parseInt(data1);
            m_num2_count = Integer.parseInt(data2);
            m_num3_count = Integer.parseInt(data3);
            num1.setText(data1);
            num2.setText(data2);
            num3.setText(data3);

            String finish_time1 = savedInstanceState.getString(KEY_FINISH_TIME1);
            String finish_time2 = savedInstanceState.getString(KEY_FINISH_TIME2);
            String finish_time3 = savedInstanceState.getString(KEY_FINISH_TIME3);
            String time_diff_1 = savedInstanceState.getString(KEY_TIME_DIFF1);
            String time_diff_2 = savedInstanceState.getString(KEY_TIME_DIFF2);
            String time_diff_3 = savedInstanceState.getString(KEY_TIME_DIFF3);
            finish_tim1.setText(finish_time1);
            finish_tim2.setText(finish_time2);
            finish_tim3.setText(finish_time3);
            time_diff1.setText(time_diff_1);
            time_diff2.setText(time_diff_2);
            time_diff3.setText(time_diff_3);

            max_movement = savedInstanceState.getInt(KEY_MAX_MOVEMENT);
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
                            m_num1_count += 1;
                        }
                        num1.setText(Integer.toString(m_num1_count));
                        if (m_num1_count == max_movement) {
                            m_end1 = System.currentTimeMillis();
                            finish_tim1.setText(timeformet(m_end1, "HH:mm:ss"));
                            time_diff1.setText(time_diff(m_start, m_end1));
                        }
                        break;

                    case R.id.number2_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num2_count < max_movement) {
                            m_num2_count += 1;
                        }
                        num2.setText(Integer.toString(m_num2_count));
                        if (m_num2_count == max_movement) {
                            m_end2 = System.currentTimeMillis();
                            finish_tim2.setText(timeformet(m_end2, "HH:mm:ss"));
                            time_diff2.setText(time_diff(m_start, m_end2));
                        }
                        break;

                    case R.id.number3_add:
                        if(m_start == 0){
                            Toast.makeText(getApplicationContext(), R.string.start_button_press, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (m_num3_count < max_movement) {
                            m_num3_count += 1;
                        }
                        num3.setText(Integer.toString(m_num3_count));
                        if (m_num3_count == max_movement) {
                            m_end3 = System.currentTimeMillis();
                            finish_tim3.setText(timeformet(m_end3, "HH:mm:ss"));
                            time_diff3.setText(time_diff(m_start, m_end3));
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
                }
            }
        };

        num1_add.setOnClickListener(Listener);
        num2_add.setOnClickListener(Listener);
        num3_add.setOnClickListener(Listener);
        num1_sub.setOnClickListener(Listener);
        num2_sub.setOnClickListener(Listener);
        num3_sub.setOnClickListener(Listener);
        start_button.setOnClickListener(Listener);
    }

    private void restoreData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TripletsActivity", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String time = sharedPreferences.getString(KEY_TIME, "");
            String data1 = sharedPreferences.getString(KEY_NUM1, "");
            String data2 = sharedPreferences.getString(KEY_NUM2, "");
            String data3 = sharedPreferences.getString(KEY_NUM3, "");
            if (time==""||data1==""||data2==""||data3=="") {
                m_start = 0;
                m_num1_count = 0;
                m_num2_count = 0;
                m_num3_count = 0;
            } else {
                m_start = Long.parseLong(time);
                start_time.setText(timeformet(m_start, "HH:mm:ss"));
                m_num1_count = Integer.parseInt(data1);
                m_num2_count = Integer.parseInt(data2);
                m_num3_count = Integer.parseInt(data3);
                num1.setText(data1);
                num2.setText(data2);
                num3.setText(data3);
            }
            String finish_time1 = sharedPreferences.getString(KEY_FINISH_TIME1, "");
            String finish_time2 = sharedPreferences.getString(KEY_FINISH_TIME2, "");
            String finish_time3 = sharedPreferences.getString(KEY_FINISH_TIME3, "");
            String time_diff_1 = sharedPreferences.getString(KEY_TIME_DIFF1, "");
            String time_diff_2 = sharedPreferences.getString(KEY_TIME_DIFF2, "");
            String time_diff_3 = sharedPreferences.getString(KEY_TIME_DIFF3, "");

            finish_tim1.setText(finish_time1);
            finish_tim2.setText(finish_time2);
            finish_tim3.setText(finish_time3);
            time_diff1.setText(time_diff_1);
            time_diff2.setText(time_diff_2);
            time_diff3.setText(time_diff_3);

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

        start_time.setText("");
        num1.setText(Integer.toString(m_num1_count));
        num2.setText(Integer.toString(m_num2_count));
        num3.setText(Integer.toString(m_num3_count));

        finish_tim1.setText("");
        finish_tim2.setText("");
        finish_tim3.setText("");
        time_diff1.setText("");
        time_diff2.setText("");
        time_diff3.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.description:
                Intent intent = new Intent(this, DescriptionActivity.class);
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
        SharedPreferences sharedPreferences = getSharedPreferences("TripletsActivity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String time = Long.toString(m_start);
        String data1 = num1.getText().toString();
        String data2 = num2.getText().toString();
        String data3 = num3.getText().toString();
        editor.putString("KEY_TIME", time);
        editor.putString("KEY_NUM1", data1);
        editor.putString("KEY_NUM2", data2);
        editor.putString("KEY_NUM3", data3);

        String finish_tim1_data1 = finish_tim1.getText().toString();
        String finish_tim1_data2 = finish_tim2.getText().toString();
        String finish_tim1_data3 = finish_tim3.getText().toString();
        String time_diff1_data1 = time_diff1.getText().toString();
        String time_diff1_data2 = time_diff2.getText().toString();
        String time_diff1_data3 = time_diff3.getText().toString();
        editor.putString("KEY_FINISH_TIME1", finish_tim1_data1);
        editor.putString("KEY_FINISH_TIME2", finish_tim1_data2);
        editor.putString("KEY_FINISH_TIME3", finish_tim1_data3);
        editor.putString("KEY_TIME_DIFF1", time_diff1_data1);
        editor.putString("KEY_TIME_DIFF2", time_diff1_data2);
        editor.putString("KEY_TIME_DIFF3", time_diff1_data3);

        editor.putInt("KEY_MAX_MOVEMENT", max_movement);

        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String time = Long.toString(m_start);
        String data1 = num1.getText().toString();
        String data2 = num2.getText().toString();
        String data3 = num3.getText().toString();
        outState.putString(KEY_TIME, time);
        outState.putString(KEY_NUM1, data1);
        outState.putString(KEY_NUM2, data2);
        outState.putString(KEY_NUM3, data3);

        String finish_tim1_data1 = finish_tim1.getText().toString();
        String finish_tim1_data2 = finish_tim2.getText().toString();
        String finish_tim1_data3 = finish_tim3.getText().toString();
        String time_diff1_data1 = time_diff1.getText().toString();
        String time_diff1_data2 = time_diff2.getText().toString();
        String time_diff1_data3 = time_diff3.getText().toString();
        outState.putString(KEY_FINISH_TIME1, finish_tim1_data1);
        outState.putString(KEY_FINISH_TIME2, finish_tim1_data2);
        outState.putString(KEY_FINISH_TIME3, finish_tim1_data3);
        outState.putString(KEY_TIME_DIFF1, time_diff1_data1);
        outState.putString(KEY_TIME_DIFF2, time_diff1_data2);
        outState.putString(KEY_TIME_DIFF3, time_diff1_data3);

        outState.putInt(KEY_MAX_MOVEMENT, max_movement);
    }

    @Override
    public void onBackPressed() {
        //기존의 뒤로가기 버튼의 기능 제거
        //super.onBackPressed();

        if (System.currentTimeMillis() > m_backKeyPressedTime + 2000) {
            m_backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.back_button, Toast.LENGTH_SHORT).show();
            return;
        }

        //1초 이내에 뒤로가기 버튼을 한번 더 클릭시 finish()
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
                num1.setText(Integer.toString(m_num1_count));
                num2.setText(Integer.toString(m_num2_count));
                num3.setText(Integer.toString(m_num3_count));

                finish_tim1.setText("");
                finish_tim2.setText("");
                finish_tim3.setText("");
                time_diff1.setText("");
                time_diff2.setText("");
                time_diff3.setText("");

                m_start = System.currentTimeMillis();
                start_time.setText(timeformet(m_start, "HH:mm:ss"));
                Toast.makeText(TripletsActivity.this, R.string.start_message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TripletsActivity.this, R.string.reset_cancel, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    String time_diff(long start_time, long m_end_time) {
        long diff = (m_end_time - start_time)/1000;

        long hour = diff / 60 / 60;
        long min = (diff / 60) % 60;
        long sec = diff % 60;

        String returnTime = hour + ":" + min + ":" + sec;
        return returnTime;
    }

    String timeformet(long time, String format){
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}