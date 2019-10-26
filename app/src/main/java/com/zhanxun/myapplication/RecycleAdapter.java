package com.zhanxun.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhanxun.myapplication.bean.MultiNewsArticleDataModel;
import com.zhanxun.myapplication.bean.NewsModel;

import java.util.List;

import static com.zhanxun.myapplication.R.layout.item;

/**
 * Created by wilson on 2017/4/18.
 */

public class RecycleAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<MultiNewsArticleDataModel> mDatas;
    private LayoutInflater m_layoutInflater;

    public RecycleAdapter(Context context, List<MultiNewsArticleDataModel> mDatas) {
        this.mDatas=mDatas;
        this.context=context;
        m_layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder=new ItemViewHolder(m_layoutInflater.inflate(R.layout.item,parent,false));
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        ItemViewHolder holder= (ItemViewHolder) viewholder;
        holder.m_text.setText(mDatas.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView m_text;
       // private ImageView m_imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
          //  m_imageView= (ImageView) itemView.findViewById(R.id.img);
            m_text= (TextView) itemView.findViewById(R.id.txt);
        }
    }
}
