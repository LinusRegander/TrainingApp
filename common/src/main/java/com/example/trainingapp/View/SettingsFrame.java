package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
 * The SettingsFrame class for the Settings page.
 @author Linus Regander
 */

public class SettingsFrame{
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

    /**
     * Constructor
     */
    public SettingsFrame(Controller controller){
        this.controller = controller;
        settingsForm();
    }

    /**
     * Creates the form
     * Initializes objects and builds the page
     */
    public void settingsForm(){
        form = new Form(null, new BorderLayout());
        topBar();
        settingsFrame();
        navBar();
        form.show();
    }

    /**
     * Creates the topbar
     */
    public void topBar(){
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

        form.add(NORTH, topbar);
    }

    /**
     * Creates the central page
     */
    public void settingsFrame(){
        settingsContainer = new Container(BoxLayout.y());

        Container a = new Container(BoxLayout.y());
        a.setUIID("Container2");
        settingsContainer.add(a);

        profile = new Button("Profile");
        profile.addActionListener(l -> controller.openProfileFrame());
        a.add(profile);

        darkMode = new Button("Dark Mode");
        a.add(darkMode);

        lightMode = new Button("Light Mode");
        a.add(lightMode);

        form.add(CENTER, settingsContainer);
    }

    /**
     * Creates the navbar
     */
    public void navBar(){
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

        form.add(SOUTH, container);
    }

    /**
     * Gets the form
     */
    public Form getForm(){
        return form;
    }
}
