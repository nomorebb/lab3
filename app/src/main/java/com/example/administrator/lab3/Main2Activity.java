package com.example.administrator.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public class Main2Activity extends AppCompatActivity {
    String[] l_item = new String[] {"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
    List<Map<String,Object>> datas = new ArrayList<>();
    int tag;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Name= bundle.getString("name");
        TextView getname = (TextView)findViewById(R.id.Name);
        getname.setText(Name);
        String letter = bundle.getString("firstletter");
        int imageid = bundle.getInt("imageId");
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.setImageResource(imageid);
        String Price = bundle.getString("price");
        TextView getprice = (TextView)findViewById(R.id.price);
        getprice.setText(Price);
        String information = bundle.getString("information");
        TextView getinforamtion = (TextView)findViewById(R.id.information);
        getinforamtion.setText(information);
        String firstletter = bundle.getString("firstletter");

        for(int i=0;i<4;i++){
            Map<String,Object> temp = new LinkedHashMap<>();
            temp.put("item",l_item[i]);
            datas.add(temp);
        }
        ListView listView = (ListView) findViewById(R.id.list_view);
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(this,datas,R.layout.item,new String[] {"item"},new int[] {R.id.list_item2});
        listView.setAdapter(simpleAdapter1);

        ImageView star = (ImageView)findViewById(R.id.star);
        tag = bundle.getInt("tag");
        if(tag==0){
            star.setImageDrawable(getResources().getDrawable(R.mipmap.empty_star));
        }
        else if(tag==1){
            star.setImageDrawable(getResources().getDrawable(R.mipmap.full_star));
        }
        star.setOnClickListener((v)->{
            if(tag==0){
                star.setImageDrawable(getResources().getDrawable(R.mipmap.full_star));
                tag = 1;
            }
            else if(tag==1){
                star.setImageDrawable(getResources().getDrawable(R.mipmap.empty_star));
                tag = 0;
            }
        });
        ImageView car = (ImageView)findViewById(R.id.car);
        car.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(Main2Activity.this, "商品已加到购物车", Toast.LENGTH_SHORT).show();
                Map<String,Object> t = new LinkedHashMap<>();
                t.put("firstletterofcar",letter);
                t.put("nameofcar",Name);
                t.put("priceofcar",Price);
                t.put("informationofcar",information);
                t.put("imageidofcar",imageid);
                MainActivity.dataofcar.add(t);
                MainActivity.simpleAdapter.notifyDataSetChanged();
            }
        });
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                intent.putExtra("tag",tag);
                Main2Activity.this.setResult(0,intent);
                Main2Activity.this.finish();
            }
        });
    }
}
