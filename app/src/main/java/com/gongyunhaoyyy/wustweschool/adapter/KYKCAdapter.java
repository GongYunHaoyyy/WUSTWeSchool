package com.gongyunhaoyyy.wustweschool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.KYkecheng;

import java.util.ArrayList;
import java.util.List;

//    ┏┓　   ┏┓
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//
//  Creste by GongYunHao on 2018/2/13
// 
public class KYKCAdapter extends RecyclerView.Adapter<KYKCAdapter.ViewHolder>{
    private List<KYkecheng> myKykc=new ArrayList<>();

    public KYKCAdapter(List<KYkecheng> mKYkechengmx) {
        this.myKykc=mKYkechengmx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_recycler_xklb,parent,false );
        final KYKCAdapter.ViewHolder kyviewHolder=new KYKCAdapter.ViewHolder( view );
        kyviewHolder.xklbview.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                int position=kyviewHolder.getAdapterPosition();
                KYkecheng mKYkecheng=myKykc.get( position );
                Toast.makeText( view.getContext(),mKYkecheng.getKcmc(),Toast.LENGTH_SHORT ).show();
            }
        } );
        return kyviewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KYkecheng mKYkecheng1=myKykc.get( position );
        holder.mkcmc.setText( mKYkecheng1.getKcmc() );
        holder.mkcxz.setText( mKYkecheng1.getKcxzmc() );
        holder.mkksj.setText( mKYkecheng1.getKcsj() );
        holder.myxrs.setText( mKYkecheng1.getXkrs() );
        holder.msyrs.setText( mKYkecheng1.getSyrs() );
        holder.mxf.setText( "学分： "+mKYkecheng1.getXf() );
        holder.mskjs.setText( "授课教师： "+mKYkecheng1.getJsxm() );

    }

    @Override
    public int getItemCount() {
        return myKykc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout xklbview;
        TextView mkcmc,mkcxz,mkksj,myxrs,msyrs,mxf,mskjs;
        public ViewHolder(View itemView) {
            super( itemView );
            xklbview=itemView.findViewById( R.id.g_c_xklb_view );
            mkcmc=itemView.findViewById( R.id.g_c_kcmc );
            mkcxz=itemView.findViewById( R.id.g_c_kcxz );
            mkksj=itemView.findViewById( R.id.g_c_kksj );
            myxrs=itemView.findViewById( R.id.g_c_yxrs );
            msyrs=itemView.findViewById( R.id.g_c_syrs );
            mxf=itemView.findViewById( R.id.g_c_xf );
            mskjs=itemView.findViewById( R.id.g_c_skjs );
        }
    }
}
