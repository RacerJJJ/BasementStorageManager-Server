package me.racer.jjj.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sql {
    private static final Logger log = LoggerFactory.getLogger(sql.class);
    public static Connection mysqlcon;

    public static void initsql(String host, String port, String database, String user, String password) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            mysqlcon = DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" + database , user, password);
            Statement mysqlstatement = mysqlcon.createStatement();
            mysqlstatement.execute("CREATE TABLE IF NOT EXISTS stock (itemid INT PRIMARY KEY, EAN CHAR(13), amount INT, expirydate DATE, adddate DATETIME)");
            mysqlstatement.execute("CREATE TABLE IF NOT EXISTS itemcache (EAN CHAR(13) PRIMARY KEY, name VARCHAR(200), type ENUM('beverage','food'), location ENUM('Fridge','basement'))");
            mysqlstatement.execute("CREATE TABLE IF NOT EXISTS analytics (EAN CHAR(13) PRIMARY KEY, )");
            mysqlstatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            sql.log.error("Verbindung zu SQL nicht möglich!");
            return;
        }
    }
}
