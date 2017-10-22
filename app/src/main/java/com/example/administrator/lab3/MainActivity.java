package com.example.administrator.lab3;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;


public class MainActivity extends AppCompatActivity {

    List<Map<String,Object>> data = new ArrayList<>();
    List<String> Lprice = new ArrayList<>();
    CommonAdapter commonAdapter;
    List<String> Linformation = new ArrayList<>();
    String[] Name = new  String[] {"Arla Milk","Borggreve","Devondale Milk","EnchatedForest","Ferrero","Kindle","Lindt","Maltesers","Mcvitie","Waitrose"};
    String[] Letter = new  String[] {"A","B","D","E","F","K","L","M","M","W"};
    String[] price = new String[]{"¥ 59.00","¥ 28.90","¥ 79.00","¥ 5.00","¥ 132.59","¥ 2399.00","¥ 139.43","¥ 141.43","¥ 14.90","¥ 179.00"};
    String[] information = new String[]{"产地  德国","重量  640g","产地  澳大利亚","作者  Johanna Basford","重量  300g","版本  8GB","重量  249g","重量  118g","产地  英国","重量  2Kg"};
    int[] tag = new int[100];
    int gg;
    int flag = 0;
    public static List<Map<String,Object>> dataofcar = new ArrayList<>();
    public static SimpleAdapter simpleAdapter;
    int simida = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypedArray ar = getResources().obtainTypedArray(R.array.actions_images);
        int len = ar.length();
        final int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);

        ar.recycle();
        for(int i=0;i<10;i++){
            tag[i] = 0;
        }
        Map<String,Object> t = new LinkedHashMap<>();
        t.put("firstletterofcar","*");
        t.put("nameofcar","购物车");
        t.put("priceofcar","价格");
        dataofcar.add(t);
        if(dataofcar.size()>1){
            dataofcar.remove(dataofcar.size()-1);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for(int i=0;i<10;i++){
            Map<String,Object>temp = new LinkedHashMap<>();
            temp.put("first_letter",Letter[i]);
            temp.put("name",Name[i]);
            Lprice.add(price[i]);
            Linformation.add(information[i]);
            data.add(temp);
        }
        commonAdapter = new CommonAdapter<Map<String,Object>>(data, this, R.layout.list_item){
            @Override
            public void convert(final ViewHolder holder, Map<String,Object> s) {
                TextView name = holder.getView(R.id.name);
                name.setText(s.get("name").toString());
                TextView first = holder.getView(R.id.first_letter);
                first.setText(s.get("first_letter").toString());
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onLongClick(int position){
                commonAdapter.removeData(position);
                //Lprice.remove(position);
                Linformation.remove(position);
                for(int i=position;i<9;i++){
                    price[i] = price[i+1];
                    resIds[i] = resIds[i+1];
                    Name[i] = Name[i+1];
                    tag[i] = tag[i+1];
                    Letter[i] = Letter[i+1];
                    information[i] = information[i+1];
                }
                Toast.makeText(MainActivity.this, "移除第" + position +"个商品", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onClick(int position){
                gg = position;
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("firstletter",Letter[position]);
                bundle.putString("name",Name[position]);
                bundle.putString("price",price[position]);
                bundle.putString("information",Linformation.get(position));
                bundle.putInt("imageId",resIds[position]);
                bundle.putInt("tag",tag[position]);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000);
        recyclerView.setAdapter(animationAdapter);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        ListView listview = (ListView)findViewById(R.id.mylist);
//        int size = data.size();
//        if(size>0){
//            System.out.println(size);
//            data.removeAll(data);
//            simpleAdapter.notifyDataSetChanged();
//            listview.setAdapter(simpleAdapter);
//        }
        simpleAdapter = new SimpleAdapter(this,dataofcar,R.layout.listview_item,new String[] {"firstletterofcar","nameofcar","priceofcar"},new int [] {R.id.first_letterofcar,R.id.nameofcar,R.id.Price});
        listview.setAdapter(simpleAdapter);
        floatingActionButton.setOnClickListener((v)->{
            if(flag==0){
                floatingActionButton.setImageDrawable(getResources().getDrawable(R.mipmap.mainpage));
                recyclerView.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.VISIBLE);
                flag = 1;
            }
            else if(flag==1){
                floatingActionButton.setImageDrawable(getResources().getDrawable(R.mipmap.shoplist));
                recyclerView.setVisibility(View.VISIBLE);
                listview.setVisibility(View.INVISIBLE);
                flag = 0;
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                if(position!=0){
                   // bb = position;
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("firstletter",dataofcar.get(position).get("firstletterofcar").toString());
                    bundle.putString("name",dataofcar.get(position).get("nameofcar").toString());
                    bundle.putString("price",dataofcar.get(position).get("priceofcar").toString());
                    bundle.putString("information",dataofcar.get(position).get("informationofcar").toString());
                    bundle.putInt("imageId",Integer.valueOf(dataofcar.get(position).get("imageidofcar").toString()).intValue());
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    TextView textView = (TextView) view.findViewById(R.id.name);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("移除商品");
                    builder.setMessage("从购物车移除" + Name[position] + "?");
                    builder.setPositiveButton("确定", (dialog, which) -> {
                        dataofcar.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                    }).setNegativeButton("取消", (dialog, which) -> {

                    }).create();
                    builder.show();
                }
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        tag[gg] = datas.getExtras().getInt("tag");//得到新Activity 关闭后返回的数据
    }

}

