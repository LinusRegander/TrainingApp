package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;
import java.util.Date;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
 @author Linus Regander, Daniel Olsson
 */

public class WorkoutLogFrame {
    private Controller controller;
    private Form logForm;
    private Button homeButton;
    private Button achievementButton;
    private Button programsButton;
    private Button settingsButton;
    private Button workoutButton;
    private Container navBar;
    private Container topBar;
    private Container logContainer;
    private ArrayList<LogWorkout> logWorkouts = new ArrayList<>();
    private ArrayList<WorkoutInfo> workouts = new ArrayList<>();

    public WorkoutLogFrame(Controller controller){
        this.controller = controller;
        startLogForm();
    }

    public void startLogForm(){
        logWorkouts = controller.getLogWorkoutList();
        workouts = controller.getWorkoutInfoList();
        logForm = new Form(new BorderLayout());
        logForm.setUIID("LogForm");
        topBar();
        workoutLog();
        navBar();
        logForm.show();
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

        logForm.add(NORTH, topBar);
    }
    public void workoutLog(){
        logContainer = new Container(BoxLayout.y());
        logContainer.setScrollableY(true);
        for(int i = 0; i < logWorkouts.size(); i++){
            int id = logWorkouts.get(i).getWorkoutId();
            System.out.println(id);
            String name = "";
            for(WorkoutInfo workoutInfo : workouts){
                if(id == workoutInfo.getId()){
                    System.out.println(workoutInfo.getName());
                    name = workoutInfo.getName();
                }
            }
            String creator = logWorkouts.get(i).getCreator();
            Date date = logWorkouts.get(i).getDate();
            String evaluation = logWorkouts.get(i).getEvaluation();
            MultiButton multiButton = new MultiButton(name);
            multiButton.setTextLine2("Click to see more");
            multiButton.addActionListener(l -> Dialog.show("Workout: " + id, "Created by: " + creator + "\n" + "Date: " + date + "\n" + evaluation, "OK", "Cancel"));
            logContainer.add(multiButton);
        }
        logForm.add(CENTER, logContainer);
        logForm.forceRevalidate();
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

        logForm.add(SOUTH, navBar);
    }
}
