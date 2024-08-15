package me.racer.jjj.scraper;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class utils {
    public static String getContent(URL url) throws IOException {
        Scanner sc = new Scanner(url.openStream());
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }

        return sb.toString();
    }
}
