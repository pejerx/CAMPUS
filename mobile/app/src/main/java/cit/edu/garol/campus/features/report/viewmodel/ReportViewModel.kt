package cit.edu.garol.campus.features.report.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cit.edu.garol.campus.core.network.MultipartUtils
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.report.repository.ReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReportUiState(

    val isLoading: Boolean = false,

    val reports: List<AdminReportItem> = emptyList(),

    val errorMessage: String? = null,

    val uploadSuccess: Boolean = false

)

class ReportViewModel(

    private val repository: ReportRepository

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ReportUiState()
        )

    val uiState: StateFlow<ReportUiState> =
        _uiState.asStateFlow()

    /*
     * ==========================================================
     * PUBLIC REPORTS
     * ==========================================================
     */

    fun loadPublicReports() {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {

                val reports =
                    repository.getPublicReports()

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        reports = reports
                    )

            } catch (exception: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage =
                        exception.message
                            ?: "Cannot connect to the server."
                    )

            }

        }

    }

    /*
     * ==========================================================
     * USER REPORTS
     * ==========================================================
     */

    fun loadMyReports(
        userId: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {

                val reports =
                    repository.getMyReports(userId)

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        reports = reports
                    )

            } catch (exception: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage =
                        exception.message
                            ?: "Cannot connect to the server."
                    )

            }

        }

    }

    /*
     * ==========================================================
     * CREATE REPORT
     * ==========================================================
     */

    fun submitReport(

        context: Context,

        userId: String,

        reportType: String,

        itemName: String,

        category: String,

        description: String,

        location: String,

        imageUri: Uri?

    ) {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null,
                    uploadSuccess = false
                )

            try {

                val imagePart =
                    MultipartUtils.createPartFromUri(
                        context,
                        imageUri
                    )

                repository.createReport(

                    userId = MultipartUtils.createRequestBody(userId),

                    reportType = MultipartUtils.createRequestBody(reportType),

                    itemName = MultipartUtils.createRequestBody(itemName),

                    category = MultipartUtils.createRequestBody(category),

                    description = MultipartUtils.createRequestBody(description),

                    location = MultipartUtils.createRequestBody(location),

                    image = imagePart

                )

                _uiState.value =
                    _uiState.value.copy(

                        isLoading = false,

                        uploadSuccess = true

                    )

            } catch (exception: Exception) {

                exception.printStackTrace()

                _uiState.value =
                    _uiState.value.copy(

                        isLoading = false,

                        uploadSuccess = false,

                        errorMessage =
                        exception.message
                            ?: "Failed to submit report."

                    )

            }

        }

    }

}