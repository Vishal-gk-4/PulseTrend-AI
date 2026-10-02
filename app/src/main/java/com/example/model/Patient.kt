package com.example.model

enum class AcuityLevel(val displayName: String) {
    CRITICAL("Critical"),
    MODERATE("Moderate"),
    STABLE("Stable")
}

data class Vitals(
    val hr: Int,
    val hrUnit: String = "bpm",
    val hrTrend: String = "",
    val spo2: Int,
    val spo2Unit: String = "%",
    val spo2Type: String = "RA",
    val sbp: Int,
    val dbp: Int,
    val map: Int,
    val rr: Int,
    val rrTrend: String = "/min",
    val tempC: Float = 37.0f
)

data class EcgRhythm(
    val lead: String,
    val stSegment: String,
    val isCritical: Boolean = false
)

data class BedPatient(
    val bedId: String,
    val bedNumber: Int,
    val mrn: String,
    val name: String,
    val ageSex: String,
    val diagnosis: String,
    val fullDiagnosis: String = diagnosis,
    val riskScore: Int,
    val acuity: AcuityLevel,
    val velocity: Double,
    val velocityLabel: String,
    val vitals: Vitals,
    val ecg: EcgRhythm,
    val aiReasoning: String,
    val attending: String = "Dr. M. Vance, MD",
    val actionText: String = "View Chart",
    val subActionText: String = "Attending: Dr. Vance",
    val icuDay: String = "ICU Day 2",
    val codeStatus: String = "FULL CODE",
    val monitorSource: String = "PHILIPS INTELLIVUE X3 [SYNCED]"
)

data class TimelineEvent(
    val timeUtc: String,
    val title: String,
    val badge: String,
    val badgeType: BadgeType
)

enum class BadgeType {
    ALERT,
    INFO,
    NORMAL,
    WARNING
}

data class ShapFeature(
    val rank: Int,
    val name: String,
    val detail: String,
    val impactPct: Int,
    val level: AcuityLevel
)

data class CounterfactualState(
    val o2Step: Int = 2, // 0: Room Air, 1: 2L NC, 2: HFNC 40L @ 60%, 3: NIV / ETT
    val fluidMl: Int = 1500,
    val vasoActive: Boolean = true,
    val isDispatched: Boolean = false
) {
    val o2Label: String
        get() = when (o2Step) {
            0 -> "Room Air (21%)"
            1 -> "2L Nasal Cannula (28%)"
            2 -> "HFNC 40L @ 60% FiO2"
            else -> "NIV Support (12/6 cmH2O)"
        }

    val fluidLabel: String
        get() = "$fluidMl mL LR Bolus"

    // Calculates real-time trajectory simulation
    val projectedRisk: Int
        get() {
            var score = 82
            when (o2Step) {
                0 -> score += 6
                1 -> score -= 2
                2 -> score -= 16
                3 -> score -= 20
            }
            val fluidRed = fluidMl / 100
            score -= fluidRed
            if (vasoActive) score -= 18
            return score.coerceIn(22, 94)
        }

    val projectedHr: Int
        get() {
            var hr = 128
            when (o2Step) {
                2 -> hr -= 12
                3 -> hr -= 16
            }
            hr -= (fluidMl / 200)
            if (vasoActive) hr -= 8
            return hr.coerceIn(76, 138)
        }

    val projectedMap: Int
        get() {
            var map = 65
            map += (fluidMl / 250) * 2
            if (vasoActive) map += 12
            return map.coerceIn(52, 88)
        }

    val projectedSpo2: Int
        get() {
            var s = 88
            when (o2Step) {
                0 -> s = 84
                1 -> s = 89
                2 -> s = 96
                3 -> s = 98
            }
            return s.coerceIn(80, 99)
        }

    val projectedStatus: String
        get() = when {
            projectedRisk > 70 -> "CRITICAL"
            projectedRisk > 45 -> "ELEVATED"
            else -> "CONTROLLED"
        }
}
