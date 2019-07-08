package com.jy.zqizhong;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder1> {
    private ArrayList<WanAndroidBean.DataBean.DatasBean> list;
    private Context context;
    public boolean isChed = false;
    private View inflate;

    public boolean isChed() {
        return isChed;
    }



    public HomeAdapter(ArrayList<WanAndroidBean.DataBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflate = LayoutInflater.from(context).inflate(R.layout.item_rv, null);
        return new ViewHolder1(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder1 viewHolder1, final int i) {
        final WanAndroidBean.DataBean.DatasBean datasBean = list.get(i);
        Glide.with(context).load(datasBean.getEnvelopePic()).into(viewHolder1.mIvS);
        if (isChed) {
            viewHolder1.mCb.setVisibility(View.VISIBLE);
        } else {
            viewHolder1.mCb.setVisibility(View.GONE);
        }
        viewHolder1.mTvAnter.setText(datasBean.getAuthor());
        viewHolder1.mTvTitle.setText(datasBean.getTitle());
        viewHolder1.mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                datasBean.setVist(isChecked);
            }
        });
        viewHolder1.mCb.setChecked(datasBean.isVist());
        viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemLong != null) {
                    onItemLong.onitemlong(i, inflate, datasBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        ImageView mIvS;
        TextView mTvTitle;
        TextView mTvAnter;
        CheckBox mCb;

        public ViewHolder1(View itemView) {
            super(itemView);
            this.mIvS = (ImageView) itemView.findViewById(R.id.iv_s);
            this.mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.mTvAnter = (TextView) itemView.findViewById(R.id.tv_anter);
            this.mCb = (CheckBox) itemView.findViewById(R.id.cb);
        }
    }
    private OnItemLong onItemLong;

    public void setOnItemLong(OnItemLong onItemLong) {
        this.onItemLong = onItemLong;
    }

    interface OnItemLong {
        void onitemlong(int i, View view, WanAndroidBean.DataBean.DatasBean bean);
    }
}
