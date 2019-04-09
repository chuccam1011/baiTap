package com.example.baitap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter  extends BaseAdapter {


    private MainActivity context;
    private  int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private  class ViewHolder{
            TextView txtTen;
            ImageView imgDelete,imgEdit;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView== null){
            holder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(layout,null);
            holder.txtTen= (TextView)convertView.findViewById(R.id.textViewTen);
            holder.imgDelete=(ImageView)convertView.findViewById(R.id.imageViewDelete);
            holder.imgEdit=(ImageView)convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(holder);


        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        final CongViec congViec= congViecList.get(i);
        holder.txtTen.setText(congViec.getTenCV());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCV(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    context.DialogXoaCV(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        return convertView;
    }
}
