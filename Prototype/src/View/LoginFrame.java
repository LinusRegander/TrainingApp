package View;

import Controller.Controller;
import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.WindowEvent;

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
        loginForm.setSize(300,600);
        userForm();
        loginForm.setVisible(true);
    }
    public void exit(){
        loginForm.dispatchEvent(new WindowEvent(loginForm, WindowEvent.WINDOW_CLOSING));
    }
    public void userForm() {
        loginContainer = new JPanel();
        loginContainer.setBackground(Color.GREEN);


        testLabel = new JLabel();
        testLabel.setText("FitHub");
        testLabel.setLocation(450 , 0);
        loginContainer.add(testLabel);

        uLabel = new JLabel("Username/Email:");
        loginContainer.add(uLabel);
        uTextField = new JTextField();
        uTextField.setPreferredSize(new Dimension(50,20));
        loginContainer.add(uTextField);

        pLabel = new JLabel("Password:");
        loginContainer.add(pLabel);
        pTextField = new JTextField();
        pTextField.setPreferredSize(new Dimension(50,20));
        loginContainer.add(pTextField);


        //buttonContainer = new JPanel();
        //buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.LINE_AXIS));


        lButton = new JButton("Login");
        lButton.addActionListener(l -> controller.login(uTextField.getText(), pTextField.getText()));
        loginContainer.add(lButton);

        rButton = new JButton("Register");
        rButton.addActionListener(l -> controller.openRegFrame());
        rButton.addActionListener(l -> loginForm.dispatchEvent(new WindowEvent(loginForm, WindowEvent.WINDOW_CLOSING)));
        loginContainer.add(rButton);

        //loginForm.add(buttonContainer);
        loginForm.add(loginContainer);
    }

    public String getFieldContent() {
        return uTextField.getText();
    }

    public JFrame getLoginForm() {
        return loginForm;
    }
}
