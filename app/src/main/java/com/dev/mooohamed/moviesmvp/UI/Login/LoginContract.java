package com.dev.mooohamed.moviesmvp.UI.Login;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public interface LoginContract {


    interface DisplayDataOnView{

        void onDisplay(JSONObject object);
    }

    interface LoginPresenter{

        void onReceive(JSONObject object);

        FacebookCallback<LoginResult> getCallback();
    }

    interface SendCallbackToData{

        FacebookCallback<LoginResult> getCallback(LoginContract.DisplayDataOnView view);
    }

}
