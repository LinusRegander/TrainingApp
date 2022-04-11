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
import static com.codename1.ui.layouts.BorderLayout.SOUTH;

public class MainFrame {
    private Controller controller;
    private Form mainForm;
    private Container container;
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;

    public MainFrame(Controller controller) {
        this.controller = controller;
        mainFrame();
    }

    public void mainFrame() {
        mainForm = new Form(null, new BorderLayout());
        mainForm();
        mainForm.show();
    }

    public void mainForm() {
        container = new Container(BoxLayout.y());
        container.setUIID("Navbar");

        home = new Button("Home");
        home.addActionListener(l -> controller.openMainFrame());
        container.add(home);

        achievement = new Button("Achievements");
        achievement.addActionListener(l -> controller.openAchievementFrame());
        container.add(achievement);

        create = new Button("Create");
        create.addActionListener(l -> controller.openCreateFrame());
        container.add(create);

        program = new Button ("Program");
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button ("Settings");
        program.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        mainForm.add(SOUTH, container);
    }
}
