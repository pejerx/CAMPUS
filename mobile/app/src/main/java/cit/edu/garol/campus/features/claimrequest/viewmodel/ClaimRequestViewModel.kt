package cit.edu.garol.campus.features.claimrequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest
import cit.edu.garol.campus.features.claimrequest.repository.ClaimRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ClaimRequestUiState(

    val isLoading: Boolean = false,

    val claimRequests: List<ClaimRequest> = emptyList(),

    val errorMessage: String? = null,

    val successMessage: String? = null

)

class ClaimRequestViewModel(
    private val repository: ClaimRequestRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ClaimRequestUiState()
        )

    val uiState: StateFlow<ClaimRequestUiState> =
        _uiState.asStateFlow()

    /*
     * ==========================================================
     * LOAD CLAIM REQUESTS
     * ==========================================================
     */

    fun loadClaimRequests() {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {

                val response =
                    repository.getAllClaimRequests()

                if (response.isSuccessful) {

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            claimRequests =
                            response.body()
                                ?: emptyList()
                        )

                } else {

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Unable to load claim requests."
                        )

                }

            } catch (exception: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage =
                        exception.message
                            ?: "Unable to connect to the server."
                    )

            }

        }

    }

    /*
     * ==========================================================
     * APPROVE CLAIM REQUEST
     * ==========================================================
     */

    fun approveClaimRequest(
        claimRequestId: Int
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.approveClaimRequest(
                        claimRequestId
                    )

                if (response.isSuccessful) {

                    loadClaimRequests()

                    _uiState.value =
                        _uiState.value.copy(
                            successMessage =
                            "Claim request approved."
                        )

                } else {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage =
                            "Unable to approve claim request."
                        )

                }

            } catch (exception: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        errorMessage =
                        exception.message
                            ?: "Unable to connect to the server."
                    )

            }

        }

    }

    /*
     * ==========================================================
     * REJECT CLAIM REQUEST
     * ==========================================================
     */

    fun rejectClaimRequest(
        claimRequestId: Int
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.rejectClaimRequest(
                        claimRequestId
                    )

                if (response.isSuccessful) {

                    loadClaimRequests()

                    _uiState.value =
                        _uiState.value.copy(
                            successMessage =
                            "Claim request rejected."
                        )

                } else {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage =
                            "Unable to reject claim request."
                        )

                }

            } catch (exception: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        errorMessage =
                        exception.message
                            ?: "Unable to connect to the server."
                    )

            }

        }

    }

    /*
     * ==========================================================
     * CLEAR MESSAGE
     * ==========================================================
     */

    fun clearMessage() {

        _uiState.value =
            _uiState.value.copy(
                errorMessage = null,
                successMessage = null
            )

    }

}