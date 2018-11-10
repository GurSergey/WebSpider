package com.company;

import com.sun.org.apache.regexp.internal.RE;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Model {
    final int PORT_DEFAULT = -1;
    Controller controller;
    Thread thread;
    int count;
    boolean first = true;
    HTTPRequest httpRequest;
    HTTPSRequest httpsRequest;
    Request mainRequest = null;

    private final int OK_CODE = 200;
    private final int ERROR_CODE = 404;
    private final int MOVED_PERMANENTLY = 301;

    private boolean secure = false;
    private String startUrlOrAddress;
    private int port;
    private byte configFlag = 0;

    private ArrayList<String> errorLinkList;
    private HashSet<String> allSet;
    private ArrayList<String> toDoLinkList;
    private ArrayList<String> completedLinkList;


    Model(Controller controller)
    {
        this.controller = controller;
        httpRequest = new HTTPRequest();
        httpsRequest = new HTTPSRequest();
    }

    private void CompletedRequest(Request request)
    {
        if(request.getCode()==ERROR_CODE)
        {
            completedLinkList.add(request.getRecource());
            errorLinkList.add(request.getRecource());
        }
        if(request.getCode()==OK_CODE)
        {
            //System.out.println(123);
            completedLinkList.add(request.getRecource());

            if(request.isHTML()) {
                 ArrayList<String> arr = ParserHTML.parseHref(request.getContent(),count);
                for(int i =0; i< arr.size(); i++)
                {
                    if(!allSet.contains(arr.get(i))) {
                        toDoLinkList.add(arr.get(i));
                        allSet.add(arr.get(i));
                    }
                }
                 //toDoLinkList.addAll(ParserHTML.parseHref(request.getContent(),count));

            }
        }
        ContinueSearch();


    }

    private String[] ArrayListToArray(ArrayList <String> arrayList)
    {
        String[] stockArr = new String[arrayList.size()];
        stockArr = arrayList.toArray(stockArr);

        return  stockArr;
    }

    private void ContinueSearch()
    {
        if((toDoLinkList.size()!=0)&&(completedLinkList.size()<=count))
        {
            String elem =toDoLinkList.iterator().next();
            doOneRequest(mainRequest, startUrlOrAddress, elem, port);
            toDoLinkList.remove(elem);

            controller.updateData(ArrayListToArray(errorLinkList),ArrayListToArray(completedLinkList), count);
        }
        else
        {
            controller.endSearch();
        }
    }

    private void Config(Request request)
    {

        System.out.println(123);
        if(request.getCode()==OK_CODE)
        {
            System.out.print("!!!!!");
            first = false;
            mainRequest = request;
            CompletedRequest(request);
        }
        else
        {
            controller.SetError(new ErrorInputURL());
        }


    }

    private void ErrorInput(ErrorApp errorApp)
    {

    }
    public void stopThread()
    {
        thread.stop();
        controller.SetError("Поток остановлен");
    }

    private void doOneRequest(Request request, String startUrlOrAddress,  String recourse, int port)
    {
        Runnable runnable = () -> {
            try {
                try {
                    request.getRequest(startUrlOrAddress, recourse, port);
                }
                catch (NumberFormatException ex)
                {
                    ContinueSearch();
                }


                if(!first)
                    CompletedRequest(request);
                else {

                    Config(request);
                }
            } catch (ErrorInputURL errorInputURL) {
                ErrorInput(errorInputURL);
            }
            catch (Exception ex)
            {
                if(first)
                    configFlag++;
            }
        };

        thread = new Thread(runnable);
        thread.start();
    }



    public void makeSearch(String startUrlOrAddress, int port, int count, boolean isSecure) throws ErrorApp {
        if(port == PORT_DEFAULT)
        {
            port = 80;
        }
        completedLinkList = new ArrayList<>();
        errorLinkList = new ArrayList<>();
        toDoLinkList = new ArrayList<>();
        allSet = new HashSet<>();
        this.startUrlOrAddress = startUrlOrAddress;
        this.port = port;
        this.count = count;
        System.out.println(isSecure);
        if(isSecure==true)
            mainRequest = httpsRequest;
        else
            mainRequest=httpRequest;
        doOneRequest(mainRequest, startUrlOrAddress, "/", port);




    }

}
