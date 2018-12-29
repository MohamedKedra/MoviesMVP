package com.dev.mooohamed.moviesmvp.UI.Login;

import android.os.Bundle;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginData implements LoginContract.SendCallbackToData {

    LoginPresenter loginPresenter;

    public LoginData() {
    }

    @Override
    public FacebookCallback<LoginResult> getCallback(final LoginContract.DisplayDataOnView view) {

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        loginPresenter = new LoginPresenter(view);
                        loginPresenter.onReceive(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","id,first_name,last_name,email,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("Cancelled ");
            }

            @Override
            public void onError(FacebookException error) {

                System.out.println("Error "+error.getMessage());
            }
        };

        return callback;
    }


}