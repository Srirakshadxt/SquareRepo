package com.sriraksha.squarerepo.presentation

import com.sriraksha.squarerepo.data.model.SquareRepo

/** Sealed class representing the UI state of the square repositories with
 * Loading
 * Success
 * Error states. */

sealed class SquareUiState {
    data object Loading : SquareUiState()
    data class Success(val squareRepos: List<SquareRepo>) : SquareUiState()
    data class Error(val errorMessage: String) : SquareUiState()
}