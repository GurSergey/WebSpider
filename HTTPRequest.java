package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPRequest implements Request {

    int code;
    boolean isHTML = false;

    public String getRecource() {
        return recource;
    }

    String recource;

    public int getCode() {
        return code;
    }

    public boolean isHTML() {
        return isHTML;
    }

    public String getContent() {
        return content;
    }

    String content;

    public void getRequest(String ip, String resource, int port) throws NumberFormatException,ErrorInputURL {

        this.recource = resource;
        System.out.println(resource);
        try {
            StringBuffer buffer = new StringBuffer();
           Socket s = new Socket(InetAddress.getByName(ip), port);
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.println("GET /"+resource+" HTTP/1.1");
            pw.println("Host: "+ip);
            pw.println("Accept-language: ru");
            pw.println("Cookie: foo=bar");
            pw.println("User-agent: BROWSER-DESCRIPTION-HERE");
            pw.println();
            pw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()),10000);

            String t;
            boolean readHeadFlag = false;
            boolean readContentFlag = false;
            while((t = br.readLine()) != null)
            {

                System.out.println(t);
                //заголовок первая строка
                if(!readHeadFlag)
                {
                    String[]buf = t.split("\\s+");
                    code = Integer.parseInt(buf[1]);
                    readHeadFlag = true;

                }
                if(t.contains("Content-Type: text/html"))
                {
                    this.isHTML = true;
                }
                if((t.isEmpty())&&(!readContentFlag))
                {
                    readContentFlag = true;
                }
                if(readContentFlag)
                {
                    t = t.toLowerCase();
                    buffer.append(t);

                }
                if(t.contains("</html>"))
                {
                    break;
                }
            }
            pw.close();
            s.close();
            br.close();
            content = buffer.toString();
            return ;
        }
        catch (UnknownHostException e)
        {
            throw new ErrorInputURL();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
