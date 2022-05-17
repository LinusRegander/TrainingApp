package com.example.trainingapp.View;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

import javax.swing.*;

import static com.codename1.ui.layouts.BorderLayout.CENTER;
import static com.codename1.ui.layouts.BorderLayout.NORTH;

/**
 @author Linus Regander, Daniel Olsson, William Dock, Yun-Bo Chow
 */

public class RegisterFrame implements ActionListener {
    private Button rButton;
    private Container registerContainer;
    private Container topbar;
    private Controller controller;
    private Form registerForm;
    private Label eLabel;
    private Label pLabel;
    private Label uLabel;
    private TextField eTextField;
    private TextField pTextField;
    private TextField uTextField;
    private SpanLabel error;

    public RegisterFrame(Controller controller) {
        this.controller = controller;
        registerFrame();
    }

    public void registerFrame() {
        registerForm = new Form(null, new BorderLayout());
        registerForm.setUIID("RegisterForm");
        topbar();
        registerForm();
        registerForm.show();
    }

    public void topbar() {
        topbar = new Container(BoxLayout.xCenter());
        topbar.setUIID("Topbar");

        Container top = new Container(BoxLayout.xCenter());
        topbar.add(top);

        Label title = new Label("FitHub");
        top.add(title);

        Button back = new Button();
        back.setIcon(FontImage.createMaterial(FontImage.MATERIAL_CLOSE, back.getUnselectedStyle()));
        back.addActionListener(l -> controller.openLoginFrame());
        top.add(back);

        registerForm.add(NORTH, topbar);
    }

    public void registerForm() {
        registerContainer = new Container(BoxLayout.y());
        registerForm.add(CENTER, registerContainer);

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

        error = new SpanLabel("");
        registerContainer.add(error);
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

    public SpanLabel getError(){
        return error;
    }

    public void showSuccess(){
        Dialog.show("Success", "Account was registered successfully", "OK", "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rButton) {
            controller.register(uTextField.getText(), eTextField.getText(), pTextField.getText());
            registerForm.revalidate();
        }
    }
}
