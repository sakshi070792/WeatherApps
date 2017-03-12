package com.example.sakshi.weatherapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sakshi on 12/3/17.
 */
public class TueRatedFragment extends Fragment {
    EditText etname;
    String city;
    Double lats, lons;
    List<Address> addressList = null;
    static TextView tvname, tvtemp;
    TextView tvdesc,tvhumid,tvday;
    Context c;
    ImageView imageView;


    Address address;
    ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tue,null);
        tvname = (TextView)view.findViewById(R.id.name);
        tvtemp = (TextView) view.findViewById(R.id.temp);
        tvday=(TextView) view.findViewById(R.id.day);
        tvdesc=(TextView) view.findViewById(R.id.desc);
        imageView= (ImageView) view.findViewById(R.id.imgv);


        tvhumid= (TextView) view.findViewById(R.id.humid);
        c = getActivity().getApplicationContext();


        String city=TabsPagerAdapter.city;

        new DownloadTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&mode=json&units=metric&cnt=3&&appid=3878b99de50af29d1368ae667ab1adb8");

        return view;


    }


    private class DownloadTask extends AsyncTask<String,Void,String> {
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("PLease Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();




        }






        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlconnection=null;


            try {
                url=new URL(urls[0]);
                urlconnection= (HttpURLConnection) url.openConnection();
                InputStream in=urlconnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray weatherArray = jsonObject.getJSONArray("list");
                JSONObject dayForecast = weatherArray.getJSONObject(1);
                long dateTime = dayForecast.getLong("dt");
                String day = getReadableDateString(dateTime);
                JSONObject temperatureObject = dayForecast.getJSONObject("temp");
                double high = temperatureObject.getDouble("max");
                double low = temperatureObject.getDouble("min");
                int humidi=Integer.parseInt(dayForecast.getString("humidity"));
                Log.i("show",day);
                JSONObject details=jsonObject.getJSONObject("city");
                String name=details.getString("name");
                JSONArray details2=dayForecast.getJSONArray("weather");
                JSONObject det=details2.getJSONObject(0);
                String description=det.getString("description");
                String icon=det.getString("icon");
                String url="http://openweathermap.org/img/w/"+icon+".png";
                Picasso.with(c).load(url)
                        .fit().into(imageView);


                String highAndLow = formatHighLows(high, low);
                tvname.setText("City: "+name);
                tvtemp.setText("HighTemp/LowTemp: "+highAndLow);
                tvhumid.setText("Humidity: "+humidi+"%");
                tvdesc.setText("Descripion: "+description);
                tvday.setText("Date: "+day);




            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private String getReadableDateString(long time) {
            Date date = new Date(time * 1000);
            SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
            return format.format(date).toString();
        }
        private String formatHighLows(double high, double low) {
// For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

    }
}
