package com.example.trainingapp.View;

import HelperClasses.Achievement;
import HelperClasses.AchievementsInfo;
import HelperClasses.CompletedAchievement;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.table.TableLayout;
import com.example.trainingapp.Controller.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.codename1.ui.layouts.BorderLayout.*;

/**
    @author Linus Regander, Daniel Olsson
 */

public class AchievementFrame {
    private Button achievement;
    private Button create;
    private Button home;
    private Button program;
    private Button settings;
    private Container container;
    private Container mainContainer;
    private Container topbar;
    private Controller controller;
    private Form form;
    private SpanLabel descriptionText;
    private Label lAchievement;
    private SpanLabel statusText;
    private ArrayList<AchievementsInfo> allAchievementsList;
    private ArrayList<CompletedAchievement> completedAchievementsList;

    public AchievementFrame(Controller controller, ArrayList<AchievementsInfo> allAchievementsList, ArrayList<CompletedAchievement> completedAchievementsList) {
        this.controller = controller;
        this.allAchievementsList = allAchievementsList;
        this.completedAchievementsList = completedAchievementsList;
        achievementForm();
    }

    public void achievementForm() {
        form = new Form(null, new BorderLayout());
        topbar();
        achievementFrame();
        navbar();
        form.show();
    }

    public ArrayList<Achievement> setupAchievements(){
        ArrayList<Achievement> achievements = new ArrayList<>();
        ArrayList<Integer> completedAchievementID = new ArrayList<>();
        if(!completedAchievementsList.isEmpty()) {
            for (CompletedAchievement completedAchievement : completedAchievementsList) {
                completedAchievementID.add(completedAchievement.getAchievementId());
            }
        }
        for(AchievementsInfo tempAchieveInfo : allAchievementsList) {
            Achievement tempAchieve = new Achievement(tempAchieveInfo.getAchievementId(), tempAchieveInfo.getName(), tempAchieveInfo.getDescription(), false);
            if(completedAchievementID.contains(tempAchieveInfo.getAchievementId())) {
                tempAchieve.setCompleted(true);
            }
            achievements.add(tempAchieve);

        }
        return achievements;
    }

    public void topbar() {
        topbar = new Container(BoxLayout.xCenter());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        Button icon = new Button();
        icon.setUIID("Button2");
        icon.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_CIRCLE, icon.getUnselectedStyle()));
        icon.addActionListener(l -> controller.openProfileFrame());
        topbar.add(icon);

        form.add(NORTH, topbar);
    }

    public void achievementFrame() {
        mainContainer = new Container(BoxLayout.y());

        Container a = new Container(BoxLayout.xCenter());
        mainContainer.add(a);

        lAchievement = new Label("Achievements");
        a.add(lAchievement);
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setValignByRow(true);

        Container b = new Container(flowLayout);
        b.setScrollableY(true);
        ArrayList<Achievement> achievements = setupAchievements();
        for(int i = 0; i < achievements.size(); i++){
            Button achievementButton = new Button();
            if(achievements.get(i).isCompleted()){
                achievementButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_RATE, achievementButton.getUnselectedStyle()));
                achievementButton.setUIID("CAchievementButton");
            }
            else{
                achievementButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, achievementButton.getUnselectedStyle()));
                achievementButton.setUIID("AchievementButton");
            }
            int finalI = i;
            achievementButton.addActionListener(l -> {
                        descriptionText.setText("Achievement " + achievements.get(finalI).getDescription());
                        statusText.setText("Status for " + achievements.get(finalI).getStatus());
                        form.revalidate();
                    });
            b.add(achievementButton);
        }
        mainContainer.add(b);

        Container description = new Container(BoxLayout.y());
        mainContainer.add(description);

        Container c = new Container(BoxLayout.x());
        description.add(c);

        Label lDescription = new Label("Description: ");
        c.add(lDescription);

        descriptionText = new SpanLabel("");
        c.add(descriptionText);

        Container d = new Container(BoxLayout.x());
        description.add(d);

        Label status = new Label("Status: ");
        d.add(status);

        statusText = new SpanLabel("");
        d.add(statusText);

        form.add(CENTER, mainContainer);
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
        container.add(create);
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
    public Form getForm(){
        return form;
    }
}
