package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame {
    private JButton home;
    private JButton achievement;
    private JButton create;
    private JButton darkMode;
    private JButton lightMode;
    private JButton profile;
    private JButton program;
    private JButton settings;
    private JPanel container;
    private JPanel settingsContainer;
    private JPanel topbar;
    private Controller controller;
    private JFrame form;

    public SettingsFrame(Controller controller) {
        this.controller = controller;
        settingsForm();
    }

    public void settingsForm() {
        form = new JFrame(); //(SettingsFrame, new BoxLayout(form.set));
        form.setLayout(new BoxLayout(form, BoxLayout.X_AXIS));
        topbar();
        settingsFrame();
        navbar();
        form.show();
    }

    public void topbar() {
        JPanel topbar = new JPanel();
        topbar.setLayout(new BoxLayout(topbar, BoxLayout.X_AXIS));

        JPanel top = new JPanel();   //(BoxLayout.xCenter());
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        form.add(topbar, BorderLayout.NORTH); //NORTH
    }

    public void settingsFrame() {
        settingsContainer = new JPanel(); //(BoxLayout.y());
        settingsContainer.setLayout(new BoxLayout(settingsContainer, BoxLayout.Y_AXIS));

        JPanel a = new JPanel(); //(BoxLayout.y());
        a.setLayout(new BoxLayout(a, BoxLayout.Y_AXIS));


        settingsContainer.add(a);

        profile = new JButton("Profile");

        a.add(profile);

        darkMode = new JButton("Dark Mode");

        a.add(darkMode);

        lightMode = new JButton("Light Mode");

        a.add(lightMode);

        form.add(settingsContainer, BorderLayout.CENTER); //CENTER
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

        form.add(panel);
    }
}
