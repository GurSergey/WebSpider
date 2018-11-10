package com.company;

public class App {

    App()
    {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
    }
}
