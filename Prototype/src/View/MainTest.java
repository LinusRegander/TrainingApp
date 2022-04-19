package View;

import Controller.Controller;

public class MainTest {
    public static void main(String[] args) {
        Controller controller = new Controller();
        LoginFrame loginFrame = new LoginFrame(controller);
        controller.setLoginFrame(loginFrame);
    }
}
