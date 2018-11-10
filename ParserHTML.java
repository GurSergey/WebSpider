package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class ParserHTML {

    public static ArrayList<String> parseHref(String htmlCode, int max)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        Document doc =  Jsoup.parse(htmlCode);

        Elements links;
        links = doc.select("a[href]");
        //Element link = links.first();
        int i =0;
        for (Element element : links) {
            if(i>=max)
                break;
            if((!element.toString().contains("http"))&&(!element.toString().contains("https")))
                arrayList.add(element.attr("href"));
            i++;
        }

        return arrayList;
    }

}
