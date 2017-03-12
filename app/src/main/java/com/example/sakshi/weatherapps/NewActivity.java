package com.example.sakshi.weatherapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sakshi on 10/3/17.
 */
public class NewActivity extends Activity implements View.OnClickListener {
    TextView tvcurrent,tvforecast,tvcity;
    String city;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        savedInstanceState=getIntent().getExtras();
        city=  savedInstanceState.getString("city");
        tvcity= (TextView) findViewById(R.id.textcity);
        tvcity.setText(city);
        tvforecast= (TextView) findViewById(R.id.tvforecast);
        tvcurrent= (TextView) findViewById(R.id.tvcurrent);
        tvcurrent.setOnClickListener(this);
        tvforecast.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvcurrent:
                Intent i=new Intent(this,Weather.class);
                i.putExtra("city",city);
                startActivity(i);
                break;
            case R.id.tvforecast:
                Intent i1=new Intent(this,Forecast.class);
                i1.putExtra("city",city);
                startActivity(i1);

                break;

        }
    }
}
