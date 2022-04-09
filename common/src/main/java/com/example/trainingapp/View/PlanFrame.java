package com.example.trainingapp.View;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.example.trainingapp.Controller.Controller;

public class PlanFrame {
    private Controller controller;
    private Form planForm;

    public PlanFrame(Controller controller) {
        this.controller = controller;
        planForm();
    }

    public void planForm() {
        planForm = new Form(null, BoxLayout.y());
        pForm();
        planForm.show();
    }

    public void pForm() {
        planForm.setUIID("PlanForm");
        planForm.add(new Label("Test"));
    }
}
