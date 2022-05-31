package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.example.trainingapp.Controller.Controller;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.Collections;

import static com.codename1.ui.FontImage.setMaterialIcon;
import static com.codename1.ui.layouts.BorderLayout.*;

/**
 * The ProgramFrame class where you will be able to see workouts and programs.
 @author Linus Regander, Daniel Olsson
 */

public class ProgramFrame{
    private Button achievement;
    private Button create;
    private Button community;
    private Button home;
    private Button mine;
    private Button program;
    private Button settings;
    private Button workout;
    private Container d;
    private Container navbar;
    private Container topbar;
    private Container mainContainer;
    private Controller controller;
    private Form form;
    private ArrayList<WorkoutInfo> workoutInfoList = new ArrayList<>();
    private ArrayList<ProgramInfo> programInfoList = new ArrayList<>();
    private int id;
    private MultiButton add;
    private String name;
    private String description;
    private String tag1;
    private String tag2;
    private String tag3;
    private String username;
    private String email;

    /**
     * Constructor
     */
    public ProgramFrame (Controller controller){
        this.controller = controller;
        programForm();
    }

    /**
     * Creates the main form
     * Initalizes objects, arrays and builds the frame.
     */
    public void programForm(){
        workoutInfoList = controller.getWorkoutInfoList();
        form = new Form(null, new BorderLayout());
        form.setScrollableY(true);
        topBar();
        mainContainer();
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
    public void mainContainer(){
        mainContainer = new Container(BoxLayout.y());

        Container a = new Container(BoxLayout.y());
        a.setUIID("Container2");
        mainContainer.add(a);

        Container b = new Container(BoxLayout.xCenter());
        a.add(b);

        workout = new Button("Workout");
        workout.addActionListener(l -> allWorkouts());
        b.add(workout);

        program = new Button("Program");
        program.addActionListener(l -> allPrograms());
        b.add(program);

        Container c = new Container(BoxLayout.xCenter());
        a.add(c);

        community = new Button("Community");
        community.addActionListener(l -> communityStuff());
        c.add(community);

        mine = new Button("Mine");
        mine.addActionListener(l -> myWorkout());
        c.add(mine);

        d = new Container(BoxLayout.y());
        d.setScrollableY(true);
        mainContainer.add(d);

        Container e = new Container(BoxLayout.y());
        mainContainer.add(e);

        Button add = new Button("Add New");
        add.addActionListener(l -> controller.openCreateFrame());
        e.add(add);

        form.add(CENTER, mainContainer);
    }

    /**
     * Creates a new form where you can input information and create a workout
     */
    public void openWorkoutInfo(String name, String username, String email, String description, String tag1, String tag2, String tag3, ArrayList<ExerciseInfo> exercises, int workoutId){
        Form tempForm = new Form(BoxLayout.y());
        tempForm.setScrollableY(true);

        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Button back = new Button();
        back.setUIID("Button2");
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_CLOSE, back.getUnselectedStyle()));
        back.addActionListener((e) -> {
            form.setToolbar(new Toolbar());
            form.setBackCommand(new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    tempForm.showBack();
                }
            });
            form.show();
        });
        top.add(back);

        tempForm.add(topbar);

        Label title = new Label("FitHub");
        top.add(title);

        Container workoutContainer = new Container(BoxLayout.y());

        Label lblName = new Label("Name: " + name);
        workoutContainer.add(lblName);

        Label lblCreated = new Label("Created by:");
        workoutContainer.add(lblCreated);

        Label lblUsername = new Label(username);
        workoutContainer.add(lblUsername);

        Label lblEmail = new Label(email);
        workoutContainer.add(lblEmail);

        Label lblDescription = new Label("Description: " + description);
        workoutContainer.add(lblDescription);

        Label lblTags = new Label("Tags: " + tag1 + " " + tag2 + " " + tag3);
        workoutContainer.add(lblTags);

        Label lblExercises = new Label("Exercises:");
        workoutContainer.add(lblExercises);

        for(int i = 0; i < exercises.size(); i++){
            workoutContainer.add(new Label(exercises.get(i).getName()));
        }

        tempForm.add(workoutContainer);
        Container buttonContainer = new Container(BoxLayout.xCenter());
        Button startWorkoutButton = new Button("Start Workout");
        startWorkoutButton.addActionListener(l -> controller.openNewCreateFrame(exercises, workoutId));
        buttonContainer.add(startWorkoutButton);
        tempForm.add(buttonContainer);
        tempForm.show();
    }

    public void allWorkouts(){
        d.removeAll();

        for (int i = 0; i < workoutInfoList.size(); i++) {
            int id = workoutInfoList.get(i).getId();
            String name = workoutInfoList.get(i).getName();
            String username = workoutInfoList.get(i).getCreatorUsername();
            String email = workoutInfoList.get(i).getCreatorEmail();
            String description = workoutInfoList.get(i).getDescription();
            String tag1 = workoutInfoList.get(i).getTag1();
            String tag2 = workoutInfoList.get(i).getTag2();
            String tag3 = workoutInfoList.get(i).getTag3();
            ArrayList<ExerciseInfo> exercises = workoutInfoList.get(i).getExerciseInfos();
            add = new MultiButton(name);
            add.setTextLine2("Click to see more");
            add.addActionListener(l -> openWorkoutInfo(name, username, email, description, tag1, tag2, tag3, exercises, id));
            d.add(add);
        }

        form.repaint();
        form.revalidate();
    }

    public void communityStuff(){
        d.removeAll();

        for (int i = 0; i < workoutInfoList.size(); i++) {
            int id = workoutInfoList.get(i).getId();
            String name = workoutInfoList.get(i).getName();
            String username = workoutInfoList.get(i).getCreatorUsername();
            String email = workoutInfoList.get(i).getCreatorEmail();
            String description = workoutInfoList.get(i).getDescription();
            String tag1 = workoutInfoList.get(i).getTag1();
            String tag2 = workoutInfoList.get(i).getTag2();
            String tag3 = workoutInfoList.get(i).getTag3();
            ArrayList<ExerciseInfo> exercises = workoutInfoList.get(i).getExerciseInfos();

            if (!workoutInfoList.get(i).getCreatorEmail().equals(controller.getLoggedInEmail())) {
                add = new MultiButton(name);
                add.setTextLine2("Click to see more");
                add.addActionListener(l -> openWorkoutInfo(name, username, email, description, tag1, tag2, tag3, exercises, id));
                d.add(add);
            }
        }

        form.repaint();
        form.revalidate();
    }

    public void allPrograms(){
        d.removeAll();

        for (int i = 0; i < programInfoList.size(); i++) {
            int id = programInfoList.get(i).getId();
            String name = programInfoList.get(i).getName();
            String username = programInfoList.get(i).getCreatorUsername();
            String email = programInfoList.get(i).getCreatorEmail();
            String description = programInfoList.get(i).getDescription();
            String tag1 = programInfoList.get(i).getTag1();
            String tag2 = programInfoList.get(i).getTag2();
            String tag3 = programInfoList.get(i).getTag3();
            ArrayList<ExerciseInfo> exercises = workoutInfoList.get(i).getExerciseInfos();
            add = new MultiButton(name);
            add.setTextLine2("Click to see more");
            add.addActionListener(l -> openWorkoutInfo(name, username, email, description, tag1, tag2, tag3, exercises, id));
            d.add(add);
        }

        form.repaint();
        form.revalidate();
    }

    public void myWorkout(){
        d.removeAll();

        for (int i = 0; i < workoutInfoList.size(); i++) {
            int id = workoutInfoList.get(i).getId();
            String name = workoutInfoList.get(i).getName();
            String username = workoutInfoList.get(i).getCreatorUsername();
            String email = workoutInfoList.get(i).getCreatorEmail();
            String description = workoutInfoList.get(i).getDescription();
            String tag1 = workoutInfoList.get(i).getTag1();
            String tag2 = workoutInfoList.get(i).getTag2();
            String tag3 = workoutInfoList.get(i).getTag3();
            ArrayList<ExerciseInfo> exercises = workoutInfoList.get(i).getExerciseInfos();
            if (workoutInfoList.get(i).getCreatorEmail().equals(controller.getLoggedInEmail())) {
                add = new MultiButton(name);
                add.setTextLine2("Click to see more");
                add.addActionListener(l -> openWorkoutInfo(name, username, email, description, tag1, tag2, tag3, exercises, id));
                d.add(add);
            }
        }

        form.repaint();
        form.revalidate();
    }

    /**
     * Creates the navbar.
     */
    public void navBar(){
        navbar = new Container(BoxLayout.xCenter());
        navbar.setUIID("Navbar");

        home = new Button();
        home.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, home.getUnselectedStyle()));
        home.addActionListener(l -> controller.openMainFrame());
        navbar.add(home);

        achievement = new Button();
        achievement.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievement.getUnselectedStyle()));
        achievement.addActionListener(l -> controller.openAchievementFrame());
        navbar.add(achievement);

        create = new Button();
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

        program = new Button ();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new Button ();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }
}
