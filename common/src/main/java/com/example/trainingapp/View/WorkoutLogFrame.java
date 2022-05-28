package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;
import java.util.Date;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
 * The WorkoutLogFrame class for the log workout page.
 @author Linus Regander, Daniel Olsson
 */

public class WorkoutLogFrame{
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

    /**
     * Constructor
     */
    public WorkoutLogFrame(Controller controller){
        this.controller = controller;
        startLogForm();
    }

    /**
     * Creates the form
     * Initializes objects, arrays and builds the page
     */
    public void startLogForm(){
        logWorkouts = controller.getLogWorkoutList();
        workouts = controller.getWorkoutInfoList();
        logForm = new Form(new BorderLayout());
        topBar();
        workoutLog();
        navBar();
        logForm.repaint();
        logForm.revalidate();
        logForm.show();
    }

    /**
     * Creates the topbar
     */
    public void topBar(){
        topBar = new Container(BoxLayout.xCenter());
        topBar.setUIID("TopBar");

        Label title = new Label("FitHub");
        topBar.add(title);

        Button icon = new Button();
        icon.setUIID("Button2");
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        icon.addActionListener(l -> controller.openProfileFrame());
        topBar.add(icon);

        logForm.add(NORTH, topBar);
    }

    /**
     * Creates the central page
     */
    public void workoutLog(){
        logContainer = new Container(BoxLayout.y());
        logContainer.setScrollableY(true);
        for(int i = 0; i < logWorkouts.size(); i++){
            int id = logWorkouts.get(i).getWorkoutId();
            System.out.println("ö" + id);
            String name = "";
            for(WorkoutInfo workoutInfo : workouts){
                System.out.println("hej");
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

    /**
     * Creates the navbar
     */
    public void navBar(){
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

        Button create = new Button();
        create.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, create.getUnselectedStyle()));
        navBar.add(create);
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog();
                d.setLayout(BoxLayout.xCenter());
                Button workout = new Button("Create Workout");
                workout.addActionListener(l -> controller.openCreateFrame());
                d.addComponent(workout);
                Button program = new Button("Create Program");
                program.addActionListener(l -> controller.openCreateProgramFrame());
                d.addComponent(program);
                d.showPopupDialog(create);
            }
        });

        programsButton = new Button ();
        programsButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, programsButton.getUnselectedStyle()));
        programsButton.addActionListener(l -> controller.openProgramFrame());
        navBar.add(programsButton);

        settingsButton = new Button ();
        settingsButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settingsButton.getUnselectedStyle()));
        settingsButton.addActionListener(l -> controller.openSettingsFrame());
        navBar.add(settingsButton);

        logForm.add(SOUTH, navBar);
    }
}
