package com.example.administrator.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/20.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
    OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onitemClickListener) {
        this.mOnItemClickListener = onitemClickListener;
    }
    Context mcontext;
    int mlayoutId;
    List<T> mDatas;
    LayoutInflater minflater;
    public CommonAdapter(List<T> datas, Context context, int layoutId){
        mcontext = context;
        mDatas = datas;
        mlayoutId = layoutId;
        minflater = LayoutInflater.from(mcontext);
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewtype){
        ViewHolder viewHolder = ViewHolder.get(mcontext, parent, mlayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        convert(holder,mDatas.get(position));
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    public abstract void convert(ViewHolder holder, T t);
//    CommonAdapter commonAdapter = new CommonAdapter<Map<String,Object>> (this, R.layout.list_item,){
//        @Override
//        public abstract void convert(ViewHolder holder, Map<String,Object> s){
//            TextView name = holder.getView(R.id.name);
//            name.setText(s.get("name").toString());
//            TextView first = holder.getView(R.id.first_letter);
//            first.setText(s.get("first_letter").toString());
//        }
//    }
    @Override
    public int getItemCount(){
        return mDatas.size();
    }
    public void removeData(int position){
        if (position >= 0) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

}
