package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TelemetryRepository
import com.example.model.AcuityLevel
import com.example.model.BedPatient
import com.example.model.CounterfactualState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

enum class ScreenDestination(val title: String, val iconName: String) {
    WARD_TRIAGE("Ward Triage Matrix", "grid_view"),
    PATIENT_DETAIL("Patient Detail (Bed 104)", "monitor_heart"),
    SBAR_HANDOFF("SBAR Handoff & Alerts", "notifications_active"),
    ANALYTICS("Analytics & Protocols", "query_stats")
}

enum class SortCriteria {
    VELOCITY_DESC,
    SCORE_DESC,
    BED_ASC
}

data class TelemetryUiState(
    val currentScreen: ScreenDestination = ScreenDestination.WARD_TRIAGE,
    val selectedPatient: BedPatient = TelemetryRepository.wardBeds.first { it.bedNumber == 104 },
    val activeFilter: String = "ALL", // "ALL", "CRITICAL", "MODERATE", "STABLE"
    val sortCriteria: SortCriteria = SortCriteria.VELOCITY_DESC,
    val currentUtcTime: String = "14:28:10 UTC",
    val isAudioArmed: Boolean = true,
    val counterfactual: CounterfactualState = CounterfactualState(),
    val showCodeDispatchDialog: Boolean = false,
    val showHotCallDialog: Boolean = false,
    val showStatLabSuccess: Boolean = false,
    val activeAlertCount: Int = 5,
    val bedsCount: String = "12/12",
    val meanLeadTimeHours: String = "3.8",
    val modelConfidenceRoc: String = "99.4%"
)

class TelemetryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TelemetryUiState())
    val uiState: StateFlow<TelemetryUiState> = _uiState.asStateFlow()

    init {
        startUtcClockTicker()
    }

    private fun startUtcClockTicker() {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("HH:mm:ss 'UTC'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            while (true) {
                _uiState.value = _uiState.value.copy(
                    currentUtcTime = formatter.format(Date())
                )
                delay(1000)
            }
        }
    }

    fun navigateTo(destination: ScreenDestination) {
        _uiState.value = _uiState.value.copy(currentScreen = destination)
    }

    fun selectPatient(patient: BedPatient) {
        _uiState.value = _uiState.value.copy(
            selectedPatient = patient,
            currentScreen = ScreenDestination.PATIENT_DETAIL
        )
    }

    fun selectPatientByBedNumber(bedNumber: Int) {
        val patient = TelemetryRepository.wardBeds.firstOrNull { it.bedNumber == bedNumber }
            ?: TelemetryRepository.wardBeds.first()
        selectPatient(patient)
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(activeFilter = filter)
    }

    fun setSort(criteria: SortCriteria) {
        _uiState.value = _uiState.value.copy(sortCriteria = criteria)
    }

    fun toggleAudio() {
        _uiState.value = _uiState.value.copy(isAudioArmed = !_uiState.value.isAudioArmed)
    }

    // Counterfactual actions
    fun setO2Step(step: Int) {
        _uiState.value = _uiState.value.copy(
            counterfactual = _uiState.value.counterfactual.copy(o2Step = step.coerceIn(0, 3))
        )
    }

    fun setFluidMl(ml: Int) {
        _uiState.value = _uiState.value.copy(
            counterfactual = _uiState.value.counterfactual.copy(fluidMl = ml.coerceIn(0, 2000))
        )
    }

    fun toggleVaso(active: Boolean) {
        _uiState.value = _uiState.value.copy(
            counterfactual = _uiState.value.counterfactual.copy(vasoActive = active)
        )
    }

    fun applySimulationPlan() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                counterfactual = _uiState.value.counterfactual.copy(isDispatched = true)
            )
            delay(2800)
            _uiState.value = _uiState.value.copy(
                counterfactual = _uiState.value.counterfactual.copy(isDispatched = false)
            )
        }
    }

    fun triggerCodeDispatch(show: Boolean) {
        _uiState.value = _uiState.value.copy(showCodeDispatchDialog = show)
    }

    fun triggerHotCall(show: Boolean) {
        _uiState.value = _uiState.value.copy(showHotCallDialog = show)
    }

    fun triggerStatLabOrder() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(showStatLabSuccess = true)
            delay(2500)
            _uiState.value = _uiState.value.copy(showStatLabSuccess = false)
        }
    }

    fun getFilteredAndSortedBeds(): List<BedPatient> {
        val list = TelemetryRepository.wardBeds.filter { patient ->
            when (_uiState.value.activeFilter) {
                "CRITICAL" -> patient.acuity == AcuityLevel.CRITICAL
                "MODERATE" -> patient.acuity == AcuityLevel.MODERATE
                "STABLE" -> patient.acuity == AcuityLevel.STABLE
                else -> true
            }
        }

        return when (_uiState.value.sortCriteria) {
            SortCriteria.VELOCITY_DESC -> list.sortedByDescending { it.velocity }
            SortCriteria.SCORE_DESC -> list.sortedByDescending { it.riskScore }
            SortCriteria.BED_ASC -> list.sortedBy { it.bedNumber }
        }
    }
}
