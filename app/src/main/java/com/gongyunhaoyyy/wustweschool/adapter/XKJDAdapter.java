package com.gongyunhaoyyy.wustweschool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Xkjieduan;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by acer on 2018/2/10.
 */

public class XKJDAdapter extends RecyclerView.Adapter<XKJDAdapter.ViewHolder>{
    private List<Xkjieduan> myXK=new ArrayList<>();
    private Context context;
    private String xh;
    //单独一个列表记录被选中的位置
    private List<Boolean> isSelected;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_recycler_xkjd,parent,false );
        final XKJDAdapter.ViewHolder viewHolder=new XKJDAdapter.ViewHolder( view );
        viewHolder.xkjdview.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Xkjieduan myxkjdcreate=myXK.get( position );

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor nameeditor = context.getSharedPreferences( "xzxkjd", MODE_PRIVATE ).edit( );
                nameeditor.putString( "getxzxkjd",xh+","+myxkjdcreate.getJx0502id()+","+myxkjdcreate.getXnmc() );
                nameeditor.apply();
            }
        } );
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Xkjieduan myxkjdbind=myXK.get( position );
        holder.mxklb.setText( "类别："+myxkjdbind.getXklb() );
        holder.mxkkssj.setText( "开始："+myxkjdbind.getXkkssj() );
        holder.mxkjzsj.setText( "结束："+myxkjdbind.getXkjzsj() );
        holder.mxkjd.setText( "阶段："+myxkjdbind.getXkjd() );
        holder.mxnmc.setText( "学期："+myxkjdbind.getXnmc() );
    }

    public XKJDAdapter(List<Xkjieduan> myXK,String xh, Context context) {
        this.context=context;
        this.xh=xh;
        this.myXK = myXK;
        //初始化选中列表
        isSelected=new ArrayList<>(  );
        for (int i=0;i<myXK.size();i++){
            isSelected.add( false );
        }
    }

    @Override
    public int getItemCount() {
        return myXK.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View xkjdview;
        TextView mxklb,mxkkssj,mxkjzsj,mxkjd,mxnmc;

        ViewHolder(View itemView) {
            super( itemView );
            xkjdview = itemView;
            mxklb=itemView.findViewById( R.id.g_xklb );
            mxkkssj=itemView.findViewById( R.id.g_xkkssj );
            mxkjzsj=itemView.findViewById( R.id.g_xkjzsj );
            mxkjd=itemView.findViewById( R.id.g_xkjd );
            mxnmc=itemView.findViewById( R.id.g_xnmc );
        }
    }
}
