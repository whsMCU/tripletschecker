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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);

        Intent intent = getIntent();

        db = (DbHelper) intent.getSerializableExtra("DB");

        DataList = new ArrayList<ListData>();

        listView = (ListView)findViewById(R.id.v_ListView);
        trendAdapter = new TrendAdapter(this, DataList);

        viewData();

    }

    private void viewData() {
        Cursor cursor = db.viewData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                DataList.add(new ListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)));
            }
            trendAdapter = new TrendAdapter(this, DataList);
            listView.setAdapter(trendAdapter);
        }
    }
}