package me.racer.jjj.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class migros {

    // Currently not working, postponed to fix later

    public static void microsEAN(Map<String,Float> purchases) {
        for (Map.Entry<String,Float> entry : purchases.entrySet()) {
            Document searchdoc = null;
            //gtin
            System.out.println("https://www.migros.ch/de/search?query=" + entry.getKey().replaceAll(" ","%20"));
            try {
                searchdoc = Jsoup.connect("https://www.migros.ch/de/search?query=" + entry.getKey().replaceAll(" ","%20")).get();
            } catch (IOException e) {
                continue;
            }
            System.out.println(searchdoc.body());
            Elements searchresult = searchdoc.getElementsByClass("show-product-detail");
            System.out.println(searchresult);

        }

    }

    private static Map<String,Float> readpdf() {

        Map<String, Float> purchases = new HashMap<>();
        // Read content of PDF receipt
        return purchases;
    }
}
