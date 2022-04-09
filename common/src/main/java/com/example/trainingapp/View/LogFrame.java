package com.example.trainingapp.View;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

public class LogFrame {
    private Controller controller;
    private Form logForm;

    public LogFrame(Controller controller) {
        this.controller = controller;
        logForm();
    }

    public void logForm() {
        logForm = new Form(null, BoxLayout.y());
        lForm();
        logForm.show();
    }

    public void lForm() {
        logForm.setUIID("LogForm");
        logForm.add(new Label("Test"));
    }
}
