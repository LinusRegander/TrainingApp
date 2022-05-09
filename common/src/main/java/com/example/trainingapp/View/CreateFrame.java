package com.example.trainingapp.View;

import HelperClasses.ExerciseInfo;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.example.trainingapp.Controller.Controller;

import java.util.ArrayList;
import java.util.Date;

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

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new Form(null, new BoxLayout(BoxLayout.Y_AXIS));
        form.setScrollableY(true);
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
        accept.addActionListener(l -> getWorkoutInfo());
        top.add(accept);

        Container bottom = new Container(BoxLayout.xCenter());
        topbar.add(bottom);

        Label time = new Label("PlaceHolder Time");
        bottom.add(time);

        form.add(topbar);
    }

    public void workoutContainer() {
        workoutContainer = new Container(BoxLayout.y());
        workoutContainer.setScrollableY(true);
        workoutContainer.setUIID("WorkoutContainer");

        a = new Container(BoxLayout.y());
        containers.add(a);
        a.setUIID("a");
        workoutContainer.add(a);

        Label name = new Label("ID (Number): ");
        a.add(name);

        workout = new TextField();
        a.add(workout);

        temp = new Container(BoxLayout.y());
        a.add(temp);

        Label set = new Label("Sets:");
        temp.add(set);

        textArea = new TextArea();
        temp.add(textArea);

        Label reps = new Label("Reps:");
        temp.add(reps);

        textArea2 = new TextArea();
        temp.add(textArea2);

        Label lblWeight = new Label("Weight:");
        temp.add(lblWeight);

        textAreaWeight = new TextArea();
        temp.add(textAreaWeight);

        Container b = new Container(BoxLayout.yCenter());
        containers.add(b);
        b.setUIID("b");
        a.add(b);

        addSet = new Button("+ Add Set");
        addSet.addActionListener(l -> temp.add(new TextArea()));
        b.add(addSet);

        Container c = new Container(BoxLayout.y());
        containers.add(c);
        a.add(c);

        Label weight = new Label("Total Weight");
        c.add(weight);

        totalWeight = new Label("0");
        c.add(totalWeight);

        Container d = new Container(BoxLayout.y());
        containers.add(d);
        a.add(d);

        Label average = new Label("Average Weight");
        d.add(average);

        averageWeight = new Label("0");
        d.add(averageWeight);

        Container e = new Container(BoxLayout.yCenter());
        containers.add(e);
        workoutContainer.add(e);

        addExercise = new Button("+ Add Exercise");
        try {
            addExercise.addActionListener(l -> controller.addExercise(Integer.parseInt(workout.getText()), exerciseID++, Integer.parseInt(textArea.getText()), Integer.parseInt(textArea2.getText()), Integer.parseInt(textArea2.getText())));
            addExercise.addActionListener(l -> addedExercise());
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
        d.add(addExercise);

        form.add(workoutContainer);
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

        form.add(navbar);
    }

    public void addedExercise() {
        Container nExerciseContainer = new Container(BoxLayout.y());
        workoutContainer.add(nExerciseContainer);

        Label exercise = new Label("Exercise " + workout.getText() + " Sets: " + textArea.getText() + " Reps: " + textArea2.getText() + " Weight: " + textAreaWeight.getText());
        //TODO: Fixa denna: exerciseInfo.add(exercise.getText());
        nExerciseContainer.add(exercise);

        calculateTotal();
        calculateAverage();

        workout.setText("");

        textArea.setText("");

        textArea2.setText("");

        textAreaWeight.setText("");
    }

    public void calculateTotal() {
        double temp = 0;
        double total = Double.parseDouble(textAreaWeight.getText());

        ArrayList<Double> totalList = new ArrayList<>();
        totalList.add(total);

        for (int i = 0; i < totalList.size(); i++) {
            temp += totalList.get(i);
        }

        totalWeight.setText(String.valueOf(temp));
    }

    public void calculateAverage() {
        double weight = Double.parseDouble(textAreaWeight.getText());

        ArrayList<Double> weightList = new ArrayList<>();
        weightList.add(weight);

        float sum = 0;

        for (int i = 0; i < weightList.size(); i++) {
            sum += weightList.get(i);
        }

        float average = (sum / weightList.size());

        averageWeight.setText(String.valueOf(average));
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
        finished.addActionListener(l -> controller.openProgramFrame());
        tempA.add(finished);

        tempForm.add(CENTER, tempA);
        tempForm.show();
    }
}
