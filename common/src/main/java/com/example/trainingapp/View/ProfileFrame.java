package com.example.trainingapp.View;

import com.codename1.components.ImageViewer;
import com.codename1.io.Storage;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import java.io.IOException;
import java.io.InputStream;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
 * The ProfileFrame class for the page where you will be able to see your profile.
 @author Linus Regander
 */

public class ProfileFrame {
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;
    private Controller controller;
    private Container container;
    private Container profileContainer;
    private Container topbar;
    private Form profileForm;
    private int workoutCount = 0;
    private int repCount = 0;
    private int setCount = 0;
    private int finishedWorkouts = 0;
    private int programCount = 0;
    private Label email;
    private Label icon;
    private Label username;
    private Label workout;
    private Label rep;
    private Label set;
    private Label finished;
    private Label programs;

    /**
     * Constructor
     */
    public ProfileFrame(Controller controller) {
        this.controller = controller;
        profileForm();
    }

    /**
     * Creates the form
     * Initializes objects and builds the frame.
     */
    public void profileForm() {
        profileForm = new Form(new BorderLayout());
        topBar();
        profileFrame();
        navBar();
        profileForm.show();
    }

    /**
     * Creates the topbar
     */
    public void topBar() {
        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);
        profileForm.add(NORTH, topbar);
    }

    /**
     * Creates the central page.
     */
    public void profileFrame() {
        profileContainer = new Container(BoxLayout.y());

        Container a = new Container(BoxLayout.xCenter());
        a.setUIID("Container2");
        profileContainer.add(a);

        icon = new Label();
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        a.add(icon);

        Container b = new Container(BoxLayout.y());
        profileContainer.add(b);

        username = new Label();
        username.setText("Username: " + controller.getUsername());
        b.add(username);

        email = new Label();
        email.setText("Email: " + controller.getLoggedInEmail());
        b.add(email);

        Label statistics = new Label("Statistics: ");
        statistics.setUIID("Statistics");
        b.add(statistics);

        workout = new Label();
        workout.setText("Created Workouts: " + controller.workoutCount());
        b.add(workout);

        set = new Label();
        set.setText("Sets: " + setCount);
        b.add(set);

        rep = new Label();
        rep.setText("Reps: " + repCount);
        b.add(rep);

        finished = new Label();
        finished.setText("Finished Workouts: " + finishedWorkouts);
        b.add(finished);

        programs = new Label();
        programs.setText("Programs: " + programCount);
        b.add(programs);

        profileForm.add(CENTER, profileContainer);
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
        create.addActionListener(l -> controller.openCreateFrame());
        container.add(create);

        program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        profileForm.add(SOUTH, container);
    }

    public Form getProfileForm() {
        return profileForm;
    }
}
