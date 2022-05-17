package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.*;

import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
 @author Daniel Olsson
 */

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
    private ArrayList<String> workoutExercises;



    public ExerciseSelectFrame(Controller controller, ArrayList<ExerciseInfo> exerciseInfos, CreateFrame createFrame) {
        this.controller = controller;
        this.exerciseInfos = exerciseInfos;
        this.createFrame = createFrame;
        workoutExercises = createFrame.getExerciseNames();
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
        Button back = new Button();
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_CLOSE, back.getUnselectedStyle()));
        back.addActionListener((e) -> {
            Form createForm = createFrame.getForm();
            createForm.setToolbar(new Toolbar());
            createForm.setBackCommand(new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    exerciseSelectForm.showBack();
                }
            });
            createForm.show();
        });
        topBar.add(back);

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
        SpanLabel descriptionText = new SpanLabel("");
        for(ExerciseInfo a : exerciseInfos){
            if(!workoutExercises.contains(a.getName())) {
                exercises.addItem(a);
            }
        }
        exercises.addActionListener(l -> {
            descriptionText.setText(exercises.getSelectedItem().getDescription());
            descriptionText.revalidate();
            exerciseSelectForm.revalidate();
        });
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
        Container descriptionContainer = new Container(BoxLayout.yBottom());
        Label description = new Label("Description:");
        descriptionContainer.add(description);
        descriptionContainer.add(descriptionText);
        bigContainer.add(descriptionContainer);
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
