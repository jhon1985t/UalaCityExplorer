package com.jhonjto.ualacityexplorer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun search_displays_matching_cities() {
        composeTestRule.onNodeWithText("Buscar ciudad").performTextInput("Al")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Alabama, US").assertIsDisplayed()
        composeTestRule.onNodeWithText("Albuquerque, US").assertIsDisplayed()
    }

    @Test
    fun toggle_favorite_icon_changes() {
        composeTestRule.onNodeWithText("Alabama, US").performClick()
        composeTestRule.onNodeWithContentDescription("Favorite").performClick()
    }
}
