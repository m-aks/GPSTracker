package com.example.gpstracker.viewModel;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gpstracker.BR;
import com.example.gpstracker.R;
import com.example.gpstracker.database.asynctask.GetActiveUserAsyncTask;
import com.example.gpstracker.database.asynctask.GetUserByLoginAsyncTask;
import com.example.gpstracker.database.asynctask.InsertUserDataAsyncTask;
import com.example.gpstracker.database.asynctask.UpdateUserAsyncTask;
import com.example.gpstracker.database.entity.UserEntity;
import com.example.gpstracker.model.AuthRequest;
import com.example.gpstracker.view.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginViewModel extends BaseObservable {

    static final String baseUrl = "http://192.168.1.36:8080/api/v1/auth";

    private String login = "";
    private String password = "";
    private String errorMessage = "";
    private boolean errorFlag = false;
    private boolean isLoginEmpty = true;
    private boolean isPasswordEmpty = true;

    /*public LoginViewModel() {
        login = "";
        password = "";
        errorMessage = "";
        errorFlag = false;
        isLoginEmpty = true;
        isPasswordEmpty = true;
    }*/

    @Bindable
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        setIsLoginEmpty(login.equals(""));
        this.login = login;
        notifyPropertyChanged(BR.login);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        setIsPasswordEmpty(password.equals(""));
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        setErrorFlag(true);
        notifyPropertyChanged(BR.errorMessage);
    }

    @Bindable
    public boolean getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
        notifyPropertyChanged(BR.errorFlag);
    }

    @Bindable
    public boolean getIsLoginEmpty() {
        return isLoginEmpty;
    }

    public void setIsLoginEmpty(boolean isLoginEmpty) {
        this.isLoginEmpty = isLoginEmpty;
        notifyPropertyChanged(BR.isLoginEmpty);
    }

    @Bindable
    public boolean getIsPasswordEmpty() {
        return isPasswordEmpty;
    }

    public void setIsPasswordEmpty(boolean isPasswordEmpty) {
        this.isPasswordEmpty = isPasswordEmpty;
        notifyPropertyChanged(BR.isPasswordEmpty);
    }

    public void register(final Object activity) {
        if (activity instanceof LoginActivity) {
            ((LoginActivity) activity).startRegisterActivity();
        }
    }

    public void check(final Object activity) {
        if (activity instanceof LoginActivity) {
            String url = baseUrl + "/check";
            try {
                UserEntity user = new GetActiveUserAsyncTask().execute().get();
                if (user != null) {
                    String token = user.getToken();
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer_" + token);
                    Volley.newRequestQueue((LoginActivity) activity).add(
                            new AuthRequest(url, headers,
                                    response -> ((LoginActivity) activity).startMainActivity(),
                                    error -> {
                                        user.setActive(false);
                                        new UpdateUserAsyncTask().execute(user);
                                    })
                    );
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void login(final Object activity) {
        if (activity instanceof LoginActivity) {
            String url = baseUrl + "/login";
            JSONObject postData = new JSONObject();
            try {
                postData.put("username", login);
                postData.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Volley.newRequestQueue((LoginActivity) activity).add(
                    new JsonObjectRequest(Request.Method.POST, url, postData,
                            response -> handleResponse((LoginActivity) activity, response),
                            error -> setErrorMessage(error.getMessage())));
        }
    }

    void handleResponse(LoginActivity activity, JSONObject response) {
        try {
            String token = response.get("token").toString();
            UserEntity user = new GetUserByLoginAsyncTask().execute(login).get();
            if (user != null) {
                user.setToken(token);
                user.setActive(true);
                new UpdateUserAsyncTask().execute(user);
            } else {
                user = new UserEntity();
                user.setLogin(login);
                user.setToken(token);
                user.setActive(true);
                new InsertUserDataAsyncTask().execute(user);
            }
            activity.startMainActivity();
        } catch (JSONException e) {
            setErrorMessage(activity.getResources()
                    .getString(R.string.token_not_found));
        } catch (ExecutionException | InterruptedException e) {
            setErrorFlag(true);
        }
    }
}
