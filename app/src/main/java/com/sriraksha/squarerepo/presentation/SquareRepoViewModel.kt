package com.sriraksha.squarerepo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sriraksha.squarerepo.R
import com.sriraksha.squarerepo.data.NoMorePagesException
import com.sriraksha.squarerepo.data.NoNetworkException
import com.sriraksha.squarerepo.data.RemoteDataSourceException
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.domain.usecases.GetSquareReposUseCase
import com.sriraksha.squarerepo.presentation.utils.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareRepoViewModel @Inject constructor(
    private val getSquareReposUseCase: GetSquareReposUseCase,
    private val resources: ResourcesProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<SquareUiState>(SquareUiState.Loading)
    val uiState: StateFlow<SquareUiState> = _uiState.asStateFlow()

    private val _selectedPlanet = MutableStateFlow<SquareRepo?>(null)
    val selectedPlanet: StateFlow<SquareRepo?> = _selectedPlanet.asStateFlow()

    fun loadSquareRepositories() {
        viewModelScope.launch {
            _uiState.value = SquareUiState.Loading
            getSquareReposUseCase().fold(
                onSuccess = { response ->
                    if (response.isNotEmpty())
                        _uiState.value = SquareUiState.Success(response)
                    else
                        handleError(Exception("No more pages available"))
                },
                onFailure = { error ->
                    handleError(error)
                }
            )
        }
    }


    private fun handleError(error: Throwable) {
        _uiState.value = when (error) {
            is NoNetworkException -> SquareUiState.Error(
                error.message ?: resources.getString(R.string.no_network_connection_available)
            )

            is NoMorePagesException -> SquareUiState.Error(resources.getString(R.string.no_more_pages_available))
            is RemoteDataSourceException -> SquareUiState.Error(
                error.message ?: resources.getString(R.string.something_went_wrong)
            )

            else -> SquareUiState.Error(
                error.message ?: resources.getString(R.string.something_went_wrong)
            )
        }
    }
}