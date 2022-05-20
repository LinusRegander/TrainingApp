package com.example.trainingapp.View;

import HelperClasses.*;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.example.trainingapp.Controller.Controller;
import com.example.trainingapp.Controller.ICallback;

import java.util.ArrayList;
import java.util.Date;

/**
 * The CreateFrame class for the Create page where you create workouts.
 @author Linus Regander, Daniel Olsson, Yun-Bo Chow, William Dock
 */

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

    /**
     * Constructor
     */
    public CreateFrame(Controller controller){
        this.controller = controller;
        createForm();
        controller.setInformee(this);
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

        Container bottom = new Container(BoxLayout.xCenter());
        topbar.add(bottom);

        Label time = new Label("PlaceHolder Time");
        bottom.add(time);

        form.add(NORTH, topbar);
    }

    public void workoutContainerReworked(){
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        exercisesContainer = new Container(BoxLayout.y());
        /*Container testExerciseContainer = new Container(BoxLayout.y());
        testExerciseContainer.setUIID("a");

        Exercise exercise = new Exercise("Deadlift", 1);
        exercises.add(exercise);
        Label exerciseName = new Label(exercise.getName());
        testExerciseContainer.add(exerciseName);

        Container setsContainer = new Container(BoxLayout.y());
        TableLayout layout = new TableLayout(1, 3);
        Container setContainer = new Container(layout);
        Set set = new Set(0, 0.0);
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
        testExerciseContainer.add(setsContainer);

        Button addSetButton = new Button("+ Add set");
        addSetButton.setUIID("AchievementButton");
        addSetButton.addActionListener(l -> addSet(setsContainer, ++setCount[0], exercise.getSets()));
        testExerciseContainer.add(addSetButton);
        exercisesContainer.add(testExerciseContainer);
        */
        workoutContainer.add(exercisesContainer);

        Container addExerciseContainer = new Container(BoxLayout.xCenter());
        Button addExerciseButton = new Button("+ Add exercise");
        addExerciseButton.addActionListener(l -> controller.openExerciseSelectFrame(this));
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

    public void addExercise(String name, int id){
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
        addSetButton.addActionListener(l -> addSet(setsContainer, ++setCount[0], exercise.getSets()));
        exerciseContainer.add(addSetButton);

        exercisesContainer.add(exerciseContainer);
        exercisesContainer.revalidate();
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
        create.addActionListener(l -> controller.openCreateFrame());
        navbar.add(create);

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

        finished.addActionListener(l -> {
            controller.addWorkoutInfo(tempName.getText(), controller.getLoggedInEmail(), tempDescription.getText(), tag1.getText(), tag2.getText(), tag3.getText(), exerciseInfo);
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
    //TODO: Remove or replace
    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }

    @Override
    public void inform(int id){
        controller.addLogWorkout(controller.getLoggedInEmail(), id, getDate(), null);
        controller.openMainFrame();
        controller.newCreateFrame();

    }
}
