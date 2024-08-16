package me.racer.jjj;


import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import static me.racer.jjj.utils.eanutils.getEANdata;
import static me.racer.jjj.utils.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        /**
        Map<String, Float> testmap = Map.of("Coca-Cola", 1f);
        microsEAN(testmap);
         **/
        //System.out.println(getEANdata("5449000000439"));


        initsql("127.0.0.1","3306", "basementstoragemanager","mysql","root","root" );
        //loadtocache("5449000000439");
        //loadtocache("5449000214911");
        try {
            Map<String, ArrayList<String>> stock = getstock("stock.expirydate ASC");
            byte[] serializedmap = SerializationUtils.serialize((Serializable) stock);
            System.out.println((Map<String, ArrayList<String>>) SerializationUtils.deserialize(serializedmap));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}