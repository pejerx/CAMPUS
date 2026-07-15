package cit.edu.garol.campus.features.claimrequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cit.edu.garol.campus.features.claimrequest.repository.ClaimRequestRepository

class ClaimRequestViewModelFactory(
    private val repository: ClaimRequestRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ClaimRequestViewModel::class.java)) {

            return ClaimRequestViewModel(
                repository
            ) as T

        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}