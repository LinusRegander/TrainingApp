package com.example.trainingapp.View;

import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.example.trainingapp.Controller.Controller;

import java.io.IOException;

import static com.codename1.ui.Image.createImage;

public class MainFrame {
    private Controller controller;
    private Form mainForm;
    private Container ct;
    private Label lAch;
    private Label lHome;
    private Label lPlus;
    private Label lSear;
    private Label lSett;
    private Label lStat;
    private Label lUser;
    private Image ach;
    private Image home;
    private Image plus;
    private Image sear;
    private Image sett;
    private Image stat;
    private Image user;

    public MainFrame(Controller controller) {
        this.controller = controller;
        mainFrame();
    }

    public void mainFrame() {
        mainForm = new Form(null, BoxLayout.y());
        mainForm();
        mainForm.show();
    }

    public void mainForm() {
        testToolbar();
        createToolbar();
    }

    public void createToolbar() {
        try {
            Label i = new Label();
            Image img = Image.createImage("achievements16x16.png");
            i.setIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testToolbar() {
        Toolbar.setGlobalToolbar(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_WARNING, s);
        mainForm.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        mainForm.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));
    }
}
