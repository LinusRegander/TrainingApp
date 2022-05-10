package com.example.trainingapp.View;

import HelperClasses.ExerciseInfo;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.codename1.ui.layouts.BorderLayout.*;

public class CreateFrame {
    private Button achievement;
    private Button addExercise;
    private Button addSet;
    private Button create;
    private Button home;
    private Button program;
    private Button settings;
    private Container a;
    private Container navbar;
    private Container temp;
    private Container topbar;
    private Container workoutContainer;
    private Controller controller;
    private Form form;
    private Label averageWeight;
    private Label repAmount;
    private Label setAmount;
    private Label totalWeight;
    private Label weightAmount;
    private String textFromArea;
    private TextArea textArea;
    private TextArea textArea2;
    private TextField workout;
    private ArrayList<ExerciseInfo> exerciseInfo = new ArrayList<>();
    private ArrayList<Container> containers = new ArrayList<>();
    private TableLayout tableLayout = new TableLayout(1, 3);

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("CreateForm");
        topbar();
        //workoutContainer();
        workoutContainerReworked();
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
        accept.addActionListener(l -> getWorkoutInfo());
        top.add(accept);

        Container bottom = new Container(BoxLayout.xCenter());
        topbar.add(bottom);

        Label time = new Label("PlaceHolder Time");
        bottom.add(time);

        form.add(NORTH, topbar);
    }

    public void workoutContainer() {
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        a = new Container(BoxLayout.y());
        containers.add(a);
        a.setUIID("a");
        workoutContainer.add(a);

        workout = new TextField();
        a.add(workout);

        temp = new Container(BoxLayout.y());
        a.add(temp);

        textArea = new TextArea();
        temp.add(textArea);

        textArea2 = new TextArea();
        temp.add(textArea2);

        Container b = new Container(BoxLayout.yCenter());
        containers.add(b);
        b.setUIID("b");
        a.add(b);

        addSet = new Button("+ Add Set");
        addSet.addActionListener(l -> newContainer());
        b.add(addSet);

        Container c = new Container(BoxLayout.xCenter());
        containers.add(c);
        c.setUIID("c");
        a.add(c);

        totalWeight = new Label("Total Weight");
        c.add(totalWeight);

        averageWeight = new Label("Average Weight");
        c.add(averageWeight);

        Container d = new Container(BoxLayout.yCenter());
        containers.add(d);
        d.setUIID("d");
        workoutContainer.add(d);

        addExercise = new Button("+ Add Exercise");
        d.add(addExercise);

        Container e = new Container(BoxLayout.xCenter());
        containers.add(e);
        e.setUIID("e");
        workoutContainer.add(e);

        weightAmount = new Label("Total Weight");
        e.add(weightAmount);

        setAmount = new Label("Total Sets");
        e.add(setAmount);

        repAmount = new Label("Total Reps");
        e.add(repAmount);

        form.add(CENTER, workoutContainer);
    }

    public void workoutContainerReworked(){
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        Container exerciseContainer = new Container(BoxLayout.y());
        Container testExerciseContainer = new Container(BoxLayout.y());
        testExerciseContainer.setUIID("a");

        Label exerciseName = new Label("Test exercise");
        testExerciseContainer.add(exerciseName);

        Container setsContainer = new Container(BoxLayout.y());
        Container setContainer = new Container(tableLayout);
        int[] setCount = new int[]{1};
        Button setButton = new Button(Integer.toString(setCount[0]));
        setButton.setUIID("AchievementButton");
        setContainer.add(tableLayout.createConstraint().widthPercentage(10), setButton);

        TextField weightTextField = new TextField("","Weight");
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), repTextField);
        
        setsContainer.add(setContainer);
        testExerciseContainer.add(setsContainer);

        Button addSetButton = new Button("+ Add set");
        addSetButton.setUIID("AchievementButton");
        addSetButton.addActionListener(l -> addSet(setsContainer, ++setCount[0]));
        testExerciseContainer.add(addSetButton);
        exerciseContainer.add(testExerciseContainer);

        workoutContainer.add(exerciseContainer);

        Container addExerciseContainer = new Container(BoxLayout.xCenter());
        Button addExerciseButton = new Button("+ Add exercise");
        addExerciseButton.setUIID("CAchievementButton");
        addExerciseContainer.add(addExerciseButton);

        workoutContainer.add(addExerciseContainer);


        form.add(CENTER, workoutContainer);
    }
    public void newContainer() {
        TextArea newArea = new TextArea();
        temp.add(newArea);

        TextArea newArea2 = new TextArea();
        temp.add(newArea2);
    }

    public void addSet(Container setsContainer, int setCount){
        Container setContainer = new Container(tableLayout);
        Button setButton = new Button(Integer.toString(setCount));
        setButton.setUIID("AchievementButton");
        setContainer.add(tableLayout.createConstraint().widthPercentage(10), setButton);

        TextField weightTextField = new TextField("","Weight");
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), repTextField);

        setsContainer.add(setContainer);
        setsContainer.revalidate();
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

        program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }

    public void getWorkoutInfo() {
        Form tempForm = new Form(null, new BorderLayout());

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
        finished.addActionListener(l -> controller.addWorkoutInfo(tempName.getText(), "email", tempDescription.getText(), tag1.getText(), tag2.getText(), tag3.getText(), exerciseInfo));
        finished.addActionListener(l -> controller.openProgramFrame());
        tempA.add(finished);

        tempForm.add(CENTER, tempA);
        tempForm.show();
    }
}
