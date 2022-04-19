package View;

import Controller.Controller;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import static com.codename1.ui.layouts.BorderLayout.NORTH;
import static com.codename1.ui.layouts.BorderLayout.SOUTH;

public class ProgramFrame {
    private Controller controller;
    private Form form;
    private Container container;
    private Container topbar;
    private Button home;
    private Button achievement;
    private Button create;
    private Button program;
    private Button settings;

    public ProgramFrame (Controller controller) {
        this.controller = controller;
        programForm();
    }

    public void programForm() {
        form = new Form(null, new BorderLayout());
        form.setUIID("ProgramForm");
        topbar();
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


    public void navbar() {
        container = new Container(BoxLayout.xCenter());
        container.setUIID("Navbar");

        home = new Button();
        home.setIcon(FontImage.createMaterial(FontImage.MATERIAL_HOME, home.getUnselectedStyle()));
        home.addActionListener(l -> controller.openMainFrame());
        container.add(home);

        achievement = new Button();
        achievement.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievement.getUnselectedStyle()));
        achievement.addActionListener(l -> controller.openAchievementFrame());
        container.add(achievement);

        create = new Button();
        create.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD, create.getUnselectedStyle()));
        create.addActionListener(l -> controller.openCreateFrame());
        container.add(create);

        program = new Button ();
        program.setIcon(FontImage.createMaterial(FontImage.MATERIAL_LEADERBOARD, program.getUnselectedStyle()));
        program.addActionListener(l -> controller.openProgramFrame());
        container.add(program);

        settings = new Button ();
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, settings.getUnselectedStyle()));
        settings.addActionListener(l -> controller.openSettingsFrame());
        container.add(settings);

        form.add(SOUTH, container);

    }
}
