package me.racer.jjj.socket;

import com.sun.net.httpserver.HttpServer;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
                String response = "Hello!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
                exchange.close();
            }));
            server.setExecutor(null); // maybe offload to different thread?
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
