package com.example.data

import com.example.model.AcuityLevel
import com.example.model.BadgeType
import com.example.model.BedPatient
import com.example.model.EcgRhythm
import com.example.model.ShapFeature
import com.example.model.TimelineEvent
import com.example.model.Vitals

object TelemetryRepository {

    val wardBeds: List<BedPatient> = listOf(
        // Bed 104 (CRITICAL)
        BedPatient(
            bedId = "BED 104",
            bedNumber = 104,
            mrn = "#MRN-84029",
            name = "Elena Rostova",
            ageSex = "64F",
            diagnosis = "Sepsis / Pneumonia",
            fullDiagnosis = "Severe Sepsis sec. to Community-Acquired Pneumonia",
            riskScore = 82,
            acuity = AcuityLevel.CRITICAL,
            velocity = 3.4,
            velocityLabel = "ACCELERATING",
            vitals = Vitals(
                hr = 128,
                hrTrend = "bpm ↑",
                spo2 = 88,
                spo2Type = "hypox ↓",
                sbp = 88,
                dbp = 54,
                map = 65,
                rr = 32,
                rrTrend = "/min ↑",
                tempC = 38.9f
            ),
            ecg = EcgRhythm("ECG LEAD II", "ST +1.8mm", isCritical = true),
            aiReasoning = "HR velocity +35%/hr & SpO2 drop -5% over past 45m. High risk of septic shock decompensation.",
            attending = "Dr. M. Vance, MD",
            actionText = "Review Trajectory",
            subActionText = "Attending: Dr. Vance",
            icuDay = "ICU Day 2",
            codeStatus = "FULL CODE"
        ),
        // Bed 108 (CRITICAL)
        BedPatient(
            bedId = "BED 108",
            bedNumber = 108,
            mrn = "#MRN-41902",
            name = "Arthur Pendelton",
            ageSex = "71M",
            diagnosis = "Acute Coronary Syndrome",
            fullDiagnosis = "NSTEMI / Acute Coronary Syndrome with hemodynamic instability",
            riskScore = 78,
            acuity = AcuityLevel.CRITICAL,
            velocity = 2.8,
            velocityLabel = "ESCALATING",
            vitals = Vitals(
                hr = 114,
                hrTrend = "bpm ↑",
                spo2 = 91,
                spo2Type = "low",
                sbp = 178,
                dbp = 102,
                map = 127,
                rr = 26,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("V5 RHYTHM", "ST Dep -2.1", isCritical = true),
            aiReasoning = "Persistent ST depression trajectory + rising troponin delta projected. Cardiac output compromise.",
            attending = "Dr. M. Vance, MD",
            actionText = "Escalate ACS",
            subActionText = "Attending: Dr. Vance"
        ),
        // Bed 102 (MODERATE)
        BedPatient(
            bedId = "BED 102",
            bedNumber = 102,
            mrn = "#MRN-63110",
            name = "Marcus Sterling",
            ageSex = "58M",
            diagnosis = "Post-Op CABG (POD 1)",
            fullDiagnosis = "Status Post Coronary Artery Bypass Graft x3 (POD 1)",
            riskScore = 64,
            acuity = AcuityLevel.MODERATE,
            velocity = 1.6,
            velocityLabel = "WARMING",
            vitals = Vitals(
                hr = 98,
                hrTrend = "bpm",
                spo2 = 94,
                spo2Type = "vent",
                sbp = 105,
                dbp = 68,
                map = 80,
                rr = 22,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Normal Sinus", isCritical = false),
            aiReasoning = "Early trending metabolic acidosis, lactate upward trend detected (+0.4 mmol/h).",
            attending = "Dr. M. Vance, MD",
            actionText = "Order ABG",
            subActionText = "Telemetry Active"
        ),
        // Bed 107 (MODERATE)
        BedPatient(
            bedId = "BED 107",
            bedNumber = 107,
            mrn = "#MRN-77319",
            name = "Sarah Jenkins",
            ageSex = "49F",
            diagnosis = "ARDS (Moderate)",
            fullDiagnosis = "Acute Respiratory Distress Syndrome secondary to viral pneumonitis",
            riskScore = 58,
            acuity = AcuityLevel.MODERATE,
            velocity = 1.2,
            velocityLabel = "UNFAVORABLE",
            vitals = Vitals(
                hr = 92,
                hrTrend = "bpm",
                spo2 = 93,
                spo2Type = "FiO2 60%",
                sbp = 118,
                dbp = 74,
                map = 88,
                rr = 24,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Sinus Tach", isCritical = false),
            aiReasoning = "Compliance worsening; PaO2/FiO2 ratio trending downwards to 164. PEEP reassessment advised.",
            attending = "Dr. M. Vance, MD",
            actionText = "Vent Parameters",
            subActionText = "BiPAP Support"
        ),
        // Bed 111 (MODERATE)
        BedPatient(
            bedId = "BED 111",
            bedNumber = 111,
            mrn = "#MRN-90214",
            name = "David K. Morales",
            ageSex = "62M",
            diagnosis = "Diabetic Ketoacidosis",
            fullDiagnosis = "Severe DKA with hyperkalemia, active insulin infusion",
            riskScore = 52,
            acuity = AcuityLevel.MODERATE,
            velocity = 0.5,
            velocityLabel = "PLATEAU",
            vitals = Vitals(
                hr = 90,
                hrTrend = "bpm",
                spo2 = 96,
                spo2Type = "RA",
                sbp = 122,
                dbp = 78,
                map = 92,
                rr = 20,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Tall T-waves resolved", isCritical = false),
            aiReasoning = "Insulin response plateaued, anion gap monitoring active (Current AG: 16).",
            attending = "Dr. M. Vance, MD",
            actionText = "Titration Log",
            subActionText = "Drip: 6 U/hr"
        ),
        // Bed 101 (STABLE)
        BedPatient(
            bedId = "BED 101",
            bedNumber = 101,
            mrn = "#MRN-18239",
            name = "Clara Oswald",
            ageSex = "38F",
            diagnosis = "Polytrauma (Post-Op Day 3)",
            fullDiagnosis = "Multiple pelvic fractures and splenic laceration s/p embolization",
            riskScore = 24,
            acuity = AcuityLevel.STABLE,
            velocity = -0.8,
            velocityLabel = "IMPROVING",
            vitals = Vitals(
                hr = 74,
                hrTrend = "bpm",
                spo2 = 99,
                spo2Type = "2L NC",
                sbp = 120,
                dbp = 80,
                map = 93,
                rr = 16,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "NSR", isCritical = false),
            aiReasoning = "Hemodynamics stabilized post-fluid resuscitation. Pain scale down to 2/10.",
            attending = "Dr. M. Vance, MD",
            actionText = "View Chart",
            subActionText = "Step-down candidate"
        ),
        // Bed 103 (STABLE)
        BedPatient(
            bedId = "BED 103",
            bedNumber = 103,
            mrn = "#MRN-55910",
            name = "Robert Chen",
            ageSex = "55M",
            diagnosis = "Acute Pancreatitis",
            fullDiagnosis = "Gallstone induced pancreatitis, improving biliary dilation",
            riskScore = 28,
            acuity = AcuityLevel.STABLE,
            velocity = 0.0,
            velocityLabel = "STEADY",
            vitals = Vitals(
                hr = 78,
                hrTrend = "bpm",
                spo2 = 98,
                spo2Type = "RA",
                sbp = 126,
                dbp = 82,
                map = 96,
                rr = 18,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "NSR", isCritical = false),
            aiReasoning = "Inflammatory markers down trending; pain controlled, enteral nutrition initiated.",
            attending = "Dr. M. Vance, MD",
            actionText = "View Chart",
            subActionText = "IV Fluids: LR 100cc/h"
        ),
        // Bed 105 (STABLE)
        BedPatient(
            bedId = "BED 105",
            bedNumber = 105,
            mrn = "#MRN-33129",
            name = "Miriam Al-Mansoor",
            ageSex = "67F",
            diagnosis = "Stroke Watch (tPA +36h)",
            fullDiagnosis = "Acute ischemic stroke s/p IV thrombolysis, NIHSS down to 2",
            riskScore = 19,
            acuity = AcuityLevel.STABLE,
            velocity = -0.4,
            velocityLabel = "STABLE",
            vitals = Vitals(
                hr = 72,
                hrTrend = "bpm",
                spo2 = 98,
                spo2Type = "RA",
                sbp = 130,
                dbp = 84,
                map = 99,
                rr = 16,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Normal Sinus", isCritical = false),
            aiReasoning = "Neurological status baseline, MAP within target window (<140 mmHg). NIHSS score 2.",
            attending = "Dr. M. Vance, MD",
            actionText = "Neuro Chart",
            subActionText = "Neuro check q2h"
        ),
        // Bed 106 (STABLE)
        BedPatient(
            bedId = "BED 106",
            bedNumber = 106,
            mrn = "#MRN-88204",
            name = "Henry Wu",
            ageSex = "74M",
            diagnosis = "CHF Exacerbation",
            fullDiagnosis = "Decompensated biventricular systolic heart failure",
            riskScore = 32,
            acuity = AcuityLevel.STABLE,
            velocity = 0.1,
            velocityLabel = "STEADY",
            vitals = Vitals(
                hr = 82,
                hrTrend = "bpm",
                spo2 = 95,
                spo2Type = "3L NC",
                sbp = 134,
                dbp = 86,
                map = 102,
                rr = 18,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Atrial Fib controlled", isCritical = false),
            aiReasoning = "Diuresis protocol effective; net -1,800 mL in 24h. Lung bibasilar crackles clearing.",
            attending = "Dr. M. Vance, MD",
            actionText = "View Chart",
            subActionText = "Lasix gtt: 5mg/hr"
        ),
        // Bed 109 (STABLE)
        BedPatient(
            bedId = "BED 109",
            bedNumber = 109,
            mrn = "#MRN-67120",
            name = "Evelyn Carter",
            ageSex = "52F",
            diagnosis = "Post-Thyroidectomy",
            fullDiagnosis = "Total thyroidectomy POD 1, calcium intact",
            riskScore = 15,
            acuity = AcuityLevel.STABLE,
            velocity = -0.6,
            velocityLabel = "STABLE",
            vitals = Vitals(
                hr = 68,
                hrTrend = "bpm",
                spo2 = 100,
                spo2Type = "RA",
                sbp = 118,
                dbp = 76,
                map = 90,
                rr = 14,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "NSR", isCritical = false),
            aiReasoning = "Normal ionized calcium, minimal drain output (15 mL serosanguinous), airway widely patent.",
            attending = "Dr. M. Vance, MD",
            actionText = "View Chart",
            subActionText = "Post-op POD 1"
        ),
        // Bed 110 (STABLE)
        BedPatient(
            bedId = "BED 110",
            bedNumber = 110,
            mrn = "#MRN-44910",
            name = "Samuel O'Connor",
            ageSex = "63M",
            diagnosis = "GI Bleed Resolved",
            fullDiagnosis = "Upper gastrointestinal hemorrhage s/p endoscopic clipping",
            riskScore = 22,
            acuity = AcuityLevel.STABLE,
            velocity = -0.3,
            velocityLabel = "STABLE",
            vitals = Vitals(
                hr = 76,
                hrTrend = "bpm",
                spo2 = 97,
                spo2Type = "RA",
                sbp = 124,
                dbp = 80,
                map = 94,
                rr = 16,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "NSR", isCritical = false),
            aiReasoning = "Hemoglobin stable at 10.4 over 12h, no active bleeding. Stepdown ward eligible.",
            attending = "Dr. M. Vance, MD",
            actionText = "View Chart",
            subActionText = "Hgb 10.4 g/dL"
        ),
        // Bed 112 (STABLE)
        BedPatient(
            bedId = "BED 112",
            bedNumber = 112,
            mrn = "#MRN-19043",
            name = "Vivian Vance",
            ageSex = "45F",
            diagnosis = "S/P Appendectomy",
            fullDiagnosis = "Laparoscopic appendectomy POD 2, ready for discharge",
            riskScore = 12,
            acuity = AcuityLevel.STABLE,
            velocity = -0.9,
            velocityLabel = "DISCHARGE PENDING",
            vitals = Vitals(
                hr = 66,
                hrTrend = "bpm",
                spo2 = 99,
                spo2Type = "RA",
                sbp = 115,
                dbp = 72,
                map = 86,
                rr = 14,
                rrTrend = "/min"
            ),
            ecg = EcgRhythm("ECG LEAD II", "Normal Sinus", isCritical = false),
            aiReasoning = "Afebrile x48h, ambulating independently, scheduled for floor transfer at 16:00.",
            attending = "Dr. M. Vance, MD",
            actionText = "Discharge Handoff",
            subActionText = "Transfer Order Out"
        )
    )

    val shapFeaturesBed104: List<ShapFeature> = listOf(
        ShapFeature(
            rank = 1,
            name = "Heart Rate Acceleration Velocity",
            detail = "(+46 bpm / 4h)",
            impactPct = 38,
            level = AcuityLevel.CRITICAL
        ),
        ShapFeature(
            rank = 2,
            name = "SpO2 Desaturation Slope",
            detail = "(-2.1%/hr descent)",
            impactPct = 31,
            level = AcuityLevel.CRITICAL
        ),
        ShapFeature(
            rank = 3,
            name = "Shock Index Elevation",
            detail = "(HR 128 / SBP 88 = 1.45)",
            impactPct = 18,
            level = AcuityLevel.MODERATE
        ),
        ShapFeature(
            rank = 4,
            name = "Respiratory Rate Elevation",
            detail = "(32 / min tachypnea)",
            impactPct = 9,
            level = AcuityLevel.MODERATE
        ),
        ShapFeature(
            rank = 5,
            name = "Core Temperature Spike",
            detail = "(38.9°C febrile storm)",
            impactPct = 4,
            level = AcuityLevel.STABLE
        )
    )

    val timelineEventsBed104: List<TimelineEvent> = listOf(
        TimelineEvent(
            timeUtc = "14:15 UTC",
            title = "Lactate Drawn (Stat)",
            badge = "4.2 mmol/L",
            badgeType = BadgeType.ALERT
        ),
        TimelineEvent(
            timeUtc = "13:50 UTC",
            title = "Ceftriaxone 2g IVPB",
            badge = "INFUSING",
            badgeType = BadgeType.INFO
        ),
        TimelineEvent(
            timeUtc = "13:30 UTC",
            title = "Arterial Line Placed",
            badge = "Radial Left",
            badgeType = BadgeType.NORMAL
        ),
        TimelineEvent(
            timeUtc = "12:00 UTC",
            title = "Admission Chest CT",
            badge = "Bilateral Consolidations",
            badgeType = BadgeType.WARNING
        )
    )
}
