package br.com.vagners.sendmoney;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.vagners.sendmoney.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vagnerss on 28/08/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void send_test() {

        onView(withId(R.id.user_name))
                .check(matches(withText(R.string.user_name)));

        onView(withId(R.id.user_email))
                .check(matches(withText(R.string.user_email)));

        onView(withId(R.id.btn_send))
                .perform(click());

        onView(withId(R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.send_value)).perform(click()).perform(typeText("30"));

        onView(withId(R.id.btn_confirm)).perform(click());

        onView(withText(R.string.send_sucess));

    }

    @Test
    public void extract_test() {

        onView(withId(R.id.user_name))
                .check(matches(withText(R.string.user_name)));

        onView(withId(R.id.user_email))
                .check(matches(withText(R.string.user_email)));

        onView(withId(R.id.btn_extract))
                .perform(click());

    }
}
