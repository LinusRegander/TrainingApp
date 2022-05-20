package com.example.trainingapp.View;

import HelperClasses.Exercise;
import HelperClasses.ExerciseInfo;
import HelperClasses.Set;
import HelperClasses.WorkoutInfo;
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

public class CreateProgramFrame {
    private Controller controller;
    private Form form;
    private Container mainContainer;
    private Container navbar;
    private Container topbar;
    private ArrayList<WorkoutInfo> workoutInfos = new ArrayList<>();

    public CreateProgramFrame(Controller controller){
        this.controller = controller;
        createForm();
    }

    public void createForm(){
        form = new Form(new BorderLayout());
        topBar();
        programContainer();
        navBar();
        form.show();
    }

    public void topBar(){
        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Button back = new Button();
        back.setUIID("Button2");
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_CLOSE, back.getUnselectedStyle()));
        back.addActionListener((e) -> {
            Form mainForm = controller.getMainForm();
            mainForm.setToolbar(new Toolbar());
            mainForm.setBackCommand(new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    form.showBack();
                }
            });
            mainForm.show();
            controller.newCreateFrame();
        });
        top.add(back);

        Label title = new Label("FitHub");
        top.add(title);

        Button accept = new Button();
        accept.setUIID("Button2");
        accept.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DONE, accept.getUnselectedStyle()));
        accept.addActionListener(l -> getProgramInfo());
        top.add(accept);
        form.add(NORTH, topbar);
    }

    public void programContainer() {
        mainContainer = new Container(BoxLayout.y());

        Container addProgramContainer = new Container(BoxLayout.xCenter());
        Button addProgram = new Button("+ Add Program");
        addProgram.addActionListener(l -> controller.openWorkoutSelectFrame(this));
        addProgramContainer.add(addProgram);

        mainContainer.add(addProgramContainer);
        form.add(CENTER, mainContainer);
    }

    public void addWorkout(int id, String name, String email, String description, String tag1, String tag2, String tag3, String username){
        Container workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setUIID("Container2");

        WorkoutInfo workoutInfo = new WorkoutInfo(id, name, email, description, tag1, tag2, tag3, username);
        workoutInfos.add(workoutInfo);
        Label workoutName = new Label(workoutInfo.getName());
        workoutContainer.add(workoutName);

        mainContainer.add(workoutContainer);
        mainContainer.revalidate();
    }

    public void getProgramInfo(){
        Form tempForm = new Form(null, new BorderLayout());

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

        tempForm.add(NORTH, topbar);

        Label title = new Label("FitHub");
        top.add(title);

        Container tempA = new Container(BoxLayout.y());

        Label name = new Label("Enter Workout Name:");
        tempA.add(name);

        TextField tempName = new TextField();
        tempA.add(tempName);

        Label description = new Label("Enter a description");
        tempA.add(description);

        TextField tempDescription = new TextField();
        tempA.add(tempDescription);

        Label tempTag = new Label("Enter 3 Tags:");
        tempA.add(tempTag);

        TextField tag1 = new TextField();
        tempA.add(tag1);

        TextField tag2 = new TextField();
        tempA.add(tag2);

        TextField tag3 = new TextField();
        tempA.add(tag3);

        Button finished = new Button("Finished");

        //TODO: Add program to database
        //finished.addActionListener(l -> controller.addProgramInfo(tempName.getText(), controller.getLoggedInEmail(), tempDescription.getText(), tag1.getText(), tag2.getText(), tag3.getText(), exerciseInfo));
        tempA.add(finished);
        tempForm.add(CENTER, tempA);
        tempForm.show();
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

    public ArrayList<String> getWorkoutNames(){
        ArrayList<String> names = new ArrayList<>();
        for (WorkoutInfo w : workoutInfos) {
            names.add(w.getName());
        }
        return names;
    }

    public Form getForm(){
        return form;
    }
}
