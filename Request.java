package com.company;

import java.io.IOException;

public interface Request {

    void getRequest(String ip, String resource, int port) throws  ErrorInputURL, NumberFormatException;
    public int getCode();

    public boolean isHTML();

    public String getContent();
    public String getRecource();
}
