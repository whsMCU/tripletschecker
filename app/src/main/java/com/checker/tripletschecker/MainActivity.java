package com.checker.tripletschecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    static final String KEY_TIME = "KEY_TIME";
    static final String KEY_NUM1 = "KEY_NUM1";
    static final String KEY_NUM2 = "KEY_NUM2";
    static final String KEY_NUM3 = "KEY_NUM3";

    static final String KEY_NUM4 = "KEY_NUM4";
    static final String KEY_NUM5 = "KEY_NUM5";
    static final String KEY_NUM6 = "KEY_NUM6";
    static final String KEY_NUM7 = "KEY_NUM7";
    static final String KEY_NUM8 = "KEY_NUM8";
    static final String KEY_NUM9 = "KEY_NUM9";

    long m_start = 0;
    long m_end1 = 0, m_end2 = 0, m_end3 = 0;

    private long m_backKeyPressedTime = 0;

    TextView num1, num2, num3, start_time, finish_tim1, finish_tim2, finish_tim3, time_diff1, time_diff2, time_diff3;
    Button num1_add, num2_add, num3_add, num1_sub, num2_sub, num3_sub, start_button;

    int m_num1_count, m_num2_count, m_num3_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
            Date date = new Date(m_start);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            start_time.setText(dateFormat.format(date));
            m_num1_count = Integer.parseInt(data1);
            m_num2_count = Integer.parseInt(data2);
            m_num3_count = Integer.parseInt(data3);
            num1.setText(data1);
            num2.setText(data2);
            num3.setText(data3);

            String data4 = savedInstanceState.getString(KEY_NUM4);
            String data5 = savedInstanceState.getString(KEY_NUM5);
            String data6 = savedInstanceState.getString(KEY_NUM6);
            String data7 = savedInstanceState.getString(KEY_NUM7);
            String data8 = savedInstanceState.getString(KEY_NUM8);
            String data9 = savedInstanceState.getString(KEY_NUM9);
            finish_tim1.setText(data4);
            finish_tim2.setText(data5);
            finish_tim3.setText(data6);
            time_diff1.setText(data7);
            time_diff2.setText(data8);
            time_diff3.setText(data9);

        }

        View.OnClickListener Listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start_Button:
                        showMessage_Start();
                        break;

                    case R.id.number1_add:
                        if (m_num1_count < 10) {
                            m_num1_count += 1;
                        }
                        num1.setText(Integer.toString(m_num1_count));
                        Log.d("test", "num1_add");
                        if (m_num1_count == 10) {
                            m_end1 = System.currentTimeMillis();
                            finish_tim1.setText(timeformat(m_end1, "HH:mm:ss"));
                            time_diff1.setText(time_diff(m_start, m_end1));
                        }
                        break;

                    case R.id.number2_add:
                        if (m_num2_count < 10) {
                            m_num2_count += 1;
                        }
                        num2.setText(Integer.toString(m_num2_count));
                        Log.d("test", "num2_add");
                        if (m_num2_count == 10) {
                            m_end2 = System.currentTimeMillis();
                            finish_tim2.setText(timeformat(m_end2, "HH:mm:ss"));
                            time_diff2.setText(time_diff(m_start, m_end2));
                        }
                        break;

                    case R.id.number3_add:
                        if (m_num3_count < 10) {
                            m_num3_count += 1;
                        }
                        num3.setText(Integer.toString(m_num3_count));
                        Log.d("test", "num3_add");
                        if (m_num3_count == 10) {
                            m_end3 = System.currentTimeMillis();
                            finish_tim3.setText(timeformat(m_end3, "HH:mm:ss"));
                            time_diff3.setText(time_diff(m_start, m_end3));
                        }
                        break;

                    case R.id.number1_sub:
                        if (m_num1_count > 0) {
                            m_num1_count -= 1;
                        }
                        num1.setText(Integer.toString(m_num1_count));
                        Log.d("test", "num1_sub");
                        break;

                    case R.id.number2_sub:
                        if (m_num2_count > 0) {
                            m_num2_count -= 1;
                        }
                        num2.setText(Integer.toString(m_num2_count));
                        Log.d("test", "num2_sub");
                        break;

                    case R.id.number3_sub:
                        if (m_num3_count > 0) {
                            m_num3_count -= 1;
                        }
                        num3.setText(Integer.toString(m_num3_count));
                        Log.d("test", "num3_sub");
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

        String data4 = finish_tim1.getText().toString();
        String data5 = finish_tim2.getText().toString();
        String data6 = finish_tim3.getText().toString();
        String data7 = time_diff1.getText().toString();
        String data8 = time_diff2.getText().toString();
        String data9 = time_diff3.getText().toString();
        outState.putString(KEY_NUM4, data4);
        outState.putString(KEY_NUM5, data5);
        outState.putString(KEY_NUM6, data6);
        outState.putString(KEY_NUM7, data7);
        outState.putString(KEY_NUM8, data8);
        outState.putString(KEY_NUM9, data9);
    }

    @Override
    public void onBackPressed() {
        //기존의 뒤로가기 버튼의 기능 제거
        //super.onBackPressed();

        if (System.currentTimeMillis() > m_backKeyPressedTime + 2000) {
            m_backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //2초 이내에 뒤로가기 버튼을 한번 더 클릭시 finish()
        if (System.currentTimeMillis() <= m_backKeyPressedTime + 2000) {
            finish();
        }
    }

    void showMessage_Start() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("앗!");
        builder.setMessage("정말 시작 또는 리셋 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                start_time.setText(timeformat(m_start, "HH:mm:ss"));
                Toast.makeText(MainActivity.this, "리셋하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "리셋을 취소하였습니다.", Toast.LENGTH_SHORT).show();
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

    String timeformat(long time, String format){
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}