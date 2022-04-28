package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Serwer {
    ArrayList klientArraylist;
    PrintWriter printWriter;

    public static void main(String[] args) {
        Serwer s = new Serwer();
        s.startSerwer();

    }

    public void startSerwer() {
        klientArraylist = new ArrayList();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Slucham: " + serverSocket);
                printWriter = new PrintWriter(socket.getOutputStream());
                klientArraylist.add(printWriter);

                Thread t = new Thread(new SerwerKlient(socket));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SerwerKlient implements Runnable {
        Socket socket;
        BufferedReader bufferedReader;

        public SerwerKlient(Socket socketKlient) {
            try {
                System.out.println("Połączony.");
                socket = socketKlient;
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void run() {
            String str;
            PrintWriter pw = null;

            try {
                while ((str = bufferedReader.readLine()) != null) {
                    System.out.println("Odebrano >>  ");
                    Iterator it = klientArraylist.iterator();
                    while (it.hasNext()) {
                        pw = (PrintWriter) it.next();
                        pw.println(str);
                        pw.flush();
                    }

                }

            } catch (Exception e) {

            }
        }

    }
}
