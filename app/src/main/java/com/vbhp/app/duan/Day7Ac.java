package com.vbhp.app.duan;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vbhp.app.duan.adapter.CustomAdapter;
import com.vbhp.app.duan.model.Thoitiet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Day7Ac extends AppCompatActivity {
    String tenthanhpho = "";
  //  ImageView imgback;
   // TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> mangThoitiets;
    TextView tvThoiTiet;
    ImageView imgBack;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Day7Ac.this, SearchAc.class));
            }
        });
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        //Log.d("Ketqua: ", "Du lieu truyen qua" + city);
        if (city.equals("")){
            tenthanhpho = "Hanoi";
            Get7DaysData(tenthanhpho);
        }else {
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }

    }


    private void Anhxa() {
       // imgback = findViewById(R.id.imageviewBack);
        //txtName = findViewById(R.id.textviewTenthanhpho);
        imgBack = findViewById(R.id.imgBackDay);
        tvThoiTiet = findViewById(R.id.textviewThoiTiet);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
        tvThoiTiet.setTypeface(typeface);
        lv = findViewById(R.id.listview);
        mangThoitiets = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(Day7Ac.this, mangThoitiets);
        lv.setAdapter(customAdapter);
    }

    private void Get7DaysData(final String data) {
        String url = "https://openweathermap.org/data/2.5/forecast?q="+data+"&id=1581130&appid=b6907d289e10d714a6e88b30761fae22&fbclid=IwAR3vD5ImiiTKnV1HXlBDVhCMk1iUPg8OSj-4pPw1wEBSeoX_uWdnKnlPVZE";
       // String url = "https://samples.openweathermap.org/data/2.5/forecast/daily?q="+data+"&appid=7955bc4a07cdb01248177ad96112bc5b";
        RequestQueue requestQueue = Volley.newRequestQueue(Day7Ac.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("KetQua", "Json:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                           // String name = jsonObjectCity.getString("name");
                            //txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyyy HH:mm:ss");//HH:mm:ss
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp  = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_min");
                                String min = jsonObjectTemp.getString("temp_max");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String NhietdoMax = String.valueOf(a.intValue());
                                String NhietdoMin = String.valueOf(b.intValue());


                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                mangThoitiets.add(new Thoitiet(Day, status, icon, NhietdoMax, NhietdoMin));

                            }

                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


}

