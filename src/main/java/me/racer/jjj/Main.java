package me.racer.jjj;


import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import static me.racer.jjj.socket.httpserver.initializeHTTPServer;
import static me.racer.jjj.utils.sql.*;

public class Main {

    public static final String IMG_FILE_PATH = "/cache/images/";

    public static void main(String[] args) {
        System.out.println("Hello world!");
        /**
        Map<String, Float> testmap = Map.of("Coca-Cola", 1f);
        microsEAN(testmap);
         **/
        //System.out.println(getEANdata("5449000000439"));


        initsql("127.0.0.1","3306", "basementstoragemanager","mysql","root","root" );

        initializeHTTPServer("127.0.0.1", 8080);
        //loadtocache("5449000000439");
        //loadtocache("5449000214911");
        try {
            String stock = getstock("stock.expirydate ASC");
            System.out.println(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}