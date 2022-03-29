package com.example.trainingapp;

import static com.codename1.ui.CN.*;
import com.codename1.system.Lifecycle;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.io.*;
import com.codename1.ui.plaf.*;
import com.codename1.ui.util.Resources;
import com.example.trainingapp.Controller.Controller;
import com.example.trainingapp.View.LoginFrame;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class Training extends Lifecycle {
    private Controller controller;

    @Override
    public void runApp() {
        controller = new Controller();
        System.out.println("Test");
    }
}
