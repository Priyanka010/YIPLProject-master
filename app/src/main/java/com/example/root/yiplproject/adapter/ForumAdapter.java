package com.example.root.yiplproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.yiplproject.ComplainDetail;
import com.example.root.yiplproject.R;
import com.example.root.yiplproject.model.Complain;

import java.util.List;


/**
 * Created by user on 7/24/2017.
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder>{

    private List<Complain> complain;
    Context context;

    public ForumAdapter(List<Complain> complain, Context context) {
        this.complain = complain;
        this.context=context;
    }

    @Override
    public ForumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_forum, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumAdapter.ViewHolder holder, final int position) {
        holder.tv_date.setText(complain.get(position).getDatee());
        holder.tv_head.setText(complain.get(position).getHead());
        holder.tv_body.setText(complain.get(position).getBody());
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ComplainDetail.class);
                intent.putExtra("hello",complain.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return complain.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_head, tv_body;
        LinearLayout linear;

        public ViewHolder(View view) {
            super(view);
            linear=(LinearLayout)view.findViewById(R.id.linear);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_head = (TextView) view.findViewById(R.id.tv_head);
            tv_body = (TextView) view.findViewById(R.id.tv_body);

        }
    }
}