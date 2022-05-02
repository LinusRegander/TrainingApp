package com.example.trainingapp.View;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
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
import static com.codename1.ui.layouts.BorderLayout.*;

public class MainFrame {
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;
    private Button trainingLog;
    private Controller controller;
    private Container container;
    private Container mainContainer;
    private Container topbar;
    private Form mainForm;
    private Label sets;
    private Label something;
    private Label statistics;
    private Label totalWeight;
    private Label workouts;

    public MainFrame(Controller controller) {
        this.controller = controller;
        mainForm();
    }

    public void mainForm() {
        mainForm = new Form(new BorderLayout());
        mainForm.setUIID("MainForm");
        topbar();
        homeArea();
        navbar();
        mainForm.show();
    }

    public void topbar() {
        topbar = new Container(BoxLayout.xCenter());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        Button icon = new Button();
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        icon.addActionListener(l -> controller.openProfileFrame());
        topbar.add(icon);

        mainForm.add(NORTH, topbar);
    }

    public void homeArea() {
        mainContainer = new Container(BoxLayout.y());
        mainForm.add(CENTER, mainContainer);

        Container statisticsContainer = new Container(BoxLayout.y());
        statisticsContainer.setUIID("StatisticsContainer");
        mainContainer.add(statisticsContainer);

        Container a = new Container(BoxLayout.y());
        a.setUIID("mainSC");
        statisticsContainer.add(a);

        statistics = new Label("Statistics from last 7 days:");
        a.add(statistics);

        Container b = new Container(BoxLayout.xCenter());
        b.setUIID("mainWS");
        statisticsContainer.add(b);

        workouts = new Label("Workouts");
        b.add(workouts);

        sets = new Label("Sets");
        b.add(sets);

        Container c = new Container(BoxLayout.xCenter());
        c.setUIID("mainTS");
        statisticsContainer.add(c);

        totalWeight = new Label("Total Weight");
        c.add(totalWeight);

        something = new Label("Something");
        c.add(something);

        trainingLog = new Button("Training Log >");
        trainingLog.setUIID("TrainingLog");
        trainingLog.addActionListener(l -> controller.openProgramFrame());
        mainContainer.add(trainingLog);
    }

    public void navbar() {
        container = new Container(BoxLayout.xCenter());
        container.setUIID("Navbar");

        home = new Button();
        home.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, home.getUnselectedStyle()));
        home.addActionListener(l -> controller.openMainFrame());
        container.add(home);

        achievement = new Button();
        achievement.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievement.getUnselectedStyle()));
        achievement.addActionListener(l -> controller.openAchievementFrame());
        container.add(achievement);

        create = new Button();
        create.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, create.getUnselectedStyle()));
        create.addActionListener(l -> controller.openCreateFrame());
        container.add(create);

        program = new Button ();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button ();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        mainForm.add(SOUTH, container);
    }

    public Form getMainForm() {
        return mainForm;
    }
}
