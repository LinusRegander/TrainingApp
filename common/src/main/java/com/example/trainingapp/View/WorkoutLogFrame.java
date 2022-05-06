package com.example.trainingapp.View;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.example.trainingapp.Controller.Controller;

import static com.codename1.ui.layouts.BorderLayout.*;

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

    public WorkoutLogFrame(Controller controller){
        this.controller = controller;
        startLogForm();
    }
    public String[] testWorkouts(){
        String [] workouts = new String[]{"Workout 1", "Workout 2", "Workout 3", "Workout 4","Workout 5","Workout 6","Workout 7","Workout 8","Workout 9","Workout 10","Workout 11","Workout 12","Workout 13"};
        return workouts;
    }
    public void startLogForm(){
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
        String[] workouts = testWorkouts();
        for(int i = 0; i < workouts.length; i++){
            MultiButton multiButton = new MultiButton(workouts[i]);
            multiButton.setTextLine2("Click to see more");
            multiButton.addActionListener(l -> Dialog.show("Clicked", "Something happened", "OK", "Cancel"));
            logContainer.add(multiButton);
        }
        logForm.add(CENTER, logContainer);
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
