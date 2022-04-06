package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.Button;
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
    private Controller controller;
    private TextField uTextField;
    private TextField pTextField;
    private TextField eTextField;
    private Form loginForm;
    private Label testLabel;
    private Label uLabel;
    private Label pLabel;
    private Button lButton;
    private Button rButton;

    public LoginFrame(Controller controller) {
        this.controller = controller;
        loginFrame();
    }

    public void loginFrame() {
        loginForm = new Form(null, BoxLayout.y());
        userForm();
        loginForm.show();
    }

    public void userForm() {
        testLabel = new Label();
        testLabel.setText("FitHub");
        Style tl = testLabel.getStyle();
        tl.setMarginLeft(450);
        tl.setFgColor(10);
        loginForm.add(testLabel);

        uLabel = new Label("Username/Email:");
        loginForm.add(uLabel);
        uTextField = new TextField();
        loginForm.add(uTextField);

        pLabel = new Label("Password:");
        loginForm.add(pLabel);
        pTextField = new TextField();
        loginForm.add(pTextField);

        lButton = new Button("Login");
        lButton.addActionListener(l -> controller.loginVerification());
        loginForm.add(lButton);

        rButton = new Button("Register");
        rButton.addActionListener(l -> controller.openRegFrame());
        loginForm.add(rButton);
    }

    public String getFieldContent() {
        return uTextField.getText();
    }

    public Form getLoginForm() {
        return loginForm;
    }
}
