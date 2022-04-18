package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

public class RegisterFrame {
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
        rButton.addActionListener((e) -> {
                    Form loginForm = controller.getLoginForm();
                        loginForm.setToolbar(new Toolbar());
                        loginForm.setBackCommand(new Command("Back") {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            registerForm.showBack();
                        }
                    });
                    loginForm.show();
                });
        rButton.addActionListener(l -> {controller.registration();});
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
}
