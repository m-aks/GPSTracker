package com.example.gpstracker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.example.gpstracker.view.activity.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
public class UITest {

    private String stringLogin;
    private String stringPassword;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void initValidString() {
        stringLogin = "testUser";
        stringPassword = "testPassword";
    }
    @Test
    public void pressButtonGo() {
        onView(withId(R.id.btn_login)).perform(click());
    }
    @Test
    public void inputLogin() {
        onView(withId(R.id.input_login))
                .perform(typeText(stringLogin), closeSoftKeyboard());
    }
    @Test
    public void inputPassword() {
        onView(withId(R.id.input_password))
                .perform(typeText(stringPassword), closeSoftKeyboard());
    }

}
