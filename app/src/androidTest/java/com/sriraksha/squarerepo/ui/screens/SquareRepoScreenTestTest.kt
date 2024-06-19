package com.sriraksha.squarerepo.ui.screens

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.presentation.SquareRepoViewModel
import com.sriraksha.squarerepo.presentation.SquareUiState
import com.sriraksha.squarerepo.presentation.utils.DateUtils
import com.sriraksha.squarerepo.presentation.utils.DateUtils.Companion.DATE_FORMAT_EEEE_DD_MMMM_YYYY
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class SquareRepoScreenTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun loadingStateDisplaysLoadingScreen() {
        val viewModel: SquareRepoViewModel = mockk()
        every { viewModel.uiState } returns MutableStateFlow<SquareUiState>(SquareUiState.Loading)

        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        val semantic = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)

        composeTestRule.onAllNodes(semantic, true).fetchSemanticsNodes().isNotEmpty()
        composeTestRule.onNodeWithText("Square Repositories").assertIsDisplayed()
    }

    @Test
    fun successStateDisplaysRepositoryList() {
        val viewModel: SquareRepoViewModel = mockk()
        val uiStateFlow = MutableStateFlow<SquareUiState>(SquareUiState.Success(mockSquareRepos))

        every { viewModel.uiState } returns uiStateFlow
        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        composeTestRule.onRoot().printToLog("ComposeView")
        composeTestRule.onNodeWithText(mockSquareRepos[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(mockSquareRepos[0].description).assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Created")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[0].createdAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Updated")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[0].updatedAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Pushed")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[0].pushedAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Watchers")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(mockSquareRepos[0].watchersCount.toString())[0].assertIsDisplayed()


        composeTestRule.onNodeWithText(mockSquareRepos[1].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(mockSquareRepos[1].description).assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Created")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[1].createdAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Updated")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[1].updatedAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Pushed")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(
            DateUtils.dateToString(
                mockSquareRepos[1].pushedAt,
                DATE_FORMAT_EEEE_DD_MMMM_YYYY
            )
        )[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Watchers")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(mockSquareRepos[1].watchersCount.toString())[0].assertIsDisplayed()

    }

    @Test
    fun errorStateDisplaysErrorMessage() {
        val errorMessage = "Something went wrong"
        val viewModel: SquareRepoViewModel = mockk()

        every { viewModel.uiState } returns MutableStateFlow<SquareUiState>(
            SquareUiState.Error(
                errorMessage
            )
        )

        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun noNetworkErrorStateDisplaysNoNetworkMessage() {
        val errorMessage = "No network connection available"
        val viewModel: SquareRepoViewModel = mockk()

        every { viewModel.uiState } returns MutableStateFlow<SquareUiState>(
            SquareUiState.Error(
                errorMessage
            )
        )

        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun retryFetchingSquareReposWhenErrorMessageClicked() {
        val errorMessage = "No network connection available"
        val viewModel: SquareRepoViewModel = mockk()

        val uiStateFlow = MutableStateFlow<SquareUiState>(SquareUiState.Loading)
        val errorUiState = SquareUiState.Error(errorMessage)

        coEvery { viewModel.uiState } coAnswers {
            uiStateFlow.value = SquareUiState.Loading
            delay(100)
            uiStateFlow.value = errorUiState
            uiStateFlow
        }

        coEvery { viewModel.loadSquareRepositories() } answers {
            uiStateFlow.value = SquareUiState.Error(errorMessage)
        }

        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
        composeTestRule.onNode(hasTestTag("loading-indicator")).assertDoesNotExist()

        composeTestRule.onNodeWithText("Retry").performClick()
        coVerify { viewModel.loadSquareRepositories() }
    }

    @Test
    fun loadingStateOnEmptyListDisplaysLoadingScreen() {
        val viewModel: SquareRepoViewModel = mockk()
        every { viewModel.uiState } returns MutableStateFlow<SquareUiState>(
            SquareUiState.Success(
                emptyList()
            )
        )

        composeTestRule.setContent {
            SquareRepoScreen(viewModel = viewModel)
        }

        val semantic = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)

        composeTestRule.onAllNodes(semantic, true).fetchSemanticsNodes().isNotEmpty()

        composeTestRule.onNodeWithText("Square Repositories").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name").assertDoesNotExist()

    }

    private val mockSquareRepos = listOf(
        SquareRepo(
            1L,
            1L,
            "Repo1",
            "Description1",
            "http://image.url/1",
            5L,
            "2024-01-01T12:00:00Z",
            "2024-01-01T12:00:00Z",
            "2024-01-01T12:00:00Z",
            100L,
            "http://org.url/1",
            "http://repo.url/1",
            "public",
            false
        ),
        SquareRepo(
            2L,
            2L,
            "Repo2",
            "Description2",
            "http://image.url/2",
            10L,
            "2024-01-01T12:00:00Z",
            "2024-01-01T12:00:00Z",
            "2024-01-01T12:00:00Z",
            200L,
            "http://org.url/2",
            "http://repo.url/2",
            "public",
            false
        )
    )
}
