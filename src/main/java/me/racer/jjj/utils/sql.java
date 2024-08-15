package me.racer.jjj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
            sqlstatment.execute("CREATE TABLE IF NOT EXISTS productcache (EAN CHAR(13) PRIMARY KEY, name VARCHAR(200), quantity VARCHAR(200), imageurl VARCHAR(200), type ENUM('beverage','food'), location ENUM('Fridge','basement') NULL)");
            sqlstatment.execute("CREATE TABLE IF NOT EXISTS analytics (EAN CHAR(13) PRIMARY KEY, totalamount int, closetoexpiryamount int)");
            sqlstatment.close();
        } catch (SQLException | ClassNotFoundException e) {
            sql.log.error("Verbindung zu SQL nicht möglich!");
            System.exit(1);
        }
    }

    public static String loadtocache(String EAN) {
        ArrayList<String> EANdata = new ArrayList<>();
        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
        ProductResponse productResponse = wrapper.fetchProductByCode(EAN);


        Product product = productResponse.getProduct();
        if (product == null) {
            return "Product could not be found.";
        }
        try {
            Statement cachestat = mainsqlcon.createStatement();
            cachestat.executeUpdate("INSERT INTO productcache (EAN, name, quantity, imageurl, type, location) VALUES ('" + EAN + "', '" + product.getProductName() + "', '" + product.getQuantity() + "', '" + product.getImageUrl() + "', '" + product.getCategories().split(",")[0] + "', '" + null +  "')");
            cachestat.close();
        } catch (SQLException e) {
            return "There was a problem adding it to the cache-database.";
        }

        return "success";
    }
}
