package com.example.rightpoint.omdb.search


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.rightpoint.omdb.R
import com.example.rightpoint.omdb.shows.ShowListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith




@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchActivityTest {

    private lateinit var searchQuery: String


    @get:Rule var activityRule: ActivityTestRule<SearchActivity>
            = ActivityTestRule(SearchActivity::class.java)


    @Before fun initValidString() {
        searchQuery = "Joker"
    }


    fun searchWithDefaultValue() {
        onView(withId(R.id.titleField)).perform(typeText(searchQuery), closeSoftKeyboard())
        onView(withId(R.id.executeSearchAction)).perform(click())
    }


    @Test fun performValidSearchMovie() {
        searchWithDefaultValue()
        onView(withId(R.id.moviesAction)).check(matches(isDisplayed()))
        onView(withId(R.id.moviesAction)).perform(click())
    }


    @Test fun performValidSearchSeries() {
        searchWithDefaultValue()
        onView(withId(R.id.seriesAction)).check(matches(isDisplayed()))
        onView(withId(R.id.seriesAction)).perform(click())
    }


    @Test fun performValidSearchAnything() {
        searchWithDefaultValue()
        onView(withId(R.id.anythingAction)).check(matches(isDisplayed()))
        onView(withId(R.id.anythingAction)).perform(click())
    }


    @Test fun performInvalidSearch() {
        onView(withId(R.id.titleField)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.executeSearchAction)).perform(click())
        onView(withText(R.string.search_invalid)).check(matches(isDisplayed()))
    }


    @Test fun performSearchResultingInEmpty() {
        onView(withId(R.id.titleField)).perform(typeText("lollol-should-be-empty"), closeSoftKeyboard())
        onView(withId(R.id.executeSearchAction)).perform(click())
        onView(withId(R.id.anythingAction)).perform(click())
        onView(withText(R.string.general_error_empty)).check(matches(isDisplayed()))
    }

}
