package com.example.trainingapp.View;

import HelperClasses.Exercise;
import HelperClasses.ExerciseInfo;
import HelperClasses.Set;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.example.trainingapp.Controller.Controller;
import com.example.trainingapp.Controller.ICallback;

import java.util.ArrayList;
import java.util.Date;

import static com.codename1.ui.layouts.BorderLayout.*;

public class CreateFrame implements ICallback {
    private Button achievement;
    private Button create;
    private Button home;
    private Button program;
    private Button settings;
    private Container navbar;
    private Container topbar;
    private Container workoutContainer;
    private Container exercisesContainer;
    private Controller controller;
    private Form form;
    private ArrayList<ExerciseInfo> exerciseInfo = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Boolean change = false;
    private int workoutId;

    /**
     * Constructor
     */
    public CreateFrame(Controller controller){
        this.controller = controller;

        createForm();
        controller.setInformee(this);
    }

    public CreateFrame(Controller controller, ArrayList<ExerciseInfo> exerciseInfos, int workoutId){
        this.controller = controller;
        this.workoutId = workoutId;

        createForm();
        controller.setInformee(this);
        populateWorkout(exerciseInfos);
    }

    /**
     * Creates the form
     * Initializes objects and builds the page
     */
    public void createForm(){
        form = new Form(null, new BorderLayout());
        topBar();
        workoutContainerReworked();
        navBar();
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
        accept.addActionListener(l -> getWorkoutInfo());
        top.add(accept);

        form.add(NORTH, topbar);
    }

    public void workoutContainerReworked(){
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        exercisesContainer = new Container(BoxLayout.y());
        workoutContainer.add(exercisesContainer);

        Container addExerciseContainer = new Container(BoxLayout.xCenter());
        Button addExerciseButton = new Button("+ Add exercise");
        addExerciseButton.addActionListener(l -> {
            controller.openExerciseSelectFrame(this);

        });
        addExerciseContainer.add(addExerciseButton);

        workoutContainer.add(addExerciseContainer);

        form.add(CENTER, workoutContainer);
    }

