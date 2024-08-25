package me.racer.jjj.socket;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang3.SerializationUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static me.racer.jjj.utils.sql.*;

public class httpserver {
    public static void initializeHTTPServer(String host, int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 10);
            server.createContext("/api/sendean", (exchange -> {
                // get info of ean
                // add ean to db
                // send

                //JSON DATA FROM CLIENT


                InputStream requstream = exchange.getRequestBody();
                Scanner scanner = new Scanner(requstream);
                String requdata = scanner.nextLine();
                JSONObject requjson;
                try {
                    requjson = (JSONObject) new JSONParser().parse(requdata);
                } catch (ParseException e) {
                    String resp = "Invalid data received.";
                    exchange.sendResponseHeaders(200, resp.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(resp.getBytes());
                    output.flush();
                    exchange.close();
                    return;
                }

                String resp = loadtocache((String) requjson.get("EAN"));

                try {
                    addStock((String) requjson.get("EAN"), Integer.valueOf((String) requjson.get("EAN")), (String) requjson.get("expirydate"));
                } catch (Exception e) {
                    String respo = "Failed to update stock.";
                    exchange.sendResponseHeaders(200, respo.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(respo.getBytes());
                    output.flush();
                    exchange.close();
                    return;
                }

                exchange.sendResponseHeaders(200, resp.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(resp.getBytes());
                output.flush();
                exchange.close();
            }));

            server.createContext("/api/removeean", (exchange -> {
                InputStream requstream = exchange.getRequestBody();
                Scanner scanner = new Scanner(requstream);
                String requdata = scanner.nextLine();
                try {
                    removeStock(Integer.valueOf(requdata));
                    String respo = "Successfully removed from stock.";
                    exchange.sendResponseHeaders(200, respo.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(respo.getBytes());
                    output.flush();
                    exchange.close();
                } catch (SQLException e) {
                    String respo = "Failed to update stock.";
                    exchange.sendResponseHeaders(200, respo.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(respo.getBytes());
                    output.flush();
                    exchange.close();
                    return;
                }
            }));

            server.createContext("/api/currentstock", (exchange -> {
                // get current stock from db
                // send to client

                InputStream requstream = exchange.getRequestBody();
                Scanner scanner = new Scanner(requstream);
                String requdata;
                try {
                    requdata = scanner.nextLine();
                } catch (NoSuchElementException e) {
                    requdata = "stock.expirydate ASC";
                }


                String stock;
                try {
                    stock = getstock(requdata);
                    //byte[] serializedmap = SerializationUtils.serialize((Serializable) stock);
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, stock.length());
                    OutputStream output = exchange.getResponseBody();
                    output.write(stock.getBytes());
                    output.flush();
                    exchange.close(); //maybe I should send it as json data instead

                } catch (SQLException e) {
                    String error = "There was an error processing the request.\nError: " + e.getMessage();
                    exchange.sendResponseHeaders(200, error.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(error.getBytes());
                    output.flush();
                    exchange.close();
                }


            }));

            server.createContext("/api/productstock", (exchange -> {
                // get current stock from db
                // send to client
                InputStream requstream = exchange.getRequestBody();
                Scanner scanner = new Scanner(requstream);
                String requdata = scanner.nextLine();
                System.out.println(requdata);

                String stock;
                try {
                    stock = getproductstock(requdata);
                    //byte[] serializedmap = SerializationUtils.serialize((Serializable) stock);
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, stock.length());
                    OutputStream output = exchange.getResponseBody();
                    output.write(stock.getBytes());
                    output.flush();
                    exchange.close(); //maybe I should send it as json data instead

                } catch (SQLException e) {
                    String error = "There was an error processing the request.\nError: " + e.getMessage();
                    exchange.sendResponseHeaders(200, error.getBytes().length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(error.getBytes());
                    output.flush();
                    exchange.close();
                }


            }));
            server.setExecutor(null); // maybe offload to different thread?
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
