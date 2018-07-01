package com.example.pm.assistant;


import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.myDatabase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickAddButton_OpenAddContactFragment() {
        onView(withId(R.id.navigation_add)).perform(click());

        onView(withId(R.id.add_contact_view)).check(matches(isDisplayed()));
    }

    @Test
    public void clickContactsButton_NoChange() {
        onView(withId(R.id.navigation_contact)).perform(click());

        onView(withId(R.id.contatosList)).check(matches(isDisplayed()));
    }

    @Test
    public void clickAddButtonClickContactsButton_OpenContactsFragment() {
        onView(withId(R.id.navigation_add)).perform(click());
        onView(withId(R.id.navigation_contact)).perform(click());

        onView(withId(R.id.contatosList)).check(matches(isDisplayed()));
    }

    @Test
    public void addContact_ShowNewContactOnList() {
        Activity activity = mActivityTestRule.getActivity();
        myDatabase db = myDatabase.getsInstance(activity.getApplicationContext());
        Contato contato = new Contato("nomeTeste", "relacionamentoTeste", "caminhodafoto.png", "face_token");
        db.dao().addContato(contato);

        onView(withId(R.id.navigation_contact)).perform(click());

        onView(withRecyclerView(R.id.contatosList).atPosition((db.dao().getAllContatos()).size() - 1))
                .check(matches(hasDescendant(withText("nomeTeste"))));
        onView(withRecyclerView(R.id.contatosList).atPosition((db.dao().getAllContatos()).size() - 1))
                .check(matches(hasDescendant(withText("relacionamentoTeste"))));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    public static class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[] { Integer.valueOf
                                            (recyclerViewId) });
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        }
                        else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }
}