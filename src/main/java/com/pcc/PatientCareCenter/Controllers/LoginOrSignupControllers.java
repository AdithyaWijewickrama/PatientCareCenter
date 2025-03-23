package com.pcc.PatientCareCenter.Controllers;

import com.pcc.PatientCareCenter.Controllers.LoginOrSignup.*;

public class LoginOrSignupControllers {
    public static final VerifyEmailController VERIFY_EMAIL_CONTROLLER = new VerifyEmailController();
    public static final ForgotPasswordController FORGOT_PASSWORD_CONTROLLER = new ForgotPasswordController();
    public static final LoginController LOGIN_CONTROLLER = new LoginController();
    public static final SignupController SIGNUP_CONTROLLER = new SignupController();
    public static final DoctorDetailsController DOCTOR_DETAILS_CONTROLLER = new DoctorDetailsController();

    public static VerifyEmailController getVerifyEmailController() {
        return VERIFY_EMAIL_CONTROLLER;
    }

    public static ForgotPasswordController getForgotPasswordController() {
        return FORGOT_PASSWORD_CONTROLLER;
    }

    public static LoginController getLoginController() {
        return LOGIN_CONTROLLER;
    }

    public static SignupController getSignupController() {
        return SIGNUP_CONTROLLER;
    }

    public static DoctorDetailsController getDoctorDetailsController() {
        return DOCTOR_DETAILS_CONTROLLER;
    }


}
