package com.vbhp.app.duan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName, txtCoutry, txtTemp, txtStatus, txtHumidity, txtCound, txtWind, txtDay;
    ImageView imgIcon;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetCurrentWeatherData("Saigon");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                if (city.equals("")){
                    City = "Saigon";
                    GetCurrentWeatherData(City);
                }else {
                    City = city;
                    GetCurrentWeatherData(City);
                }
            }
        });

        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    public  void  GetCurrentWeatherData(final String data){
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://samples.openweathermap.org/data/2.5/weather?q="+ data +"&appid=7955bc4a07cdb01248177ad96112bc5b";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Tên thành phố: " +  name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss");
                            String Day = simpleDateFormat.format(date);

                            txtDay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String stautus = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("https://openweathermap.org/img/w/" +  icon + ".png").into(imgIcon);
                            txtStatus.setText(stautus);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo  + "C");
                            txtHumidity.setText(doam + "%");

                            JSONObject jsonObjectClounds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClounds.getString("all");
                            txtCound.setText(may + "%");

                            JSONObject jsonObject1Wind = jsonObject.getJSONObject("wind");
                            String gio = jsonObject1Wind.getString("speed");
                            txtWind.setText(gio + "m/s");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCoutry.setText("Tên quốc gia: " + country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        edtSearch = findViewById(R.id.edittextSearch);
        btnSearch = findViewById(R.id.buttonSearch);
        btnChangeActivity = findViewById(R.id.buttonChangeActivity);
        txtName = findViewById(R.id.textviewName);
        txtCoutry = findViewById(R.id.textviewCountry);
        txtTemp = findViewById(R.id.textviewTemp);
        txtStatus = findViewById(R.id.textviewStautus);
        txtHumidity = findViewById(R.id.textviewHumidity);
        txtCound = findViewById(R.id.textviewCounnd);
        txtWind = findViewById(R.id.textviewWind);
        txtDay = findViewById(R.id.textviewDay);
        imgIcon = findViewById(R.id.imageIcon);

    }
}
