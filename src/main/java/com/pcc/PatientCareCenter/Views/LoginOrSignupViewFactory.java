package com.pcc.PatientCareCenter.Views;

import com.pcc.PatientCareCenter.Views.Stages.LoginOrSignupStages;

public class LoginOrSignupViewFactory {
    private LoginOrSignupStages loginOrSignup;

    public LoginOrSignupViewFactory() {
        loginOrSignup = new LoginOrSignupStages();
    }

    public synchronized void showLoginOrSignupWindow() {
        loginOrSignup.setCurrentPane("Login");
        loginOrSignup.getLoginOrSignupWindow().show();
    }

    public void showLoginPane() {
        loginOrSignup.setCurrentPane("Login");
    }
    public void showDoctorDetails() {
        loginOrSignup.setCurrentPane("DoctorDetails");
    }

    public void showSignupPane() {
        loginOrSignup.setCurrentPane("Signup");
    }

    public void showForgotPasswordPane() {
        loginOrSignup.setCurrentPane("ForgotPassword");
    }

    public void showVerifyEmailPane() {
        loginOrSignup.setCurrentPane("VerifyEmail");
    }

    public LoginOrSignupStages getLoginOrSignup() {
        return loginOrSignup;
    }
}
