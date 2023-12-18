package com.mertaydin.rickandmortymvvm

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mertaydin.rickandmortymvvm.activity.ListCharactersActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ListCharactersActivity::class.java)

    @Test
    fun main() {
        Thread.sleep(2000)
        onView(withText("Rick Sanchez")).perform(click())
        Thread.sleep(2000)
        onView(withText("Episodes")).perform(click())
        onView(withText("Episodes")).perform(click())
        pressBack()
        onView(withText("Beth Smith")).perform(click())
        onView(withText("Episodes")).perform(click())
        onView(withText("Episodes")).perform(click())
        pressBack()
        swipeUp()
    }
}