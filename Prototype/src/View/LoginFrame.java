package View;

import Controller.Controller;
import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;

public class LoginFrame {
    private JButton lButton;
    private JButton rButton;
    private JPanel buttonContainer;
    private JPanel loginContainer;
    private Controller controller;
    private JLabel pLabel;
    private JLabel testLabel;
    private JLabel uLabel;
    private JTextField eTextField;
    private JTextField pTextField;
    private JTextField uTextField;
    private JFrame loginForm;

    public LoginFrame(Controller controller) {
        this.controller = controller;
        loginFrame();
    }

    public void loginFrame() {
        loginForm = new JFrame();
        userForm();
        loginForm.setVisible(true);
    }

    public void userForm() {
        loginContainer = new JPanel();
        loginContainer.setLayout(new BoxLayout(loginContainer, BoxLayout.Y_AXIS));
        loginForm.add(loginContainer);

        testLabel = new JLabel();
        testLabel.setText("FitHub");
        testLabel.setLocation(450 , 0);
        loginContainer.add(testLabel);

        uLabel = new JLabel("Username/Email:");
        loginContainer.add(uLabel);
        uTextField = new JTextField();
        loginContainer.add(uTextField);

        pLabel = new JLabel("Password:");
        loginContainer.add(pLabel);
        pTextField = new JTextField();
        loginContainer.add(pTextField);

        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.LINE_AXIS));
        loginForm.add(buttonContainer);

        lButton = new JButton("Login");
        lButton.addActionListener(l -> controller.login(uTextField.getText(), pTextField.getText()));
        buttonContainer.add(lButton);

        rButton = new JButton("Register");
        rButton.addActionListener(l -> controller.openRegFrame());
        buttonContainer.add(rButton);
    }

    public String getFieldContent() {
        return uTextField.getText();
    }

    public JFrame getLoginForm() {
        return loginForm;
    }
}
