package com.example.trainingapp.View;

import HelperClasses.Exercise;
import HelperClasses.ExerciseInfo;
import HelperClasses.Set;
import HelperClasses.WorkoutInfo;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.*;

public class WorkoutSelectFrame {
    private ArrayList<WorkoutInfo> workoutInfos = new ArrayList<>();
    private ArrayList<String> workoutList;
    private Controller controller;
    private Container mainContainer;
    private Container navbar;
    private Container topbar;
    private Container workoutSelectContainer;
    private CreateProgramFrame createProgramFrame;
    private Form form;
    private List<WorkoutInfo> workouts = new List<>();

    public WorkoutSelectFrame(Controller controller, ArrayList<WorkoutInfo> workoutInfos, CreateProgramFrame createProgramFrame){
        this.controller = controller;
        this.workoutInfos = workoutInfos;
        this.createProgramFrame = createProgramFrame;
        workoutList = createProgramFrame.getWorkoutNames();
        createForm();
    }

    public void createForm(){
        form = new Form(new BorderLayout());
        form.setScrollableY(true);
        topBar();
        workoutSelector();
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

    public void workoutSelector(){
        Container bigContainer = new Container(BoxLayout.y());
        workoutSelectContainer = new Container(BoxLayout.y());
        workoutSelectContainer.setScrollableY(true);
        SpanLabel descriptionText = new SpanLabel("");
        for(WorkoutInfo a : workoutInfos){
            if(!workoutList.contains(a.getName())) {
                workouts.addItem(a);
            }
        }
        workouts.addActionListener(l -> {
            descriptionText.setText(workouts.getSelectedItem().getDescription());
            descriptionText.revalidate();
            form.revalidate();
        });
        workoutSelectContainer.add(workouts);
        bigContainer.add(workoutSelectContainer);
        Container buttonContainer = new Container(BoxLayout.xCenter());
        Button addButton = new Button("Choose Workout");
        addButton.addActionListener(l -> {
            createProgramFrame.addWorkout(workouts.getSelectedItem().getId(), workouts.getSelectedItem().getName(), workouts.getSelectedItem().getCreatorEmail(), workouts.getSelectedItem().getDescription(), workouts.getSelectedItem().getTag1(), workouts.getSelectedItem().getTag2(), workouts.getSelectedItem().getTag3(), workouts.getSelectedItem().getCreatorUsername());
            createProgramFrame.getForm().show();
        });
        buttonContainer.add(addButton);
        bigContainer.add(buttonContainer);
        Container descriptionContainer = new Container(BoxLayout.yBottom());
        Label description = new Label("Description:");
        descriptionContainer.add(description);
        descriptionContainer.add(descriptionText);
        bigContainer.add(descriptionContainer);
        form.add(CENTER, bigContainer);
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
        navbar.add(create);
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
