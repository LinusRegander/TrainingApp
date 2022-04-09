package com.example.trainingapp.View;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.example.trainingapp.Controller.Controller;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.codename1.ui.Image.createImage;

public class MainFrame {
    private Controller controller;
    private Form mainForm;
    private Form logForm;
    private Form planForm;
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
    private Container container;

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
        Button plan = new Button("Plan Workout");
        plan.addActionListener(l -> controller.openPlanFrame());
        mainForm.add(plan);

        Button log = new Button("Log Workout");
        log.addActionListener(l -> controller.openLogFrame());
        mainForm.add(log);
    }
}
