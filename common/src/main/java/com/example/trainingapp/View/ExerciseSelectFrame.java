package com.example.trainingapp.View;

import HelperClasses.ExerciseInfo;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.*;

public class ExerciseSelectFrame {
    private Controller controller;
    private Form exerciseSelectForm;
    private Container exerciseSelectContainer;
    private Button homeButton;
    private Button achievementButton;
    private Button programsButton;
    private Button settingsButton;
    private Button workoutButton;
    private Container navBar;
    private Container topBar;
    private List<ExerciseInfo> exercises = new List<>();
    private ArrayList<ExerciseInfo> exerciseInfos;
    private CreateFrame createFrame;



    public ExerciseSelectFrame(Controller controller, ArrayList<ExerciseInfo> exerciseInfos, CreateFrame createFrame) {
        this.controller = controller;
        this.exerciseInfos = exerciseInfos;
        this.createFrame = createFrame;
        startExerciseSelectForm();
        exerciseSelectForm.revalidate();
    }
    public void startExerciseSelectForm(){
        exerciseSelectForm = new Form(new BorderLayout());
        topBar();
        exerciseSelector();
        navBar();
        exerciseSelectForm.show();
    }

    public void topBar(){
        topBar = new Container(BoxLayout.xCenter());
        topBar.setUIID("TopBar");

        Label title = new Label("FitHub");
        topBar.add(title);

        Button icon = new Button();
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        icon.addActionListener(l -> controller.openProfileFrame());
        topBar.add(icon);

        exerciseSelectForm.add(NORTH, topBar);
    }
    public void exerciseSelector(){
        Container bigContainer = new Container(BoxLayout.y());
        exerciseSelectContainer = new Container(BoxLayout.y());
        exerciseSelectContainer.setScrollableY(true);

        for(ExerciseInfo a : exerciseInfos){
            exercises.addItem(a);
        }
        //exercises.addActionListener(l -> Dialog.show("Description", exercises.getSelectedItem().getDescription(), "OK", "Cancel"));
        exerciseSelectContainer.add(exercises);
        bigContainer.add(exerciseSelectContainer);
        Container buttonContainer = new Container(BoxLayout.xCenter());
        Button addButton = new Button("Choose exercise");
        addButton.setUIID("CAchievementButton");
        addButton.addActionListener(l -> {
            createFrame.addExercise(exercises.getSelectedItem().getName(), exercises.getSelectedItem().getId());
            createFrame.getForm().show();
        });
        buttonContainer.add(addButton);
        bigContainer.add(buttonContainer);
        exerciseSelectForm.add(CENTER, bigContainer);
    }
    public void navBar() {
        navBar = new Container(BoxLayout.xCenter());
        navBar.setUIID("Navbar");

        homeButton = new Button();
        homeButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, homeButton.getUnselectedStyle()));
        homeButton.addActionListener(l -> controller.openMainFrame());
        navBar.add(homeButton);

        achievementButton = new Button();
        achievementButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievementButton.getUnselectedStyle()));
        achievementButton.addActionListener(l -> controller.openAchievementFrame());
        navBar.add(achievementButton);

        workoutButton = new Button();
        workoutButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, workoutButton.getUnselectedStyle()));
        workoutButton.addActionListener(l -> controller.openCreateFrame());
        navBar.add(workoutButton);

        programsButton = new Button ();
        programsButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, programsButton.getUnselectedStyle()));
        programsButton.addActionListener(l -> controller.openProgramFrame());
        navBar.add(programsButton);

        settingsButton = new Button ();
        settingsButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settingsButton.getUnselectedStyle()));
        settingsButton.addActionListener(l -> controller.openSettingsFrame());
        navBar.add(settingsButton);

        exerciseSelectForm.add(SOUTH, navBar);
    }
}
