package com.gongyunhaoyyy.wustweschool.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2017/10/23.
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private List<Score> mlist=new ArrayList<>( );

    static class ViewHolder extends RecyclerView.ViewHolder {
        View scoreview;
        ImageView s90;
        TextView bx,kcmc,ksxz,xf,jd,cj,kkxq;

        public ViewHolder(View itemView) {
            super( itemView );
            scoreview=itemView;
            bx=(TextView)itemView.findViewById( R.id.bx );
            kcmc=(TextView)itemView.findViewById( R.id.kcmc );
            ksxz=(TextView)itemView.findViewById( R.id.ksxz );
            xf=(TextView)itemView.findViewById( R.id.xf );
            jd=(TextView)itemView.findViewById( R.id.jd );
            cj=(TextView)itemView.findViewById( R.id.cj );
            kkxq=(TextView)itemView.findViewById( R.id.kkxq );
            s90=(ImageView)itemView.findViewById( R.id.score_90 );
        }
    }
    public ScoreAdapter(List<Score> scorelist){
        mlist=scorelist;
    }


    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.score_item,parent,false );
                final ScoreAdapter.ViewHolder viewHolder=new ScoreAdapter.ViewHolder( view );
                viewHolder.scoreview.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        int position=viewHolder.getAdapterPosition();
                        Score ss=mlist.get( position );
                        Toast.makeText( v.getContext(),ss.getKcmc(),Toast.LENGTH_SHORT ).show();
                    }
                } );
                return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
        Score s=mlist.get( position );
        if (!(s.getZcj().charAt( 0 )>='A'&&s.getZcj().charAt( 0 )<='F')){
            if (Integer.parseInt( s.getZcj() )>=90){
                holder.s90.setVisibility( View.VISIBLE );
            }else {
                holder.s90.setVisibility( View.INVISIBLE );
            }
        }
        holder.bx.setText( "课程类型：  "+s.getKclbmc() );
        holder.kkxq.setText( s.getKkxq() );
        holder.kcmc.setText( s.getKcmc() );
        holder.ksxz.setText( s.getKsxzmc() );
        holder.xf.setText( "学分：  "+s.getXf() );
        holder.jd.setText( "绩点：  "+s.getJd() );
        holder.cj.setText( "成绩：  "+s.getZcj() );
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}