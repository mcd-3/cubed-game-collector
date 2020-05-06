package com.matthew.carvalhodagenais.cubedcollector

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.matthew.carvalhodagenais.cubedcollector.adapters.GameListRecyclerAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Tests to see if the user can add a game
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AddGameTest {

    private lateinit var title: String
    private lateinit var dev: String
    private lateinit var pub: String

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init_strings() {
        title = "AAA New Game"
        dev = "Bintendo"
        pub = "CubePub"
    }

    @Test
    fun add_game() {
        // Click the "add game" button on the menu
        onView(withId(R.id.menu_add))
            .perform(click())

        // Check title
        onView((withId(R.id.toolbar)))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText(R.string.navigation_game_add_title))))

        // Add the game info
        onView(withId(R.id.title_edit_text))
            .perform(typeText(title), closeSoftKeyboard())
        onView(withId(R.id.developer_edit_text))
            .perform(typeText(dev), closeSoftKeyboard())
        onView(withId(R.id.publisher_edit_text))
            .perform(typeText(pub), closeSoftKeyboard())

        // Save the game
        onView(withId(R.id.menu_save))
            .perform(click())

        // Click the game item
        onView(withId(R.id.game_list_recycler_view))
            .perform(RecyclerViewActions.scrollTo<GameListRecyclerAdapter.GameHolder>(
                hasDescendant(withText(title))))
        onView(withId(R.id.game_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItem<GameListRecyclerAdapter.GameHolder>(
                hasDescendant(withText(title)), click()))

        // Check if everything was saved correctly
        onView(withId(R.id.title_text_view))
            .check(matches(withText(title)))
        onView(withId(R.id.developer_text_view))
            .check(matches(withText(dev)))
        onView(withId(R.id.publisher_text_view))
            .check(matches(withText(pub)))
    }

}