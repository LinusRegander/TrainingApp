package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AchievementFrame {
    private Controller controller;
    private JPanel panel;
    private JPanel topbar;
    private JButton home;
    private JButton achievement;
    private JButton create;
    private JButton program;
    private JButton settings;

    public AchievementFrame(Controller controller) {
        this.controller = controller;
        achievementFrame();
    }

    public void achievementFrame() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        topbar();
        navbar();
    }

    public void topbar() {
        topbar = new JPanel();
        topbar.setLayout(new BoxLayout(topbar, BoxLayout.X_AXIS));

        Label title = new Label("FitHub");
        topbar.add(title);

        panel.add(topbar);
    }

    public void navbar() {
        JPanel navbar = new JPanel();
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
