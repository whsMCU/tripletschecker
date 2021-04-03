package com.checker.tripletschecker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.checker.tripletschecker.DBhelper.DbHelper;
import com.checker.tripletschecker.ListView.ListData;
import com.checker.tripletschecker.ListView.TrendAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendActivity extends AppCompatActivity {

    DbHelper db;
    ArrayList<ListData> DataList;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        db = new DbHelper(this, "twins");
        this.InitializeData();

        ListView listView = (ListView)findViewById(R.id.v_ListView);
        final TrendAdapter trendAdapter = new TrendAdapter(this, DataList);

    }

    private void InitializeData() {
        data = db.getData(0);

        DataList = new ArrayList<ListData>();

        DataList.add(new ListData(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]));



    }
}