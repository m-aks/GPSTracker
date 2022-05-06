package com.example.gpstracker.viewModel;

import static com.example.gpstracker.viewModel.LoginViewModel.baseUrl;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gpstracker.BR;
import com.example.gpstracker.R;
import com.example.gpstracker.database.asynctask.GetUserByLoginAsyncTask;
import com.example.gpstracker.database.asynctask.InsertUserDataAsyncTask;
import com.example.gpstracker.database.asynctask.UpdateUserAsyncTask;
import com.example.gpstracker.database.entity.UserEntity;
import com.example.gpstracker.view.activity.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RegisterViewModel extends BaseObservable {

    private String login = "";
    private String firstname = "";
    private String lastname = "";
    private String email = "";
    private String password = "";
    private String errorMessage = "";
    private boolean errorFlag = false;
    private boolean empty = true;

    @Bindable
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        setEmpty();
        notifyPropertyChanged(BR.login);
    }

    @Bindable
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        setEmpty();
        notifyPropertyChanged(BR.firstname);
    }

    @Bindable
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        setEmpty();
        notifyPropertyChanged(BR.lastname);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setEmpty();
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        setEmpty();
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyPropertyChanged(BR.errorMessage);
        setErrorFlag(true);
    }

    @Bindable
    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
        notifyPropertyChanged(BR.errorFlag);
    }

    @Bindable
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty() {
        this.empty = login == null || login.isEmpty() ||
                firstname == null || firstname.isEmpty() ||
                lastname == null || lastname.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty();

        notifyPropertyChanged(BR.empty);
    }

    public void register(final Object activity) {
        if (activity instanceof RegisterActivity) {
            String url = baseUrl + "/register";
            JSONObject postData = new JSONObject();
            try {
                postData.put("username", login);
                postData.put("firstName", firstname);
                postData.put("lastName", lastname);
                postData.put("email", email);
                postData.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Volley.newRequestQueue((RegisterActivity) activity).add(
                    new JsonObjectRequest(Request.Method.POST, url, postData,
                            response -> handleResponse((RegisterActivity) activity, response),
                            error -> setErrorMessage(error.getMessage())));
        }
    }

    private void handleResponse(RegisterActivity activity, JSONObject response) {
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
