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



    public ExerciseSelectFrame(Controller controller) {
        this.controller = controller;
        startExerciseSelectForm();
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
        exerciseSelectContainer = new Container(BoxLayout.y());
        exerciseSelectContainer.setScrollableY(true);
        controller.updateExerciseList();
        ArrayList<ExerciseInfo> temp = controller.getExerciseList();

        for(ExerciseInfo a : temp){
            exercises.addItem(a);
        }
        exerciseSelectContainer.add(exercises);
        exerciseSelectForm.add(CENTER, exerciseSelectContainer);
        exerciseSelectForm.revalidate();
        exerciseSelectForm.repaint();
        exerciseSelectContainer.revalidate();
        exerciseSelectContainer.repaint();

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
