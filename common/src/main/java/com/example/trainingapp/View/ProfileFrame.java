package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.*;

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
    private Label email;
    private Label icon;
    private Label username;

    public ProfileFrame(Controller controller) {
        this.controller = controller;
        profileForm();
    }

    public void profileForm() {
        profileForm = new Form(new BorderLayout());
        profileForm.setUIID("ProfileForm");
        topbar();
        profileFrame();
        navbar();
        profileForm.show();
    }

    public void topbar() {
        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        profileForm.add(NORTH, topbar);
    }

    public void profileFrame() {
        profileContainer = new Container(BoxLayout.y());
        profileContainer.setUIID("ProfileContainer");

        Container a = new Container(BoxLayout.xCenter());
        a.setUIID("a");
        profileContainer.add(a);

        icon = new Label();
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        a.add(icon);

        Container b = new Container(BoxLayout.y());
        profileContainer.add(b);

        username = new Label();
        username.setText("Place Holder");
        b.add(username);

        email = new Label();
        email.setText("Place Holder");
        b.add(email);

        profileForm.add(CENTER, profileContainer);
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

        profileForm.add(SOUTH, container);
    }
}
