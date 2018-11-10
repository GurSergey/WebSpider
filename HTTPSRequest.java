package com.company;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class HTTPSRequest implements Request {

    int code;
    boolean isHTML = false;
    String content;

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

    public void getRequest(String address, String resource, int port) throws NumberFormatException, ErrorInputURL {
        this.recource = resource;
        System.out.println(address);
        try {
            StringBuffer buffer = new StringBuffer();
//            SSLSocketFactory factory =(SSLSocketFactory)
//                     SSLSocketFactory.getDefault();
//            SSLSocket socket =
//                    (SSLSocket) factory.createSocket(InetAddress.getByName(address), port);
//
//            socket.startHandshake();
//
//            PrintWriter out = new PrintWriter(
//                    new BufferedWriter(
//                            new OutputStreamWriter(
//                                    socket.getOutputStream())));
//
//            out.println("GET " + resource + " HTTP/1.0");
//            out.println("Host: https://"+address);
//            //out.println("Accept-language: ru");
//            //out.println("Cookie: foo=bar");
//            //out.println("User-agent: BROWSER-DESCRIPTION-HERE");
//            out.println();
//            out.flush();
//           // out.flush();
//
//            if (out.checkError())
//                System.out.println(
//                        "SSLSocketClient:  java.io.PrintWriter error");
//
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(
//                            socket.getInputStream()),100000);


            URL obj = new URL("https://"+address+resource);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");





            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()),10000);
            isHTML = true;
            this.code = con.getResponseCode();

            String t;
            boolean readHeadFlag = false;
            boolean readContentFlag = false;
            while ((t = br.readLine()) != null)
                {

                   // System.out.println(t);
                    //заголовок первая строка
//                    if(!readHeadFlag)
//                    {
//                        String[]buf = t.split("\\s+");
//                        code = Integer.parseInt(buf[1]);
//                        readHeadFlag = true;
//
//                    }
//                    if(t.contains("Content-Type: text/html"))
//                    {
//                        this.isHTML = true;
//                    }
//                    if((t.isEmpty())&&(!readContentFlag))
//                    {
//                        readContentFlag = true;
//                    }
//                    if(readContentFlag)
//                    {
                        t = t.toLowerCase();
                        buffer.append(t);

                    //}
                    if(t.contains("</html>"))
                    {
                        break;
                    }
                }
//            in.close();
//            out.close();
//            socket.close();
            content = buffer.toString();

        }
        catch (UnknownHostException e)
        {
            throw new ErrorInputURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    private void doTunnelHandshake(Socket tunnel, String host, int port)
//            throws IOException
//    {
//        OutputStream out = tunnel.getOutputStream();
//        String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n"
//                + "User-Agent: "
//                + sun.net.www.protocol.http.HttpURLConnection.userAgent
//                + "\r\n\r\n";
//        byte b[];
//        try {
//            /*
//             * We really do want ASCII7 -- the http protocol doesn't change
//             * with locale.
//             */
//            b = msg.getBytes("ASCII7");
//        } catch (UnsupportedEncodingException ignored) {
//            /*
//             * If ASCII7 isn't there, something serious is wrong, but
//             * Paranoia Is Good (tm)
//             */
//            b = msg.getBytes();
//        }
//        out.write(b);
//        out.flush();
//
//        /*
//         * We need to store the reply so we can create a detailed
//         * error message to the user.
//         */
//        byte            reply[] = new byte[200];
//        int             replyLen = 0;
//        int             newlinesSeen = 0;
//        boolean         headerDone = false;     /* Done on first newline */
//
//        InputStream     in = tunnel.getInputStream();
//        boolean         error = false;
//
//        while (newlinesSeen < 2) {
//            int i = in.read();
//            if (i < 0) {
//                throw new IOException("Unexpected EOF from proxy");
//            }
//            if (i == '\n') {
//                headerDone = true;
//                ++newlinesSeen;
//            } else if (i != '\r') {
//                newlinesSeen = 0;
//                if (!headerDone && replyLen < reply.length) {
//                    reply[replyLen++] = (byte) i;
//                }
//            }
//        }
//
//        /*
//         * Converting the byte array to a string is slightly wasteful
//         * in the case where the connection was successful, but it's
//         * insignificant compared to the network overhead.
//         */
//        String replyStr;
//        try {
//            replyStr = new String(reply, 0, replyLen, "ASCII7");
//        } catch (UnsupportedEncodingException ignored) {
//            replyStr = new String(reply, 0, replyLen);
//        }
//
//        /* We asked for HTTP/1.0, so we should get that back */
//        if (!replyStr.startsWith("HTTP/1.0 200")) {
//            throw new IOException("Unable to tunnel through "
//                    + tunnelHost + ":" + tunnelPort
//                    + ".  Proxy returns \"" + replyStr + "\"");
//        }
//
//        /* tunneling Handshake was successful! */
//    }
