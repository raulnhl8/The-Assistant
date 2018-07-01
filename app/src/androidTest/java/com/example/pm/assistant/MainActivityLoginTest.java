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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.pm.assistant.MainActivityTest.withRecyclerView;

@RunWith(AndroidJUnit4.class)
public class MainActivityLoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void login_ShowCaregiverItem() {
        Activity activity = mActivityTestRule.getActivity();
        myDatabase db = myDatabase.getsInstance(activity.getApplicationContext());
        Contato contato = new Contato("nomeCuidador", "relacionamentoCuidador", "caminhodafoto.png", "cuidadorFaceToken");
        db.dao().addContato(contato);
        Cuidador cuidador = new Cuidador("email2", "password2", "cel", "enderço", (db.dao().getAllContatos()).size() - 1);
        db.dao().addCuidador(cuidador);

        onView(withId(R.id.emailEditText)).perform(typeText("email2")).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordEditText)).perform(typeText("password2")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withRecyclerView(R.id.contatosList).atPosition((db.dao().getAllContatos()).size() - 1))
                .check(matches(hasDescendant(withText("nomeCuidador"))));
        onView(withRecyclerView(R.id.contatosList).atPosition((db.dao().getAllContatos()).size() - 1))
                .check(matches(hasDescendant(withText("relacionamentoCuidador"))));
    }
}