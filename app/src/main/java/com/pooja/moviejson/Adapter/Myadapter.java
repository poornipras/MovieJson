package com.pooja.moviejson.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pooja.moviejson.Model.MyData;
import com.pooja.moviejson.R;

import java.util.List;

/**
 * Created by Pooja on 2/11/2017.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
Context context;
    List<MyData> myDatalistitems;

    public Myadapter(Context context, List<MyData> myDatalistitems) {
        this.context = context;
        this.myDatalistitems = myDatalistitems;
    }

    @Override

    public Myadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.eachcard,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(Myadapter.MyViewHolder holder, int position) {

        MyData myData=myDatalistitems.get(position);
        holder.name.setText(myData.getName());
        holder.id.setText("Id:"+String.valueOf(myData.getId()));
        holder.vote_count.setText("Votes:"+String.valueOf(myData.getVote_count()));

    }

    @Override
    public int getItemCount() {
        return myDatalistitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView id;
        TextView vote_count;

    public MyViewHolder(View itemView) {
        super(itemView);
        name= (TextView) itemView.findViewById(R.id.textView_name);
        id= (TextView) itemView.findViewById(R.id.textView_id);
        vote_count= (TextView) itemView.findViewById(R.id.textView_vote_count);
    }
}
}
