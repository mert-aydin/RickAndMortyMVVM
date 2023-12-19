package com.mertaydin.rickandmortymvvm

import android.content.Context
import android.graphics.Bitmap
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.takeScreenshot
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mertaydin.rickandmortymvvm.activity.ListCharactersActivity
import com.mertaydin.rickandmortymvvm.adapter.CharacterRecyclerViewAdapter
import com.mertaydin.rickandmortymvvm.util.NetworkIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ListCharactersActivity::class.java)

    @Before
    fun registerIdlingResource() {
        ActivityScenario.launch(ListCharactersActivity::class.java).onActivity {
            IdlingRegistry.getInstance().register(NetworkIdlingResource)
        }
    }

    @Test
    fun main() {
        onView(withText("Rick Sanchez")).perform(click())
        onView(withText("Episodes")).perform(click())
        onView(withText("Pilot (S01 - E01)")).check(matches(isDisplayed()))
        saveScreenshot()
        onView(withText("Episodes")).perform(click())
        pressBack()
        onView(withText("Beth Smith")).perform(click())
        onView(withText("Episodes")).perform(click())
        onView(withText("Episodes")).perform(click())
        pressBack()
        var position = 10
        onView(withId(R.id.character_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<CharacterRecyclerViewAdapter.CharacterViewHolder>(
                position
            )
        )
        onView(withId(R.id.character_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterRecyclerViewAdapter.CharacterViewHolder>(
                position, click()
            )
        )
        pressBack()

        /**
         * Failing test case example
         * If the test fails it'll automatically save screenshot of last state of the application.
         * /storage/emulated/0/Android/media/com.mertaydin.rickandmortymvvm/additional_test_output
         */

        /*
        position = 150
        onView(withId(R.id.character_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<CharacterRecyclerViewAdapter.CharacterViewHolder>(
                position
            )
        )
        */
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource)
    }
}

private fun saveScreenshot() {
    // /storage/emulated/0/Android/data/com.mertaydin.rickandmortymvvm/files
    val directory = ApplicationProvider.getApplicationContext<Context>().getExternalFilesDir(null)
    val filename = "${LocalDateTime.now()}.png"

    try {
        FileOutputStream(File(directory, filename)).use { out ->
            takeScreenshot().compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}