package com.example.administrator.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/10/20.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    SparseArray<View> mViews;
    Context mcontext;
    View mConvertview;
    public ViewHolder(Context context, View itemview, ViewGroup parent){
        super(itemview);
        mConvertview = itemview;
        mcontext = context;
        mViews = new SparseArray<View>();
    }
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId){
        View itemview = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder holder = new ViewHolder(context,itemview,parent);
        return holder;
    }
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view==null){
            view = mConvertview.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }
}
