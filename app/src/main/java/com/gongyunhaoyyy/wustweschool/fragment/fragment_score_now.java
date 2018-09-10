package com.gongyunhaoyyy.wustweschool.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongyunhaoyyy.wustweschool.adapter.ScoreAdapter;
import com.gongyunhaoyyy.wustweschool.base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.bean.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2017/12/14.
 */

@SuppressLint("ValidFragment")
public class fragment_score_now extends BaseFragment {
    TextView null_n, averige_n;
    private List<Score> mScorelist_now = new ArrayList<>( );
    private int number = 0;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recycler_score;
    private ScoreAdapter adapter;

    @SuppressLint("ValidFragment")
    public fragment_score_now(List<Score> scorelist_n) {
        mScorelist_now.addAll( scorelist_n );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_score_now, container, false );

        recycler_score = (RecyclerView) view.findViewById( R.id.recycler_score_now );
        null_n = (TextView) view.findViewById( R.id.null_score_now );
        averige_n = view.findViewById( R.id.averige_now );
        averige_n.setText( "平均绩点： "+ComputeAverigeScore(mScorelist_now) );
        layoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        recycler_score.setLayoutManager( layoutManager );

        for (Score ssss:mScorelist_now){
            number++;
        }
        //空的成绩~~
        if (number<1){
//                   这句话有问题，不知道为什么
            null_n.setVisibility( View.VISIBLE );
            recycler_score.setVisibility( View.GONE );
        }else {
            null_n.setVisibility( View.GONE );
            recycler_score.setVisibility( View.VISIBLE );
            for (int csy=0;csy<mScorelist_now.size();csy++){
                Log.d( ">>>>>>>>>>>>>>", mScorelist_now.get( csy ).getKkxq());
            }
            adapter=new ScoreAdapter(mScorelist_now);
            recycler_score.setAdapter( adapter );
            adapter.notifyDataSetChanged();
        }

        return view;
    }

}
