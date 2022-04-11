package com.example.trainingapp.View;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.SOUTH;

public class SettingsFrame {
    private Controller controller;
    private Form form;
    private Container container;
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;

    public SettingsFrame(Controller controller) {
        this.controller = controller;
        settingsForm();
    }

    public void settingsForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("SettingsForm");
        settingsNavbar();
        form.show();
    }

    public void settingsNavbar() {
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
