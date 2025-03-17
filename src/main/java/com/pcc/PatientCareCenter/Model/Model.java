package com.pcc.PatientCareCenter.Model;

import com.pcc.PatientCareCenter.Views.CommonViewFactory;

public class Model {
    private static Model model;
    private final CommonViewFactory commonViewFactory;
    private PasswordValidator validator;

    private Model(){
        this.commonViewFactory = new CommonViewFactory();
    }

    public CommonViewFactory getCommonViewFactory() {
        return commonViewFactory;
    }

    public PasswordValidator getPasswordValidatorForSignup(){
        if(validator==null){
            validator=new PasswordValidator();
        }
        return validator;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }


}
