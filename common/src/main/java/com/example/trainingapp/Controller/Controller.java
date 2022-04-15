package com.example.trainingapp.Controller;

import com.codename1.ui.Form;
import com.example.trainingapp.Model.User;
import com.example.trainingapp.View.*;
import jdk.tools.jmod.Main;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.text.Normalizer;


public class Controller {
    private MainFrame mainFrame;
    private RegisterFrame registerFrame;
    private LoginFrame loginFrame;
    private ProgramFrame programFrame;
    private SettingsFrame settingsFrame;
    private CreateFrame createFrame;
    private AchievementFrame achievementFrame;
    private UserManager userManager;
    private User user;

    public Controller() {
        Setup();
    }

    public void Setup() {
        mainFrame = new MainFrame(this);
        //loginFrame = new LoginFrame(this);
        //user = new User();
        //userManager = new UserManager(user);
    }

    public void loginVerification() {
        boolean login = false;

        do {
            if (userManager.existingUser(loginFrame.getFieldContent())) {
                user = userManager.getCurrUser();
                mainFrame = new MainFrame(this);
                login = true;
                openMainFrame();
                System.out.println("Login complete");
            } else {
                System.err.println("Login failed");
            }
        } while (!login);
    }

    public void registration() {
        boolean login = false;

        try {
            do {
                user = new User();
                user.setUserName(registerFrame.getUserName());
                user.setPassword(registerFrame.getPassword());
                user.setEmail(registerFrame.getEmail());
                if (userManager.addUser(user)) {
                    userManager.setCurrUser(user);
                    login = true;
                }
            } while (!login);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Form getMainForm() {
        return mainFrame.getMainForm();
    }

    public void openMainFrame() {
        mainFrame = new MainFrame(this);
    }

    public void openRegFrame() {
        registerFrame = new RegisterFrame(this);
    }

    public void openAchievementFrame() {
        achievementFrame = new AchievementFrame(this);
    }

    public void openCreateFrame() {
        createFrame = new CreateFrame(this);
    }
    public void openProgramFrame() {
        programFrame = new ProgramFrame(this);
    }

    public void openSettingsFrame() {
        settingsFrame = new SettingsFrame(this);
    }

    public Form getLoginForm() {
        return loginFrame.getLoginForm();
    }
}
