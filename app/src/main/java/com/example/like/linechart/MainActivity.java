package com.example.like.linechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.like.mylibrary.LineChartAdapter;
import com.example.like.mylibrary.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LineChartView mChartView;
    LineChartAdapter<Sale> adapter;
    List<Sale> sales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sales = new ArrayList<Sale>();
        sales.add(new Sale(0, "Mon", 20));
        sales.add(new Sale(0, "Tue", 40));
        sales.add(new Sale(0, "Wed", 79));
        sales.add(new Sale(0, "Thu", 10));
        sales.add(new Sale(0, "Fri", 35));
        sales.add(new Sale(0, "Sat", 41));
        sales.add(new Sale(0, "Sun", 13));
        setContentView(R.layout.activity_main);
        mChartView = (LineChartView) findViewById(R.id.line_chart_view);
        adapter = new LineChartAdapter<Sale>(sales) {

            @Override
            protected String getIndex(Sale s) {
                return s.getDate();
            }

            @Override
            protected int getValue(Sale s) {
                return s.getSaleNum();
            }
        };
        mChartView.setAdapter(adapter);
        mChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sales.clear();
                sales.add(new Sale(0, "Mon", 30));
                sales.add(new Sale(0, "Tue", 20));
                sales.add(new Sale(0, "Wed", 39));
                sales.add(new Sale(0, "Thu", 50));
                sales.add(new Sale(0, "Fri", 75));
                sales.add(new Sale(0, "Sat", 81));
                sales.add(new Sale(0, "Sun", 93));
                adapter.setmDatas(sales);
                adapter.notifyDataChanged();
            }
        });
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
}
