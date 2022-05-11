package com.example.trainingapp.View;

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

public class LoginFrame {
    private Button lButton;
    private Button rButton;
    private Container buttonContainer;
    private Container loginContainer;
    private Controller controller;
    private Label pLabel;
    private Label testLabel;
    private Label uLabel;
    private Label incorrectLabel;
    private TextField eTextField;
    private TextField pTextField;
    private TextField uTextField;
    private Form loginForm;

    Style iL;

    public LoginFrame(Controller controller) {
        this.controller = controller;
        loginFrame();
    }

    public void loginFrame() {
        loginForm = new Form(null, BoxLayout.y());
        loginForm.setUIID("LoginForm");
        userForm();
        loginForm.show();
    }

    public void userForm() {
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
        iL = incorrectLabel.getStyle();
        iL.setFgColor(16777215);
        loginContainer.add(incorrectLabel);

        buttonContainer = new Container(BoxLayout.xCenter());
        loginForm.add(buttonContainer);

        lButton = new Button("Login");
        lButton.setUIID("LoginButton");
        lButton.addActionListener(l -> controller.login(uTextField.getText(), pTextField.getText()));
        buttonContainer.add(lButton);

        rButton = new Button("Register");
        rButton.setUIID("RegisterButton");
        rButton.addActionListener(l -> controller.openRegFrame());
        buttonContainer.add(rButton);
    }

    public String getFieldContent() {
        return uTextField.getText();
    }

    public Form getLoginForm() {
        return loginForm;
    }

    public void failedLogin(){
        System.out.println("hallå");
        iL.setFgColor(16711680);
        loginForm.refreshTheme();
    }
}
