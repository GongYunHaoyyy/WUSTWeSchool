package com.gongyunhaoyyy.wustweschool.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by acer on 2017/12/10.
 */

public class DrawView extends View {
    double Y1=0,Y2=0;

    public DrawView(Context context, double Y1, double Y2) {
        super( context );
        this.Y1=Y1;
        this.Y2=Y2;
    }
    public DrawView(Context context) {
        super( context );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();

        //中黄平行四边形
        p.setColor( Color.rgb( 255,191,64 ) );
        Path path2 = new Path();
        path2.moveTo(0, 1000);// 此点为多边形的起点
        path2.lineTo(1500, 600);
        path2.lineTo(1500, 1880);
        path2.lineTo(0, 2180);
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, p);
        //右橙三角
        p.setColor( Color.rgb( 255,129,64 ) );
        Path path3 = new Path();
        path3.moveTo(640, 800);// 此点为多边形的起点
        path3.lineTo(1500, 600);
        path3.lineTo(1500, 1250);
        path3.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path3, p);
        //上篮梯形
        p.setColor( Color.rgb( 19,177,249 ) );
        p.setStyle(Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path1 = new Path();
        path1.moveTo(0, 0);// 此点为多边形的起点
        path1.lineTo(1500, 0);
        path1.lineTo(1500, 600);
        path1.lineTo(0, 1000);
        path1.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path1, p);

//        //底橙三角
//        p.setColor( Color.rgb( 255,129,64 ) );
//        Path path4 = new Path();
//        path4.moveTo(0, 1780);// 此点为多边形的起点
//        path4.lineTo(1500, 1680);
//        path4.lineTo(1500, 2100);
//        path4.lineTo(0, 2780);
//        path4.close(); // 使这些点构成封闭的多边形
//        canvas.drawPath(path4, p);

    }

}
