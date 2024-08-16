package me.racer.jjj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sql {
    private static final Logger log = LoggerFactory.getLogger(sql.class);
    public static Connection mainsqlcon;
    public static Connection pricesqlcon;

    public static void initsql(String host, String port, String database, String pricedatabase, String user, String password) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            mainsqlcon = DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" + database , user, password);
            pricesqlcon = DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" + pricedatabase , user, password);
            Statement sqlstatment = mainsqlcon.createStatement();
            sqlstatment.execute("CREATE TABLE IF NOT EXISTS stock (itemid INT PRIMARY KEY, EAN CHAR(13), amount INT, expirydate DATE, adddate DATETIME)");
            sqlstatment.execute("CREATE TABLE IF NOT EXISTS productcache (EAN CHAR(13) PRIMARY KEY, name VARCHAR(200), quantity VARCHAR(200), imageurl VARCHAR(200), type ENUM('Beverages','Boissons','food'), location ENUM('Fridge','basement') NULL)");
            sqlstatment.execute("CREATE TABLE IF NOT EXISTS analytics (EAN CHAR(13) PRIMARY KEY, totalamount int, closetoexpiryamount int)");
            sqlstatment.close();
        } catch (SQLException | ClassNotFoundException e) {
            sql.log.error("Verbindung zu SQL nicht möglich!");
            System.exit(1);
        }
    }

    public static Map<String,ArrayList<String>> getstock(String orderby) throws SQLException {
        Map<String,ArrayList<String>> stock = new HashMap<>();
        Statement stockstat = mainsqlcon.createStatement();
        ResultSet userresult =  stockstat.executeQuery("SELECT stock.EAN, productcache.name, stock.amount, stock.expirydate, productcache.imageurl FROM stock JOIN productcache ON stock.EAN=productcache.EAN ORDER BY "+ orderby +"");

        userresult.absolute(0);
        while (userresult.next()) {
            ArrayList<String> stockarray = new ArrayList();
            for (int i = 2; i <= userresult.getMetaData().getColumnCount(); i++) {
                stockarray.add(userresult.getString(i));
            }
            stock.put(userresult.getString(1),stockarray);

        }
        //System.out.println(stock);
        /**
        userresult.first();
        System.out.println(userresult.getString(1) + " | " +userresult.getString(2)+ " | " +userresult.getString(3)+ " | " +userresult.getString(4)+ " | " + userresult.getString(5));
        userresult.next();
        System.out.println(userresult.getString(1) + " | " +userresult.getString(2)+ " | " +userresult.getString(3)+ " | " +userresult.getString(4)+ " | " + userresult.getString(5));
         **/

        return stock;
    }



    public static String loadtocache(String EAN) {
        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
        ProductResponse productResponse = wrapper.fetchProductByCode(EAN);


        Product product = productResponse.getProduct();
        if (product == null) {
            return "Product could not be found.";
        }
        try {
            Statement cachestat = mainsqlcon.createStatement();
            System.out.println(product.getCategories().split(",")[0]);
            cachestat.executeUpdate("INSERT INTO productcache (EAN, name, quantity, imageurl, type, location) VALUES ('" + EAN + "', '" + product.getProductName() + "', '" + product.getQuantity() + "', '" + product.getImageUrl() + "', '" + product.getCategories().split(",")[0] + "', '" + "basement" +  "')");
            cachestat.close();
        } catch (SQLException e) {
            return "There was a problem adding it to the cache-database.";
        }

        return "success";
    }
}
//insert into stock(itemid, EAN, amount, expirydate, adddate) values (1, "5449000000439",2,"2024-09-13","2024-08-16 12:13:14");
//insert into stock(itemid, EAN, amount, expirydate, adddate) values (2, "5449000214911",4,"2024-09-18","2024-08-16 14:13:14");