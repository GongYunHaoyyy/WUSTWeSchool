package com.gongyunhaoyyy.wustweschool.tools;

import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.element_item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by yuanyuanlai on 2018/2/26.
 */

public class Book {

    private String detailUrl;
    static Document document = null;

    static void setBook(List<element_item> books , String url ){
        try{
         document = Jsoup.connect(url).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Elements elements = document.getElementsByClass("table_line").select("tr");
        for (int i=1 ; i<elements.size() ; i++){
        }
    }

}
