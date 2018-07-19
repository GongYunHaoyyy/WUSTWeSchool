package com.gongyunhaoyyy.wustweschool.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;

/**
 * Created by acer on 2017/10/8.
 */

public class AbsGridAdapter implements ListAdapter {

    private Context mContext;

    private String[][][] contents;

    private int rowTotal;

    private int columnTotal;

    private int positionTotal;

    public AbsGridAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    public int getCount() {
        return positionTotal;
    }

    @Override
    public Object getItem(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        return contents[row][column];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public Object getItem1(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        return contents[row][column][0];
    }

    public Object getItem2(int position) {
        //求余得到二维索引
        int column = position % columnTotal;
        //求商得到二维索引
        int row = position / columnTotal;
        return contents[row][column][1];
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if( convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate( R.layout.grib_item, null);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.grid_item_text);
        TextView tvid=(TextView)convertView.findViewById( R.id.courseid );
        //如果有课,那么添加数据
        if( !getItem1(position).equals("")) {
            textView.setText((String)getItem1(position));
            tvid.setText( (String)getItem2( position ) );

            //变换颜色
            int rand = position % columnTotal;
            switch( rand ) {
                case 0:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_7));
                    break;
                case 1:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_6));
                    break;
                case 2:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_5));
                    break;
                case 3:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_4));
                    break;
                case 4:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_3));
                    break;
                case 5:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_2));
                    break;
                case 6:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_1));
                    break;
                case 7:
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_0));
                    break;
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int row = position / columnTotal;
                    int column = position % columnTotal;
                    String con = "当前选中的是" + contents[row][column][0] + "课"+(position + 1);
                    Toast.makeText(mContext, con, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     *这个一定要注意！！！
     * 如果不改成positionTotal的话，会闪退报错 Can't have a viewTypeCount < 1
     */
    @Override
    public int getViewTypeCount() {
        return positionTotal;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

//  设置内容、行数、列数
    public void setContent(String[][][] contents, int row, int column) {
        this.contents = contents;
        this.rowTotal = row;
        this.columnTotal = column;
        positionTotal = rowTotal * columnTotal;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
