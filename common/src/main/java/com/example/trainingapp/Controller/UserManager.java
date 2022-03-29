package com.example.trainingapp.Controller;
import com.example.trainingapp.Model.User;

import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> userList;
    private User currUser;

    public UserManager(User currUser) {
        this.currUser = currUser;
        userList = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if(!userList.contains(user)) {
            userList.add(user);
            return true;
        }
        else {
            return false;
        }
    }

    public User getCurrUser () {
        return currUser;
    }

    public void setCurrUser (User currUser) {
        this.currUser = currUser;
    }

    public boolean existingUser(String fieldContent) {
        for(User user : userList) {
            if(fieldContent.equals(user.getUserName())) {
                setCurrUser(user);
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> readUserList() throws FileNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/userHistory.dat")))) {
            userList = (ArrayList<User>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
        }
        return userList;
    }

    public void writeUserList() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("files/userHistory.dat")))) {
            oos.writeObject(userList);
            oos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
