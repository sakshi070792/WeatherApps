package com.example.sakshi.weatherapps;

/**
 * Created by sakshi on 10/3/17.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sakshi on 8/3/17.
 */
public class Forecast extends AppCompatActivity {
    private ViewPager viewpager;
    private TabsPagerAdapter madapter;
    String city;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast);
        viewpager = (ViewPager) findViewById(R.id.pager);
        savedInstanceState=getIntent().getExtras();
        city= savedInstanceState.getString("city");

        madapter = new TabsPagerAdapter(getSupportFragmentManager(),city);
        viewpager.setAdapter(madapter);
    }
}
