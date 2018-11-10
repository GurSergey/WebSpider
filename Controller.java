package com.company;

import java.util.ArrayList;

public class Controller {
    Model model;
    View view;

    Controller ( View view)
    {
       // this.model = model;
        this.view = view;

    }

    public void SetError(ErrorApp errorApp)
    {
        view.SetError(errorApp.getTextError());
    }
    public void SetError(String error)
    {
        view.SetError(error);
    }

    public void doSearch(String address, String port, String count, boolean isSecure) {
        model = new Model(this);
        try {
            model.makeSearch(address, Integer.parseInt(port), Integer.parseInt(count), isSecure);
        }
        catch (NumberFormatException errorNum) {
            errorNum.printStackTrace();
        } catch (ErrorApp errorApp) {
            errorApp.printStackTrace();
        }
    }

    public void updateData(String[] errorLinkArray, String[] completedLinkArray, int count)
    {
        view.updateDataMainForm(errorLinkArray, completedLinkArray, count);
    }

    public void stopThread()
    {
        model.stopThread();
    }
    public void endSearch()
    {
        view.endSearch();
    }

}
