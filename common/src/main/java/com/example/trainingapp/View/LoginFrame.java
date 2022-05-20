package com.example.trainingapp.View;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.example.trainingapp.Controller.*;
import jdk.tools.jmod.Main;

import java.awt.*;

/**
 * The LoginFrame class for the Login page.
 @author Linus Regander, William Dock
 */

public class LoginFrame{
    private Button lButton;
    private Button rButton;
    private Container buttonContainer;
    private Container loginContainer;
    private Controller controller;
    private Label pLabel;
    private Label testLabel;
    private Label uLabel;
    private Label incorrectLabel;
    private TextField pTextField;
    private TextField uTextField;
    private Form loginForm;
    Style iL;

    /**
     * Constructor
     */
    public LoginFrame(Controller controller){
        this.controller = controller;
        loginFrame();
    }

    /**
     * Creates the form
     * Initializes objects and builds the page
     */
    public void loginFrame(){
        loginForm = new Form(null, BoxLayout.y());;
        userForm();
        loginForm.show();
    }

    /**
     * Creates the central page
     */
    public void userForm(){
        loginContainer = new Container(BoxLayout.y());
        loginForm.add(loginContainer);

        testLabel = new Label();
        testLabel.setText("FitHub");
        Style tl = testLabel.getStyle();
        tl.setMarginLeft(450);
        tl.setFgColor(10);
        loginContainer.add(testLabel);

        uLabel = new Label("Email:");
        loginContainer.add(uLabel);
        uTextField = new TextField();
        loginContainer.add(uTextField);

        pLabel = new Label("Password:");
        loginContainer.add(pLabel);
        pTextField = new TextField();
        loginContainer.add(pTextField);

        incorrectLabel = new Label("The username or password isn't correct!");
        incorrectLabel.setUIID("ErrorLabel");
        iL = incorrectLabel.getStyle();
        iL.setFgColor(ColorUtil.WHITE);
        loginContainer.add(incorrectLabel);

        buttonContainer = new Container(BoxLayout.xCenter());
        loginForm.add(buttonContainer);

        lButton = new Button("Login");
        lButton.addActionListener(l -> {
            if(!uTextField.getText().isEmpty() && !pTextField.getText().isEmpty()){
                controller.login(uTextField.getText(), pTextField.getText());
            } else{
                System.out.println("test");
                incorrectLabel.setText("Username and password \n can't be empty!");
                iL.setFgColor(16711680);
            }
        });
        buttonContainer.add(lButton);

        rButton = new Button("Register");
        rButton.addActionListener(l -> controller.openRegFrame());
        buttonContainer.add(rButton);
    }

    /**
     * For failed login
     */
    //TODO: Fixa till / Linus 2022-05-18
    public void failedLogin(){
        incorrectLabel.setText("The username or password \n isn't correct!");
        iL.setFgColor(16711680);
        loginForm.refreshTheme();
    }
}
