package cit.edu.garol.campus.features.admin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.admin.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AdminLoginUiState(
    val adminId: String = "",
    val password: String = "",
    val loggedInAdminId: String? = null,
    val adminName: String? = null,
    val adminRole: String? = null,
    val redirectTo: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String? = null
)

data class AdminDashboardUiState(
    val isLoading: Boolean = false,
    val isUpdatingStatus: Boolean = false,
    val reportedItems: List<AdminReportItem> = emptyList(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class AdminViewModel(
    private val repository: AdminRepository
) : ViewModel() {

    private val _loginUiState =
        MutableStateFlow(AdminLoginUiState())

    val loginUiState: StateFlow<AdminLoginUiState> =
        _loginUiState.asStateFlow()

    private val _dashboardUiState =
        MutableStateFlow(AdminDashboardUiState())

    val dashboardUiState: StateFlow<AdminDashboardUiState> =
        _dashboardUiState.asStateFlow()

    fun updateAdminId(adminId: String) {
        _loginUiState.value =
            _loginUiState.value.copy(
                adminId = adminId,
                errorMessage = null
            )
    }

    fun updatePassword(password: String) {
        _loginUiState.value =
            _loginUiState.value.copy(
                password = password,
                errorMessage = null
            )
    }

    fun loginAdmin() {
        val currentState = _loginUiState.value

        val adminId =
            currentState.adminId.trim()

        val password =
            currentState.password

        if (adminId.isBlank()) {
            _loginUiState.value =
                currentState.copy(
                    errorMessage =
                    "Please enter the administrator ID."
                )

            return
        }

        if (password.isBlank()) {
            _loginUiState.value =
                currentState.copy(
                    errorMessage =
                    "Please enter the administrator password."
                )

            return
        }

        viewModelScope.launch {
            _loginUiState.value =
                _loginUiState.value.copy(
                    isLoading = true,
                    isLoginSuccessful = false,
                    errorMessage = null
                )

            try {
                val response =
                    repository.loginAdmin(
                        adminId = adminId,
                        password = password
                    )

                if (response.isSuccessful) {
                    val adminResponse =
                        response.body()

                    if (adminResponse == null) {
                        _loginUiState.value =
                            _loginUiState.value.copy(
                                isLoading = false,
                                isLoginSuccessful = false,
                                errorMessage =
                                "The server returned an empty response."
                            )

                        return@launch
                    }

                    val isAdministrator =
                        adminResponse.role.equals(
                            "ADMIN",
                            ignoreCase = true
                        ) ||
                                adminResponse.role.equals(
                                    "ADMINISTRATOR",
                                    ignoreCase = true
                                )

                    if (isAdministrator) {
                        _loginUiState.value =
                            _loginUiState.value.copy(
                                isLoading = false,
                                isLoginSuccessful = true,
                                loggedInAdminId =
                                adminResponse.id,
                                adminName =
                                adminResponse.firstName,
                                adminRole =
                                adminResponse.role,
                                redirectTo =
                                adminResponse.redirectTo,
                                errorMessage = null
                            )
                    } else {
                        _loginUiState.value =
                            _loginUiState.value.copy(
                                isLoading = false,
                                isLoginSuccessful = false,
                                errorMessage =
                                "The account does not have administrator access."
                            )
                    }
                } else {
                    val backendMessage =
                        response.errorBody()
                            ?.string()
                            ?.removePrefix("\"")
                            ?.removeSuffix("\"")
                            ?.trim()

                    val errorText =
                        if (!backendMessage.isNullOrBlank()) {
                            backendMessage
                        } else {
                            when (response.code()) {
                                400 ->
                                    "Invalid login request."

                                401 ->
                                    "Incorrect administrator ID or password."

                                403 ->
                                    "Administrator access was denied."

                                404 ->
                                    "The administrator login endpoint was not found."

                                500 ->
                                    "The server encountered an error."

                                else ->
                                    "Login failed. Error code: ${response.code()}"
                            }
                        }

                    _loginUiState.value =
                        _loginUiState.value.copy(
                            isLoading = false,
                            isLoginSuccessful = false,
                            errorMessage = errorText
                        )
                }
            } catch (exception: Exception) {
                _loginUiState.value =
                    _loginUiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = false,
                        errorMessage =
                        exception.message
                            ?: "Cannot connect to the backend server."
                    )
            }
        }
    }

    fun resetLoginResult() {
        _loginUiState.value =
            _loginUiState.value.copy(
                isLoginSuccessful = false,
                errorMessage = null
            )
    }

    fun logoutAdmin() {
        _loginUiState.value =
            AdminLoginUiState()

        _dashboardUiState.value =
            AdminDashboardUiState()
    }

    fun loadReportedItems() {
        viewModelScope.launch {
            _dashboardUiState.value =
                _dashboardUiState.value.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )

            try {
                val response =
                    repository.getReportedItems()

                if (response.isSuccessful) {
                    _dashboardUiState.value =
                        AdminDashboardUiState(
                            isLoading = false,
                            isUpdatingStatus = false,
                            reportedItems =
                            response.body().orEmpty(),
                            errorMessage = null,
                            successMessage = null
                        )
                } else {
                    val backendMessage =
                        response.errorBody()
                            ?.string()
                            ?.removePrefix("\"")
                            ?.removeSuffix("\"")
                            ?.trim()

                    _dashboardUiState.value =
                        _dashboardUiState.value.copy(
                            isLoading = false,
                            errorMessage =
                            backendMessage
                                ?: "Unable to retrieve reports. Error code: ${response.code()}"
                        )
                }
            } catch (exception: Exception) {
                _dashboardUiState.value =
                    _dashboardUiState.value.copy(
                        isLoading = false,
                        errorMessage =
                        exception.message
                            ?: "Cannot connect to the server."
                    )
            }
        }
    }

    fun updateReportStatus(
        reportId: String,
        newStatus: String
    ) {
        if (reportId.isBlank()) {
            _dashboardUiState.value =
                _dashboardUiState.value.copy(
                    errorMessage =
                    "The report ID is missing."
                )

            return
        }

        if (newStatus.isBlank()) {
            _dashboardUiState.value =
                _dashboardUiState.value.copy(
                    errorMessage =
                    "Please select a valid status."
                )

            return
        }

        viewModelScope.launch {
            _dashboardUiState.value =
                _dashboardUiState.value.copy(
                    isUpdatingStatus = true,
                    errorMessage = null,
                    successMessage = null
                )

            try {
                val response =
                    repository.updateReportStatus(
                        reportId = reportId,
                        status = newStatus
                    )

                if (response.isSuccessful) {
                    val updatedReport =
                        response.body()

                    if (updatedReport != null) {
                        val updatedList =
                            _dashboardUiState.value
                                .reportedItems
                                .map { report ->
                                    if (report.id == updatedReport.id) {
                                        updatedReport
                                    } else {
                                        report
                                    }
                                }

                        _dashboardUiState.value =
                            _dashboardUiState.value.copy(
                                isUpdatingStatus = false,
                                reportedItems = updatedList,
                                errorMessage = null,
                                successMessage =
                                "Report status updated successfully."
                            )
                    } else {
                        _dashboardUiState.value =
                            _dashboardUiState.value.copy(
                                isUpdatingStatus = false,
                                errorMessage = null,
                                successMessage =
                                "Report status updated successfully."
                            )

                        loadReportedItems()
                    }
                } else {
                    val backendMessage =
                        response.errorBody()
                            ?.string()
                            ?.removePrefix("\"")
                            ?.removeSuffix("\"")
                            ?.trim()

                    val errorText =
                        if (!backendMessage.isNullOrBlank()) {
                            backendMessage
                        } else {
                            when (response.code()) {
                                400 ->
                                    "Invalid status request."

                                404 ->
                                    "The item report was not found."

                                500 ->
                                    "The server encountered an error."

                                else ->
                                    "Failed to update report status. Error code: ${response.code()}"
                            }
                        }

                    _dashboardUiState.value =
                        _dashboardUiState.value.copy(
                            isUpdatingStatus = false,
                            errorMessage = errorText
                        )
                }
            } catch (exception: Exception) {
                _dashboardUiState.value =
                    _dashboardUiState.value.copy(
                        isUpdatingStatus = false,
                        errorMessage =
                        exception.message
                            ?: "Cannot connect to the server."
                    )
            }
        }
    }

    fun clearDashboardMessage() {
        _dashboardUiState.value =
            _dashboardUiState.value.copy(
                errorMessage = null,
                successMessage = null
            )
    }
}