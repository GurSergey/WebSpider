package com.company;

public class View {

    private MainForm form;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Controller controller;

    View()
    {
        this.controller = controller;
        form = new MainForm(this);
    }


    public void updateDataMainForm(String[] errorLinkArray, String[] completedLinkArray, int count)
    {
        form.errorLinkList(errorLinkArray);
        form.LinkList(completedLinkArray, count);
    }

    public void clickButton(String address, String port, String count, boolean isSecure)
    {
        controller.doSearch(address, port, count, isSecure);
    }

    public void endSearch()
    {
        form.endSearch();
    }

    public void SetError(String textError)
    {
        form.SetError(textError);
    }
    public void stopThread()
    {
        controller.stopThread();
    }

}
