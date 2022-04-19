package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class CreateFrame {
    /*
    private JButton achievement;
    private JButton addExercise;
    private JButton addSet;
    private JButton create;
    private JButton home;
    private JButton program;
    private JButton settings;
    private JPanel navbar;
    private JPanel topbar;
    private JPanel workoutContainer;
    private Controller controller;
    private JFrame form;
    private JLabel averageWeight;
    private JLabel repAmount;
    private JLabel setAmount;
    private JLabel totalWeight;
    private JLabel weightAmount;
    private String textFromArea;
    private JTextArea textArea;
    private JTextField workout;

    public CreateFrame(Controller controller) {
        this.controller = controller;
        createForm();
    }

    public void createForm() {
        form = new JFrame();
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

        textArea = new TextArea();
        a.add(textArea);

        Container b = new Container(BoxLayout.yCenter());
        b.setUIID("b");
        a.add(b);

        addSet = new Button("+ Add Set");
        b.add(addSet);

        Container c = new Container(BoxLayout.xCenter());
        c.setUIID("c");
        a.add(c);

        totalWeight = new Label("Total Weight");
        c.add(totalWeight);

        averageWeight = new Label("Average Weight");
        c.add(averageWeight);

        Container d = new Container(BoxLayout.yCenter());
        d.setUIID("d");
        workoutContainer.add(d);

        addExercise = new Button("+ Add Exercise");
        d.add(addExercise);

        Container e = new Container(BoxLayout.xCenter());
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

    public void navbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        home = new JButton();
        home.addActionListener(l -> controller.openMainFrame());
        panel.add(home);

        achievement = new JButton();
        achievement.addActionListener(l -> controller.openAchievementFrame());
        panel.add(achievement);

        create = new JButton();
        create.addActionListener(l -> controller.openCreateFrame());
        panel.add(create);

        program = new JButton ();
        program.addActionListener(l -> controller.openProgramFrame());
        panel.add(program);

        settings = new JButton ();
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        frame.add(panel);
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public TextArea getTextArea() {
        return textArea;
    }

     */
}
