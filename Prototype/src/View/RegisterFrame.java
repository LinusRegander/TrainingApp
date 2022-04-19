package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame implements ActionListener {
    private JButton rButton;
    private JPanel registerContainer;
    private Controller controller;
    private JFrame registerForm;
    private JLabel eLabel;
    private JLabel pLabel;
    private JLabel uLabel;
    private JTextField eTextField;
    private JTextField pTextField;
    private JTextField uTextField;

    public RegisterFrame(Controller controller) {
        this.controller = controller;
        registerFrame();
    }

    public void registerFrame() {
        registerForm = new JFrame(); //(null, BoxLayout.y());
        registerForm.setLayout(new BoxLayout(registerForm, BoxLayout.Y_AXIS));

        registerForm();
        registerForm.show();
    }

    public void registerForm() {
        registerContainer = new JPanel(); //(BoxLayout.y());
        registerContainer.setLayout(new BoxLayout(registerContainer, BoxLayout.Y_AXIS));
        registerForm.add(registerContainer);

        uLabel = new JLabel("Choose Username:");
        registerContainer .add(uLabel);
        uTextField = new JTextField();
        registerContainer .add(uTextField);

        pLabel = new JLabel("Choose Password:");
        registerContainer .add(pLabel);
        pTextField = new JTextField();
        registerContainer .add(pTextField);

        eLabel = new JLabel("Choose Email:");
        registerContainer .add(eLabel);
        eTextField = new JTextField();
        registerContainer .add(eTextField);

        rButton = new JButton("Register");
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
            controller.register(uTextField.getText(), eTextField.getText(), pTextField.getText());
            controller.openLoginFrame();
        }
    }
}
