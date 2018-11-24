package com.vbhp.app.duan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vbhp.app.duan.R;
import com.vbhp.app.duan.model.Thoitiet;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview, null);

        Thoitiet thoitiet = arrayList.get(position);

        TextView txtDay = convertView.findViewById(R.id.textviewNgay);
        TextView txtStatus = convertView.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp = convertView.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp = convertView.findViewById(R.id.textviewMinTemp);
        ImageView imageViewStatus = convertView.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.MaxTemp + "°C");
        txtMinTemp.setText(thoitiet.MinTemp + "°C");
        Picasso.with(context).load("https://openweathermap.org/img/w/" +  thoitiet.Image + ".png").into(imageViewStatus);




        if (position %2 ==0){
            convertView.setBackgroundColor(Color.parseColor("#CCCCFF"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#CCFFCC"));
        }

        return convertView;
    }
}
