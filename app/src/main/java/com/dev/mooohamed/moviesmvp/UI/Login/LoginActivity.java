package com.dev.mooohamed.moviesmvp.UI.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mooohamed.moviesmvp.Data.Prefs;
import com.dev.mooohamed.moviesmvp.R;
import com.dev.mooohamed.moviesmvp.Services.Urls;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.DisplayDataOnView{

    @BindView(R.id.btn_login)
    LoginButton login;
    @BindView(R.id.iv_profile)
    ImageView avatar;
    @BindView(R.id.tv_name)
    TextView title;
    @BindView(R.id.tv_email)
    TextView email;
    @BindView(R.id.tv_birthday)
    TextView birthday;
    CallbackManager callbackManager;
    LoginContract.LoginPresenter presenter;
    Prefs prefs;
    static boolean loggedOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle(R.string.profile);
        presenter = new LoginPresenter(this);
        callbackManager = CallbackManager.Factory.create();
        prefs = new Prefs(this);
        System.out.println("User : "+prefs.getUser());
        login.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));
        login.registerCallback(callbackManager, presenter.getCallback());
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLog();
    }

    public void checkLog(){
        loggedOut = AccessToken.getCurrentAccessToken() == null;
        if (!loggedOut){
            try {
                getData(new JSONObject(prefs.getUser()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            prefs.clearUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onDisplay(JSONObject object) {

        prefs.setUser(new Gson().toJson(object));
        getData(object);
    }

    public void getData(JSONObject object){

        try {
            if (!object.has("id")){
             object = object.getJSONObject("nameValuePairs");
            }
            URL profilePic = new URL(Urls.Facebook+object.getString("id")+Urls.AvatarSize);
            Picasso.with(this).load(profilePic.toString()).into(avatar);
            title.setText(object.getString("first_name")+"  "+object.getString("last_name"));
            email.setText(object.getString("email"));
            birthday.setText(object.getString("birthday"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}