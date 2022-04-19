package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;


public class ProgramFrame {
    private Controller controller;
    private JFrame frame;
    private JPanel panel;
    private JPanel topbar;
    private JButton home;
    private JButton achievement;
    private JButton create;
    private JButton program;
    private JButton settings;
    private JPanel programPanel;

    public ProgramFrame (Controller controller) {
        this.controller = controller;
        frame = new JFrame();
        frame.setBounds(0,0,300,600);
        frame.setLayout(new BoxLayout(frame, BoxLayout.PAGE_AXIS));
        frame.setVisible(true);
        topbar();
        navbar();
        programPanel();
    }

    public void topbar() {
        topbar = new JPanel();
        topbar.setLayout(new BoxLayout(topbar, BoxLayout.PAGE_AXIS));

        Label title = new Label("FitHub");
        topbar.add(title);

        frame.add(topbar);
    }

    public void programPanel(){
        programPanel = new JPanel();
        programPanel.setLayout(new BoxLayout(programPanel, BoxLayout.PAGE_AXIS));

        JRadioButton workout = new JRadioButton("Workout");
        JRadioButton program = new JRadioButton("Program");
        workout.setSelected(true);
        ButtonGroup workoutProgram = new ButtonGroup();

        workoutProgram.add(workout);
        workoutProgram.add(program);

        JRadioButton community = new JRadioButton("Community");
        JRadioButton mine = new JRadioButton("Mine");
        mine.setSelected(true);
        ButtonGroup communityMine = new ButtonGroup();

        communityMine.add(community);
        communityMine.add(mine);

        String[] data = {"Workout 1, Workout 2, Workout 3, Workout 4, Workout 5"};
        JList<String> resultList= new JList<>(data);

        programPanel.add(workout);
        programPanel.add(program);
        programPanel.add(community);
        programPanel.add(mine);
        programPanel.add(resultList);

    }


    public void navbar() {
        panel = new JPanel();
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
        panel.add(settings);

        frame.add(panel);
    }
}
