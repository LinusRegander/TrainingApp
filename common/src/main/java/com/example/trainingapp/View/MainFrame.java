package com.example.trainingapp.View;

import com.codename1.ui.Form;
import com.codename1.ui.MenuBar;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

public class MainFrame {
    private Controller controller;
    private Form mainForm;
    private MenuBar menuBar;

    public MainFrame(Controller controller) {
        this.controller = controller;
        mainFrame();
    }

    public void mainFrame() {
        mainForm = new Form(null, BoxLayout.y());
        navBar();
        mainForm.show();
    }

    public void navBar() {
        menuBar = new MenuBar();
        mainForm.add(menuBar);
    }

    public Form getMainForm() {
        return mainForm;
    }
}
