package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.FontImage.setMaterialIcon;
import static com.codename1.ui.layouts.BorderLayout.*;

public class ProgramFrame {
    private Button achievement;
    private Button addProgram;
    private Button create;
    private Button community;
    private Button home;
    private Button mine;
    private Button program;
    private Button programs;
    private Button settings;
    private Button workout;
    private Container navbar;
    private Container topbar;
    private Container mainContainer;
    private Controller controller;
    private Form form;

    public ProgramFrame (Controller controller) {
        this.controller = controller;
        programForm();
    }

    public void programForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("ProgramForm");
        topbar();
        searchBar();
        mainContainer();
        navbar();
        form.show();
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

        form.add(NORTH, topbar);
    }

    public void mainContainer() {
        mainContainer = new Container(BoxLayout.y());
        mainContainer.setUIID("ProgramContainer1");

        Container a = new Container(BoxLayout.y());
        a.setUIID("ProgramContainer2");
        mainContainer.add(a);

        Container b = new Container(BoxLayout.xCenter());
        a.add(b);

        workout = new Button("Workout");
        b.add(workout);

        program = new Button("Program");
        b.add(program);

        Container c = new Container(BoxLayout.xCenter());
        a.add(c);

        community = new Button("Community");
        c.add(community);

        mine = new Button("Mine");
        c.add(mine);

        Container d = new Container(BoxLayout.y());
        d.setUIID("ProgramContainer2");
        mainContainer.add(d);

        Label temporary = new Label("My Workout 1");
        d.add(temporary);

        addProgram = new Button();
        addProgram.setUIID("AddProgram");
        addProgram.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, addProgram.getUnselectedStyle()));
        mainContainer.add(addProgram);

        form.add(CENTER, mainContainer);
    }

    public void searchBar() {
        Toolbar.setGlobalToolbar(true);
        form.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                for (Component cmp : form.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                form.getContentPane().animateLayout(150);
            }
        });
    }

    public void navbar() {
        navbar = new Container(BoxLayout.xCenter());
        navbar.setUIID("Navbar");

        home = new Button();
        home.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, home.getUnselectedStyle()));
        home.addActionListener(l -> controller.openMainFrame());
        navbar.add(home);

        achievement = new Button();
        achievement.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievement.getUnselectedStyle()));
        achievement.addActionListener(l -> controller.openAchievementFrame());
        navbar.add(achievement);

        create = new Button();
        create.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, create.getUnselectedStyle()));
        create.addActionListener(l -> controller.openCreateFrame());
        navbar.add(create);

        program = new Button ();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new Button ();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }
}
