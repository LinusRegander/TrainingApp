package com.example.trainingapp.View;

import HelperClasses.Exercise;
import HelperClasses.ExerciseInfo;
import HelperClasses.LogExerciseSet;
import HelperClasses.Set;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.DataChangedListener;
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
    private int exerciseID = 0;
    private Label averageWeight;
    private Label repAmount;
    private Label setAmount;
    private Label totalWeight;
    private Label weightAmount;
    private String textFromArea;
    private TextArea textArea;
    private TextArea textArea2;
    private TextArea textAreaWeight;
    private TextField workout;
    private ArrayList<ExerciseInfo> exerciseInfo = new ArrayList<>();
    private ArrayList<Container> containers = new ArrayList<>();
    private TableLayout tableLayout = new TableLayout(1, 3);
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new Form(null, new BorderLayout());
        form.setScrollableY(true);
        form.setUIID("CreateForm");
        topbar();
        //workoutContainer();
        workoutContainerReworked();
        navbar();
        form.revalidate();
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

    private void refreshTheme(Form form) {
        Form c = Display.getInstance().getCurrent();
        c.refreshTheme();
        form.refreshTheme();
        form.revalidate();
    }

    public void workoutContainerReworked(){
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        Container exerciseContainer = new Container(BoxLayout.y());
        Container testExerciseContainer = new Container(BoxLayout.y());
        testExerciseContainer.setUIID("a");

        Exercise exercise = new Exercise("Deadlift", 1);
        exercises.add(exercise);
        Label exerciseName = new Label(exercise.getName());
        testExerciseContainer.add(exerciseName);

        Container setsContainer = new Container(BoxLayout.y());
        Container setContainer = new Container(tableLayout);
        Set set = new Set(0, 0);
        exercise.getSets().add(set);
        int[] setCount = new int[]{1};
        Button setButton = new Button(Integer.toString(setCount[0]));
        setButton.setUIID("AchievementButton");
        setContainer.add(tableLayout.createConstraint().widthPercentage(10), setButton);

        TextField weightTextField = new TextField("","Weight");
        weightTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setWeight(Double.parseDouble(weightTextField.getText()));
            }
        });
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        repTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setReps(Integer.parseInt(repTextField.getText()));
            }
        });
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), repTextField);

        setsContainer.add(setContainer);
        testExerciseContainer.add(setsContainer);

        Button addSetButton = new Button("+ Add set");
        addSetButton.setUIID("AchievementButton");
        addSetButton.addActionListener(l -> addSet(setsContainer, ++setCount[0], exercise.getSets()));
        testExerciseContainer.add(addSetButton);
        exerciseContainer.add(testExerciseContainer);

        workoutContainer.add(exerciseContainer);

        Container addExerciseContainer = new Container(BoxLayout.xCenter());
        Button addExerciseButton = new Button("+ Add exercise");
        addExerciseButton.setUIID("CAchievementButton");
        //TODO: change button functionality
        addExerciseButton.addActionListener(l -> new ExerciseSelectFrame(controller));
        addExerciseContainer.add(addExerciseButton);

        workoutContainer.add(addExerciseContainer);


        form.add(CENTER, workoutContainer);
    }

    public void addSet(Container setsContainer, int setCount, ArrayList<Set> sets){
        Container setContainer = new Container(tableLayout);
        Button setButton = new Button(Integer.toString(setCount));
        setButton.setUIID("AchievementButton");
        setContainer.add(tableLayout.createConstraint().widthPercentage(10), setButton);

        Set set = new Set(0, 0);
        sets.add(set);
        TextField weightTextField = new TextField("","Weight");
        weightTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setWeight(Double.parseDouble(weightTextField.getText()));
            }
        });
        setContainer.add(tableLayout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        repTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setReps(Integer.parseInt(repTextField.getText()));
            }
        });
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

        topbar = new Container(BoxLayout.y());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Button back = new Button();
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
        finished.addActionListener(l -> controller.addWorkoutInfo(tempName.getText(), controller.getLoggedInEmail(), tempDescription.getText(), tag1.getText(), tag2.getText(), tag3.getText(), exerciseInfo));
        finished.addActionListener(l -> controller.openMainFrame());
        tempA.add(finished);

        tempForm.add(CENTER, tempA);
        tempForm.show();
    }
}
