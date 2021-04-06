package com.checker.tripletschecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.checker.tripletschecker.DBhelper.DbHelper;
import com.checker.tripletschecker.ListView.ListData;
import com.checker.tripletschecker.ListView.TrendAdapter;

import java.util.ArrayList;

public class TrendActivity extends AppCompatActivity {

    DbHelper db;
    ArrayList<ListData> DataList;
    ListView listView;
    TrendAdapter trendAdapter;
    String Activity_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);

        Intent intent = getIntent();

        Activity_Name = intent.getExtras().getString("Activity");

        if(Activity_Name.equals(new String("TwinsActivity"))) {
            db = new DbHelper(this, "twins");
        }else if (Activity_Name.equals(new String("TripletsActivity"))){
            db = new DbHelper(this, "triplets");
        }else if (Activity_Name.equals(new String("QuadrupletsActivity"))){
            db = new DbHelper(this, "quadruplets");
        }

        DataList = new ArrayList<ListData>();

        listView = (ListView)findViewById(R.id.v_ListView);

        viewData();

    }

    private void viewData() {
        Cursor cursor = db.viewData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                if(Activity_Name.equals(new String("TwinsActivity"))) {
                    DataList.add(new ListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)));
                }else if (Activity_Name.equals(new String("TripletsActivity"))){
                    DataList.add(new ListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                            cursor.getString(10), cursor.getString(11)));
                }else if (Activity_Name.equals(new String("QuadrupletsActivity"))){
                    DataList.add(new ListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                            cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14)));
                }
            }
            trendAdapter = new TrendAdapter(this, DataList);
            listView.setAdapter(trendAdapter);
        }
    }
}