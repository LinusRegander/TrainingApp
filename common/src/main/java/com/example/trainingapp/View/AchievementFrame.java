package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.*;

public class AchievementFrame {
    private Button achievement;
    private Button bAchievement1;
    private Button bAchievement2;
    private Button bAchievement3;
    private Button bAchievement4;
    private Button bAchievement5;
    private Button create;
    private Button home;
    private Button program;
    private Button settings;
    private Container container;
    private Container mainContainer;
    private Container topbar;
    private Controller controller;
    private Form form;
    private Label descriptionText;
    private Label lAchievement;
    private Label statusText;

    public AchievementFrame(Controller controller) {
        this.controller = controller;
        achievementForm();
    }

    public void achievementForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("AchievementForm");
        topbar();
        achievementFrame();
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

    public void achievementFrame() {
        mainContainer = new Container(BoxLayout.y());
        mainContainer.setUIID("AchievementContainer");

        Container a = new Container(BoxLayout.xCenter());
        mainContainer.add(a);

        lAchievement = new Label("Achievements");
        a.add(lAchievement);

        Container b = new Container(BoxLayout.yCenter());
        mainContainer.add(b);

        bAchievement1 = new Button();
        bAchievement1.setUIID("AchievementButton");
        bAchievement1.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, bAchievement1.getUnselectedStyle()));
        bAchievement1.addActionListener(e -> statusText.setText("Unfinished"));
        b.add(bAchievement1);

        bAchievement2 = new Button();
        bAchievement2.setUIID("CAchievementButton");
        bAchievement2.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, bAchievement2.getUnselectedStyle()));
        b.add(bAchievement2);

        bAchievement3 = new Button();
        bAchievement3.setUIID("AchievementButton");
        bAchievement3.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, bAchievement3.getUnselectedStyle()));
        b.add(bAchievement3);

        bAchievement4 = new Button();
        bAchievement4.setUIID("AchievementButton");
        bAchievement4.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, bAchievement4.getUnselectedStyle()));
        b.add(bAchievement4);

        bAchievement5 = new Button();
        bAchievement5.setUIID("CAchievementButton");
        bAchievement5.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, bAchievement5.getUnselectedStyle()));
        b.add(bAchievement5);

        Container description = new Container(BoxLayout.y());
        description.setUIID("DescriptionContainer");
        mainContainer.add(description);

        Container c = new Container(BoxLayout.x());
        description.add(c);

        Label lDescription = new Label("Description: ");
        c.add(lDescription);

        descriptionText = new Label();
        c.add(descriptionText);

        Container d = new Container(BoxLayout.x());
        description.add(d);

        Label status = new Label("Status: ");
        d.add(status);

        statusText = new Label();
        d.add(statusText);

        form.add(CENTER, mainContainer);
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

        form.add(SOUTH, container);
    }
}
