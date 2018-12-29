package com.dev.mooohamed.moviesmvp.UI.Login;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginPresenter implements LoginContract.LoginPresenter {

    LoginContract.DisplayDataOnView displayDataOnView;
    LoginData loginData;

    public LoginPresenter(LoginContract.DisplayDataOnView displayDataOnView){
        this.displayDataOnView = displayDataOnView;
        loginData = new LoginData();
    }

    @Override
    public void onReceive(JSONObject object) {
        displayDataOnView.onDisplay(object);
    }

    @Override
    public FacebookCallback<LoginResult> getCallback() {
        return loginData.getCallback(displayDataOnView);
    }
}
