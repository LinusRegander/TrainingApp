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
            ach = Image.createImage("/achievements16x16.png");
            home = Image.createImage("/home16x16.png");
            plus = Image.createImage("/plusSign16x16.png");
            sear = Image.createImage("/search16x16.png");
            sett = Image.createImage("/settings16x16.png");
            stat = Image.createImage("/stats16x16.png");
            user = Image.createImage("/user16x16.png");
            lAch.setIcon(ach);
            lHome.setIcon(home);
            lPlus.setIcon(plus);
            lSear.setIcon(sear);
            lSett.setIcon(sett);
            lStat.setIcon(stat);
            lUser.setIcon(user);

            mainForm.add(lAch);
            mainForm.add(lHome);
            mainForm.add(lPlus);
            mainForm.add(lSear);
            mainForm.add(lSett);
            mainForm.add(lStat);
            mainForm.add(lUser);
        } catch (Exception e) {}
    }

    public void testToolbar() {
        Toolbar.setGlobalToolbar(true);
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_WARNING, s);
        mainForm.getToolbar().addCommandToOverflowMenu("Overflow", icon, (e) -> Log.p("Clicked"));
        mainForm.getToolbar().addCommandToSideMenu("Sidemenu", icon, (e) -> Log.p("Clicked"));
    }
}
