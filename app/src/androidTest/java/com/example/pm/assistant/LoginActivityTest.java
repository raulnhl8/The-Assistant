package com.example.pm.assistant;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.myDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void clickRegisterButton_OpenRegister1Activity() {
        onView(withId(R.id.registerTextView)).perform(click());

        onView(withId(R.id.textView1)).check(matches(isDisplayed()));
    }

    @Test
    public void login_OpenMainActivity() {
        Activity activity = mActivityTestRule.getActivity();
        myDatabase db = myDatabase.getsInstance(activity.getApplicationContext());
        Contato contato = new Contato("nomeCuidador", "relacionamentoCuidador", "caminhodafoto.png", "cuidadorFaceToken");
        db.dao().addContato(contato);
        Cuidador cuidador = new Cuidador("email0", "password0", "cel", "enderço", (db.dao().getAllContatos()).size() - 1);
        db.dao().addCuidador(cuidador);

        onView(withId(R.id.emailEditText)).perform(typeText("email0")).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password0")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }

    @Test
    public void incorrectLogin_NoChange() {
        onView(withId(R.id.emailEditText)).perform(typeText("email1")).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password1")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.login_view)).check(matches(isDisplayed()));
    }

}