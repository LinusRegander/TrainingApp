package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;


public class MainFrame {
    private Controller controller;
    private JButton home;
    private JButton achievement;
    private JButton create;
    private JButton program;
    private JButton settings;
    private JButton trainingLog;
    private JLabel sets;
    private JLabel something;
    private JLabel statistics;
    private JLabel totalWeight;
    private JLabel workouts;
    private JPanel panel;
    private JPanel mainPanel;
    private JPanel navbar;
    private JPanel topbar;

    public MainFrame(Controller controller) {
        this.controller = controller;
        mainFrame();
    }

    public void mainFrame() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        topbar();
        homeArea();
        navbar();
    }

    public void topbar() {
        topbar = new JPanel();
        topbar.setLayout(new BoxLayout(topbar, BoxLayout.X_AXIS));

        Label title = new Label("FitHub");
        topbar.add(title);

        panel.add(topbar);
    }

    public void homeArea() {
        mainPanel = new JPanel();
        panel.add(mainPanel);

        JPanel a = new JPanel();
        mainPanel.add(a);

        statistics = new JLabel("Statistics from last 7 days:");
        a.add(statistics);

        JPanel b = new JPanel();
        mainPanel.add(b);

        workouts = new JLabel("Workouts");
        b.add(workouts);

        sets = new JLabel("Sets");
        b.add(sets);

        JPanel c = new JPanel();
        mainPanel.add(c);

        totalWeight = new JLabel("Total Weight");
        c.add(totalWeight);

        something = new JLabel("Something");
        c.add(something);

        trainingLog = new JButton("Training Log >");
        trainingLog.addActionListener(l -> controller.openProgramFrame());
        mainPanel.add(trainingLog);
    }

    public void navbar() {
        navbar = new JPanel();
        navbar.setLayout(new BoxLayout(navbar, BoxLayout.Y_AXIS));

        home = new JButton();
        home.addActionListener(l -> controller.openMainFrame());
        navbar.add(home);

        achievement = new JButton();
        achievement.addActionListener(l -> controller.openAchievementFrame());
        navbar.add(achievement);

        create = new JButton();
        create.addActionListener(l -> controller.openCreateFrame());
        navbar.add(create);

        program = new JButton ();
        program.addActionListener(l -> controller.openProgramFrame());
        navbar.add(program);

        settings = new JButton ();
        settings.addActionListener(l -> controller.openSettingsFrame());
        navbar.add(settings);

        panel.add(navbar);
    }
}
