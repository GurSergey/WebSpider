package com.company;

public class ErrorInputGap extends ErrorApp {

    ErrorInputGap(int minGap, int maxGap)
    {
        textError = "Пределы количества обработанных страниц от "+minGap+" до "+maxGap;
    }
    public final String textError ;

}
