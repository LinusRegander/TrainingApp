package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class AchievementFrame {
    private Controller controller;
    private JFrame frame;
    private Container container;
    private Container topbar;
    private JButton home;
    private JButton achievement;
    private JButton create;
    private JButton program;
    private JButton settings;

    public AchievementFrame(Controller controller) {
        this.controller = controller;
        frame = new JFrame();
    }

    public void topbar() {
        JPanel topbar = new JPanel();
        topbar.setLayout(new BoxLayout(topbar, BoxLayout.X_AXIS));

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

        frame.add(topbar);
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
}
