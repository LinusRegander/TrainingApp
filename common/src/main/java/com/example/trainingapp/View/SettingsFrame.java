package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.*;

public class SettingsFrame {
    private Button home;
    private Button achievement;
    private Button create;
    private Button darkMode;
    private Button lightMode;
    private Button profile;
    private Button program;
    private Button settings;
    private Container container;
    private Container settingsContainer;
    private Container topbar;
    private Controller controller;
    private Form form;

    public SettingsFrame(Controller controller) {
        this.controller = controller;
        settingsForm();
    }

    public void settingsForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("SettingsForm");
        topbar();
        settingsFrame();
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

    public void settingsFrame() {
        settingsContainer = new Container(BoxLayout.y());
        settingsContainer.setUIID("SettingsContainer");

        Container a = new Container(BoxLayout.y());
        a.setUIID("Settings1");
        settingsContainer.add(a);

        profile = new Button("Profile");
        profile.setUIID("ProfileProfile");
        profile.addActionListener(l -> controller.openProfileFrame());
        a.add(profile);

        darkMode = new Button("Dark Mode");
        darkMode.setUIID("SettingsDark");
        a.add(darkMode);

        lightMode = new Button("Light Mode");
        lightMode.setUIID("SettingsLight");
        a.add(lightMode);

        form.add(CENTER, settingsContainer);
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

        program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        form.add(SOUTH, container);
    }
}
