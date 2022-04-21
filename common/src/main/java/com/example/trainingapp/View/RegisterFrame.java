package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import javax.swing.*;

public class RegisterFrame implements ActionListener {
    private Button rButton;
    private Container registerContainer;
    private Controller controller;
    private Form registerForm;
    private Label eLabel;
    private Label pLabel;
    private Label uLabel;
    private TextField eTextField;
    private TextField pTextField;
    private TextField uTextField;

    public RegisterFrame(Controller controller) {
        this.controller = controller;
        registerFrame();
    }

    public void registerFrame() {
        registerForm = new Form(null, BoxLayout.y());
        registerForm.setUIID("RegisterForm");
        registerForm();
        registerForm.show();
    }

    public void registerForm() {
        registerContainer = new Container(BoxLayout.y());
        registerForm.add(registerContainer);

        uLabel = new Label("Choose Username:");
        registerContainer .add(uLabel);
        uTextField = new TextField();
        registerContainer .add(uTextField);

        pLabel = new Label("Choose Password:");
        registerContainer .add(pLabel);
        pTextField = new TextField();
        registerContainer .add(pTextField);

        eLabel = new Label("Choose Email:");
        registerContainer .add(eLabel);
        eTextField = new TextField();
        registerContainer .add(eTextField);

        rButton = new Button("Register");
        rButton.setUIID("RegisterB");
        rButton.addActionListener(this);
        registerContainer .add(rButton);
    }

    public String getUserName() {
        return uTextField.getText();
    }

    public String getPassword() {
        return pTextField.getText();
    }

    public String getEmail() {
        return eTextField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rButton) {
            //controller.register(uTextField.getText(), eTextField.getText(), pTextField.getText());
            controller.openLoginFrame();
        }
    }
}
