package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;
import java.util.Date;

import static com.codename1.ui.layouts.BorderLayout.*;

public class CreateFrame {
    private Controller controller;
    private Form form;
    private Container navbar;
    private Container topbar;
    private Container titleContainer;
    private Container prepareContainer;
    private Container workContainer;
    private Container restContainer;
    private Container cyclesContainer;
    private Container setsContainer;
    private Container workoutContainer;
    private Container topBar;
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;
    private TextField workout;

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("CreateForm");
        topbar();
        workoutContainer();
        navbar();
        form.show();
    }

    public void topbar() {
        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Button back = new Button();
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
        });
        top.add(back);

        Label title = new Label("FitHub");
        top.add(title);

        Button accept = new Button();
        accept.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DONE, accept.getUnselectedStyle()));
        top.add(accept);

        Container bottom = new Container(BoxLayout.xCenter());
        topbar.add(bottom);

        Label time = new Label("PlaceHolder Time");
        bottom.add(time);

        form.add(NORTH, topbar);
    }

    public void workoutContainer() {
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setUIID("WorkoutContainer");

        Container a = new Container(BoxLayout.y());
        a.setUIID("a");
        workoutContainer.add(a);

        workout = new TextField();
        a.add(workout);

        TextArea textArea = new TextArea();
        a.add(textArea);

        Container b = new Container(BoxLayout.yCenter());
        b.setUIID("b");
        workoutContainer.add(b);

        Button addSet = new Button("+ Add Set");
        b.add(addSet);

        Container c = new Container(BoxLayout.xCenter());
        c.setUIID("c");
        workoutContainer.add(c);

        Label totalWeight = new Label("Total Weight");
        c.add(totalWeight);

        Label averageWeight = new Label("Average Weight");
        c.add(averageWeight);

        Container d = new Container(BoxLayout.yCenter());
        d.setUIID("d");
        workoutContainer.add(d);

        Button addExercise = new Button("+ Add Exercise");
        d.add(addExercise);

        Container e = new Container(BoxLayout.xCenter());
        e.setUIID("e");
        workoutContainer.add(e);

        Label weightAmount = new Label("Total Weight");
        e.add(weightAmount);

        Label setAmount = new Label("Total Sets");
        e.add(setAmount);

        Label repAmount = new Label("Total Reps");
        e.add(repAmount);

        form.add(CENTER, workoutContainer);
    }

    public void navbar() {
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
        create.addActionListener(l -> controller.openCreateFrame());
        navbar.add(create);

        program = new Button ();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new Button ();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }
}
