package com.ppcc.PatientCareCenter.Views;

public class CommonViewFactory {
    private final LoginOrSignupViewFactory loginOrSignupViewFactory;
    private final AdminViewFactory adminViewFactory;
    private final ClientViewFactory clientViewFactory;

    public CommonViewFactory(){
        loginOrSignupViewFactory=new LoginOrSignupViewFactory();
        adminViewFactory=new AdminViewFactory();
        clientViewFactory=new ClientViewFactory();
    }

    public AdminViewFactory getAdminViewFactory() {
        return adminViewFactory;
    }

    public ClientViewFactory getClientViewFactory() {
        return clientViewFactory;
    }

    public LoginOrSignupViewFactory getLoginOrSignupViewFactory() {
        return loginOrSignupViewFactory;
    }
}
