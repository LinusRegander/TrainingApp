package com.example.trainingapp.View;

import HelperClasses.WorkoutInfo;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.*;

public class WorkoutSelectFrame {
    private ArrayList<WorkoutInfo> workoutInfos = new ArrayList<>();
    private Controller controller;
    private Container topbar;
    private Container mainContainer;
    private Container navbar;
    private CreateProgramFrame createProgramFrame;
    private Form form;

    public WorkoutSelectFrame(Controller controller, ArrayList<WorkoutInfo> workoutInfos, CreateProgramFrame createProgramFrame){
        this.controller = controller;
        this.workoutInfos = workoutInfos;
        this.createProgramFrame = createProgramFrame;
        createForm();
    }

    public void createForm(){
        form = new Form(new BorderLayout());
        form.setScrollableY(true);
        topBar();
        workoutContainer();
        navBar();
        form.show();
    }

    public void topBar(){
        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        form.add(NORTH, topbar);
    }

    public void workoutContainer(){
        mainContainer = new Container(BoxLayout.y());
        form.add(CENTER, mainContainer);
    }

    public void navBar(){
        navbar = new Container(BoxLayout.xCenter());
        navbar.setUIID("Navbar");

        Button home = new Button();
        home.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, home.getUnselectedStyle()));
        home.addActionListener(l -> controller.openMainFrame());
        navbar.add(home);

        Button achievement = new Button();
        achievement.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievement.getUnselectedStyle()));
        achievement.addActionListener(l -> controller.openAchievementFrame());
        navbar.add(achievement);

        Button create = new Button();
        create.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, create.getUnselectedStyle()));
        create.addActionListener(l -> controller.openCreateFrame());
        navbar.add(create);

        Button program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        Button settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }
}