    public void addSet(Container setsContainer, int setCount, ArrayList<Set> sets){
        TableLayout layout = new TableLayout(1, 3);
        Container setContainer = new Container(layout);
        Button setButton = new Button(Integer.toString(setCount));
        setButton.setUIID("AchievementButton");
        setContainer.add(layout.createConstraint().widthPercentage(10), setButton);

        Set set = new Set(0, 0.0);
        sets.add(set);
        TextField weightTextField = new TextField("","Weight");
        weightTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setWeight(Double.parseDouble(weightTextField.getText()));
            }
        });
        setContainer.add(layout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        repTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setReps(Integer.parseInt(repTextField.getText()));
            }
        });
        setContainer.add(layout.createConstraint().widthPercentage(45), repTextField);

        setsContainer.add(setContainer);
        workoutContainer.revalidate();
    }

    public Container addExercise(String name, int id){
        Container exerciseContainer = new Container(BoxLayout.y());
        exerciseContainer.setUIID("Container2");

        Exercise exercise = new Exercise(name, id);
        exercises.add(exercise);
        Label exerciseName = new Label(exercise.getName());
        exerciseContainer.add(exerciseName);

        Container setsContainer = new Container(BoxLayout.y());
        TableLayout layout = new TableLayout(1, 3);
        Container setContainer = new Container(layout);
        Set set = new Set(0, 0);
        exercise.getSets().add(set);
        int[] setCount = new int[]{1};
        Button setButton = new Button(Integer.toString(setCount[0]));
        setButton.setUIID("AchievementButton");
        setContainer.add(layout.createConstraint().widthPercentage(10), setButton);

        TextField weightTextField = new TextField("","Weight");
        weightTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setWeight(Double.parseDouble(weightTextField.getText()));
            }
        });
        setContainer.add(layout.createConstraint().widthPercentage(45), weightTextField);
        TextField repTextField = new TextField("", "Reps");
        repTextField.addDataChangedListener(new DataChangedListener() {
            @Override
            public void dataChanged(int i, int i1) {
                set.setReps(Integer.parseInt(repTextField.getText()));
            }
        });
        setContainer.add(layout.createConstraint().widthPercentage(45), repTextField);

        setsContainer.add(setContainer);
        exerciseContainer.add(setsContainer);

        Button addSetButton = new Button("+ Add set");
        addSetButton.setUIID("AchievementButton");
        addSetButton.addActionListener(l -> {
            addSet(setsContainer, ++setCount[0], exercise.getSets());
            setChange(true);
        });
        exerciseContainer.add(addSetButton);

        exercisesContainer.add(exerciseContainer);
        exercisesContainer.revalidate();
        return setsContainer;
    }

    /**
     * Creates the navbar
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

        program = new Button();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new Button();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        form.add(SOUTH, navbar);
    }

    /**
     * Gets information about a workout
     */
    public void getWorkoutInfo(){
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
        TextField tempName = new TextField();

        Label description = new Label("Enter a description");
        TextField tempDescription = new TextField();

        Label tempTag = new Label("Enter 3 Tags:");

        ComboBox<String> tag1 = new ComboBox<>("Strength", "Hypertrophy", "Legs", "Chest", "Shoulders", "Biceps", "Triceps", "Arms", "Back", "Glutes");
        ComboBox<String> tag2 = new ComboBox<>("Strength", "Hypertrophy", "Legs", "Chest", "Shoulders", "Biceps", "Triceps", "Arms", "Back", "Glutes");
        ComboBox<String> tag3 = new ComboBox<>("Strength", "Hypertrophy", "Legs", "Chest", "Shoulders", "Biceps", "Triceps", "Arms", "Back", "Glutes");

        if(change) {
            tempA.add(name);
            tempA.add(tempName);
            tempA.add(description);
            tempA.add(tempDescription);
            tempA.add(tempTag);
            tempA.add(tag1);
            tempA.add(tag2);
            tempA.add(tag3);
        } else {
            description.setText("Evaluation");
            tempA.add(description);
            tempA.add(tempDescription);
        }

        Button finished = new Button("Finished");

        finished.addActionListener(l -> {
            if(change) {
                controller.addWorkoutInfo(tempName.getText(), controller.getLoggedInEmail(),
                        tempDescription.getText(), tag1.getSelectedItem(),
                        tag2.getSelectedItem(), tag3.getSelectedItem(), exercises);
            } else {
                inform(workoutId, tempDescription.getText());
            }
        });
        tempA.add(finished);
        tempForm.add(CENTER, tempA);
        tempForm.show();
    }

    /**
     * Gets the form
     */
    public Form getForm(){
        return form;
    }

    /**
     * Gets names of each exercise
     */
    public ArrayList<String> getExerciseNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Exercise exercise : exercises) {
            names.add(exercise.getName());
        }
        return names;
    }

    /**
     * Sets date to current date
     */
    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }

    @Override
    public void inform(int id, String evaluation){
        controller.addLogWorkout(controller.getLoggedInEmail(), id, getDate(), evaluation, exercises);
        controller.openMainFrame();
        controller.newCreateFrame();
    }

    public void populateWorkout(ArrayList<ExerciseInfo> exerciseInfos){
        ArrayList<Exercise> workout= new ArrayList<>();

        for (ExerciseInfo info : exerciseInfos) {
            Exercise tempExercise = new Exercise(info.getName(), info.getId(), info.getSetCount());
            workout.add(tempExercise);
        }
        for(Exercise exercise : workout){
            Container setsContainer = addExercise(exercise.getName(), exercise.getId());
            int setCount = exercise.getSetSize() - 1;
            for(int i = 0; i < setCount; i++){
                addSet(setsContainer, i + 2, exercise.getSets());
            }
        }
    }

    public void setChange(Boolean change) {
        this.change = change;
    }
}
