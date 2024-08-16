package me.racer.jjj.socket;

import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang3.SerializationUtils;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static me.racer.jjj.utils.sql.getstock;

public class httpserver {
    public static void initializeHTTPServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/api/sendean", (exchange -> {
                // get info of ean
                // add ean to db
                // send

                String response = "successfully added!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
                exchange.close();
            }));
            server.createContext("/api/currentstock", (exchange -> {
                // get current stock from db
                // send to client
                InputStream requstream = exchange.getRequestBody();
                Scanner scanner = new Scanner(requstream);
                String requdata = scanner.nextLine();

                Map<String, ArrayList<String>> stock;
                try {
                    stock = getstock(requdata);
                    byte[] serializedmap = SerializationUtils.serialize((Serializable) stock);
                    exchange.sendResponseHeaders(200, serializedmap.length);
                    OutputStream output = exchange.getResponseBody();
                    output.write(serializedmap);
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
