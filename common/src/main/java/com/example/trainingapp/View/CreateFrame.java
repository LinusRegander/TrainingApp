package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.NORTH;
import static com.codename1.ui.layouts.BorderLayout.SOUTH;

public class CreateFrame {
    private Controller controller;
    private Form form;
    private Container container;
    private Container titleContainer;
    private Container prepareContainer;
    private Container workContainer;
    private Container restContainer;
    private Container cyclesContainer;
    private Container setsContainer;
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;
    private Button pMinus;
    private Button pPlus;
    private Button wMinus;
    private Button wPlus;
    private Button cMinus;
    private Button cPlus;
    private Button sMinus;
    private Button sPlus;
    private Label titleIcon;
    private Label title;
    private Label prepareIcon;
    private Label prepare;
    private Label pLabel;
    private Label wLabel;
    private Label workIcon;
    private Label work;
    private Label restIcon;
    private Label rest;
    private Label rLabel;
    private Label rMinus;
    private Label rPlus;
    private Label cyclesIcon;
    private Label cycles;
    private Label cLabel;
    private Label setsIcon;
    private Label sets;
    private Label sLabel;
    private Label timeIcon;
    private TextField titleField;
    private ArrayList<String> workoutList = new ArrayList<>();

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new Form(null, new BoxLayout(BoxLayout.Y_AXIS));
        form.setUIID("CreateForm");
        mainFrame();
        form.show();
    }

    public void mainFrame() {
        workoutContainer();
        createNavbar();
    }

    public void workoutContainer() {
        Container workoutContainer = new Container(BoxLayout.xCenter());
        workoutContainer.setUIID("WorkoutContainer");

        TextField workout = new TextField();
        workoutContainer.add(workout);

        TextField number = new TextField();
        String n = number.getText();
        workoutList.add(n);
        workoutContainer.add(number);

        TextField weight = new TextField();
        String w = weight.getText();
        workoutList.add(w);
        workoutContainer.add(weight);

        TextField reps = new TextField();
        String r = reps.getText();
        workoutList.add(r);
        workoutContainer.add(reps);

        Button addSet = new Button("+ Add Set");
        workoutContainer.add(addSet);
        form.add(workoutContainer);
    }

    public void titleContainer() {
        titleContainer = new Container(BoxLayout.xCenter());
        titleContainer.setUIID("TitleContainer");

        titleIcon = new Label();
        titleIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_TITLE, titleIcon.getUnselectedStyle()));
        titleContainer.add(titleIcon);

        title = new Label("Number");
        titleContainer.add(title);

        titleField = new TextField();
        titleContainer.add(titleField);

        form.add(titleContainer);
    }

    public void prepareContainer() {
        prepareContainer = new Container(BoxLayout.xCenter());
        prepareContainer.setUIID("PrepareContainer");

        prepareIcon = new Label();
        prepareIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DIRECTIONS_WALK, prepareIcon.getUnselectedStyle()));
        prepareContainer.add(prepareIcon);

        prepare = new Label("Prepare");
        prepareContainer.add(prepare);

        pMinus = new Button();
        pMinus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, pMinus.getUnselectedStyle()));
        prepareContainer.add(pMinus);

        pLabel = new Label();
        //pLabel.setText(workout.getValue);
        prepareContainer.add(pLabel);

        pPlus = new Button();
        pPlus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_CIRCLE, pPlus.getUnselectedStyle()));
        prepareContainer.add(pPlus);

        form.add(prepareContainer);
    }

    public void workContainer() {
        workContainer = new Container(BoxLayout.xCenter());
        workContainer.setUIID("WorkContainer");

        workIcon = new Label();
        workIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_FITNESS_CENTER, workIcon.getUnselectedStyle()));
        workContainer.add(workIcon);

        work = new Label("Work");
        workContainer.add(work);

        timeIcon = new Label();
        timeIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_TIMER, timeIcon.getUnselectedStyle()));
        workContainer.add(timeIcon);

        wMinus = new Button();
        wMinus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, wMinus.getUnselectedStyle()));
        workContainer.add(wMinus);

        wLabel = new Label();
        //pLabel.setText(workout.getValue);
        workContainer.add(wLabel);

        wPlus = new Button();
        wPlus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_CIRCLE, wPlus.getUnselectedStyle()));
        workContainer.add(wPlus);

        form.add(workContainer);
    }

    public void restContainer() {
        restContainer = new Container(BoxLayout.xCenter());
        restContainer.setUIID("RestContainer");

        restIcon = new Label();
        restIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DIRECTIONS_WALK, restIcon.getUnselectedStyle()));
        restContainer.add(restIcon);

        rest = new Label("Rest");
        restContainer.add(rest);

        timeIcon = new Label();
        timeIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_TIMER, timeIcon.getUnselectedStyle()));
        restContainer.add(timeIcon);

        rMinus = new Button();
        rMinus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, rMinus.getUnselectedStyle()));
        restContainer.add(rMinus);

        rLabel = new Label();
        //pLabel.setText(workout.getValue);
        restContainer.add(rLabel);

        rPlus = new Button();
        rPlus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_CIRCLE, rPlus.getUnselectedStyle()));
        restContainer.add(rPlus);

        form.add(restContainer);
    }

    public void cyclesContainer() {
        cyclesContainer = new Container(BoxLayout.xCenter());
        cyclesContainer.setUIID("CyclesContainer");

        cyclesIcon = new Label();
        cyclesIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LOOP, cyclesIcon.getUnselectedStyle()));
        cyclesContainer.add(cyclesIcon);

        cycles = new Label("Cycles");
        cyclesContainer.add(cycles);

        cMinus = new Button();
        cMinus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, cMinus.getUnselectedStyle()));
        cyclesContainer.add(cMinus);

        cLabel = new Label();
        //pLabel.setText(workout.getValue);
        cyclesContainer.add(cLabel);

        cPlus = new Button();
        cPlus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_CIRCLE, cPlus.getUnselectedStyle()));
        cyclesContainer.add(cPlus);

        form.add(cyclesContainer);
    }

    public void setsContainer() {
        setsContainer = new Container(BoxLayout.xCenter());
        setsContainer.setUIID("SetsContainer");

        setsIcon = new Label();
        setsIcon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, setsIcon.getUnselectedStyle()));
        setsContainer.add(setsIcon);

        sets = new Label("Sets");
        setsContainer.add(sets);

        sMinus = new Button();
        sMinus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, sMinus.getUnselectedStyle()));
        setsContainer.add(sMinus);

        sLabel = new Label();
        //pLabel.setText(workout.getValue);
        setsContainer.add(sLabel);

        sPlus = new Button();
        sPlus.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_CIRCLE, sPlus.getUnselectedStyle()));
        setsContainer.add(sPlus);

        form.add(setsContainer);
    }

    public void createNavbar() {
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

        form.add(container);
    }
}
