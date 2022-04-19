package View;

import Controller.Controller;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;

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
    private TextField eTextField;
    private TextField pTextField;
    private TextField uTextField;
    private Form loginForm;

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

        uLabel = new Label("Username/Email:");
        loginContainer.add(uLabel);
        uTextField = new TextField();
        loginContainer.add(uTextField);

        pLabel = new Label("Password:");
        loginContainer.add(pLabel);
        pTextField = new TextField();
        loginContainer.add(pTextField);

        buttonContainer = new Container(BoxLayout.xCenter());
        loginForm.add(buttonContainer);

        lButton = new Button("Login");
        lButton.setUIID("LoginButton");
        lButton.addActionListener(l -> controller.loginVerification());
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
}
