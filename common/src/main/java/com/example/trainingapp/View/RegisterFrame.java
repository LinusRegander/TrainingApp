package com.example.trainingapp.View;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

public class RegisterFrame {
    private Controller controller;
    private Form registerForm;
    private Label uLabel;
    private TextField uTextField;
    private Label pLabel;
    private TextField pTextField;
    private Label eLabel;
    private TextField eTextField;
    private Button rButton;

    public RegisterFrame(Controller controller) {
        this.controller = controller;
        registerFrame();
    }

    public void registerFrame() {
        registerForm = new Form(null, BoxLayout.y());
        registerForm();
        registerForm.show();
    }

    public void registerForm() {
        uLabel = new Label("Choose Username:");
        registerForm.add(uLabel);
        uTextField = new TextField();
        registerForm.add(uTextField);

        pLabel = new Label("Choose Password:");
        registerForm.add(pLabel);
        pTextField = new TextField();
        registerForm.add(pTextField);

        eLabel = new Label("Choose Email:");
        registerForm.add(eLabel);
        eTextField = new TextField();
        registerForm.add(eTextField);

        rButton = new Button("Register");
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
        registerForm.add(rButton);
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
