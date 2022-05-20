package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
import java.util.ArrayList;

import static com.codename1.ui.Image.createImage;
import static com.codename1.ui.layouts.BorderLayout.*;

/**
 * MainFrame class for the main page you will be taken to after having logged in to the application.
 @author Linus Regander, Daniel Olsson, William Dock, Yun-Bo Chow
 */

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
    private Label statistics;
    private Label workouts;

    /**
     * Constructor
     */
    public MainFrame(Controller controller){
        this.controller = controller;
        mainForm();
    }

    /**
     * Creates the main form.
     * Initializes objects, lists and builds the frame.
     */
    public void mainForm(){
        mainForm = new Form(new BorderLayout());
        topBar();
        homeArea();
        navBar();
        mainForm.show();
    }

    /**
     * Creates the Topbar.
     */
    public void topBar() {
        topbar = new Container(BoxLayout.xCenter());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        Button icon = new Button();
        icon.setUIID("Button2");
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        icon.addActionListener(l -> controller.openProfileFrame());
        topbar.add(icon);

        mainForm.add(NORTH, topbar);
    }

    /**
     * Creates the central page.
     */
    public void homeArea() {
        mainContainer = new Container(BoxLayout.y());
        mainForm.add(CENTER, mainContainer);

        Container statisticsContainer = new Container(BoxLayout.y());
        mainContainer.add(statisticsContainer);

        Container a = new Container(BoxLayout.xCenter());
        a.setUIID("mainC");
        statisticsContainer.add(a);

        Container b = new Container(BoxLayout.xCenter());
        b.setUIID("mainC");
        statisticsContainer.add(b);

        statistics = new Label("Statistics from last 7 days:");
        a.add(statistics);

        workouts = new Label();
        workouts.setText("Workouts: " + controller.workoutCount());
        b.add(workouts);

        sets = new Label("Sets: " + 0);
        b.add(sets);

        trainingLog = new Button("Training Log >");
        trainingLog.addActionListener(l -> controller.openWorkoutLogFrame());
        statisticsContainer.add(trainingLog);
    }

    /**
     * Creates the navbar
     */
    public void navBar() {
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
        container.add(create);
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog();
                d.setLayout(BoxLayout.xCenter());
                Button workout = new Button("Create Workout");
                workout.addActionListener(l -> controller.openCreateFrame());
                d.addComponent(workout);
                Button program = new Button("Create Program");
                program.addActionListener(l -> controller.openCreateProgramFrame());
                d.addComponent(program);
                d.showPopupDialog(create);
            }
        });

        program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        mainForm.add(SOUTH, container);
    }

    public Form getMainForm() {
        return mainForm;
    }
}
