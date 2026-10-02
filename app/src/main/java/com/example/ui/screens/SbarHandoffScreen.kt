package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SecondaryCriticalContainer
import com.example.ui.theme.SecondaryCriticalFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.TertiaryAmberContainer
import com.example.ui.theme.TertiaryAmberFixed
import com.example.ui.theme.Typography
import com.example.viewmodel.TelemetryUiState
import com.example.viewmodel.TelemetryViewModel

data class WardAlert(
    val id: String,
    val bed: String,
    val patientName: String,
    val severity: String, // "CRITICAL", "MODERATE", "STABLE"
    val alertText: String,
    val timeAgo: String,
    var isAcknowledged: Boolean = false
)

@Composable
fun SbarHandoffScreen(
    viewModel: TelemetryViewModel,
    uiState: TelemetryUiState,
    modifier: Modifier = Modifier
) {
    var copiedFeedback by remember { mutableStateOf(false) }

    val alerts = remember {
        mutableStateListOf(
            WardAlert(
                id = "1",
                bed = "BED 104",
                patientName = "Elena Rostova",
                severity = "CRITICAL",
                alertText = "Pre-Arrest Deterioration: HR velocity +35%/hr, MAP 65 decompensation trajectory.",
                timeAgo = "1m ago"
            ),
            WardAlert(
                id = "2",
                bed = "BED 108",
                patientName = "Arthur Pendelton",
                severity = "CRITICAL",
                alertText = "Ischemic ST segment depression (-2.1mm) with refractory hypertension (178/102).",
                timeAgo = "4m ago"
            ),
            WardAlert(
                id = "3",
                bed = "BED 102",
                patientName = "Marcus Sterling",
                severity = "MODERATE",
                alertText = "Metabolic acidosis slope: Lactate delta +0.4 mmol/h post-CABG POD 1.",
                timeAgo = "12m ago"
            ),
            WardAlert(
                id = "4",
                bed = "BED 107",
                patientName = "Sarah Jenkins",
                severity = "MODERATE",
                alertText = "PaO2/FiO2 ratio trending downwards to 164. Non-invasive ventilation reassessment advised.",
                timeAgo = "18m ago"
            ),
            WardAlert(
                id = "5",
                bed = "BED 111",
                patientName = "David K. Morales",
                severity = "MODERATE",
                alertText = "DKA insulin drip plateaued, anion gap monitoring active (Current AG: 16).",
                timeAgo = "25m ago"
            )
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .verticalScroll(scrollState)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = PrimaryCyan,
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(text = "SBAR Clinical Handoff & Alert Center", style = Typography.headlineMedium, color = OnSurface)
                    Text(text = "AUTOMATED SHIFT REPORT & REAL-TIME WARD TELEMETRY TRIAGE", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                }
            }

            Button(
                onClick = {
                    copiedFeedback = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryCyan,
                    contentColor = OnPrimary
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.testTag("copy_sbar_report_btn")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = if (copiedFeedback) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (copiedFeedback) "REPORT COPIED" else "COPY SBAR REPORT",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // SBAR Structured Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, SurfaceContainerHigh, RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BED 104 • ELENA ROSTOVA (64F) — SHIFT HANDOFF DOSSIER",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = PrimaryCyan
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerHighest)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = "AI GENERATED • 14:28 UTC", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                }
            }

            // Situation
            SbarSection(
                letter = "S",
                title = "SITUATION",
                body = "Elena Rostova is a 64-year-old female in Bed 104, Day 2 of ICU admission, exhibiting sudden physiologic acceleration towards septic shock. Current Risk Score 82 (+18 pts over 90 mins).",
                accentColor = SecondaryCritical
            )

            // Background
            SbarSection(
                letter = "B",
                title = "BACKGROUND",
                body = "Admitted for severe community-acquired right lower lobe pneumonia with baseline history of hypertension. Received Ceftriaxone 2g at 13:50 UTC, radial arterial line placed at 13:30 UTC. Blood cultures pending x2 sets.",
                accentColor = TertiaryAmber
            )

            // Assessment
            SbarSection(
                letter = "A",
                title = "ASSESSMENT",
                body = "Critical hypoperfusion state. Heart rate 128 bpm (accelerating to 142 bpm projected), SpO2 88% on room air, NIBP 88/54 (MAP 65), RR 32/min, febrile at 38.9°C. Lactate drawn at 14:15 UTC is 4.2 mmol/L. Shock Index 1.45. BioPulse model predicts 91.4% probability of cardiorespiratory exhaustion within 45 minutes.",
                accentColor = SecondaryCritical
            )

            // Recommendation
            SbarSection(
                letter = "R",
                title = "RECOMMENDATION",
                body = "1. Initiate High-Flow Nasal Cannula (HFNC 40L @ 60% FiO2) immediately.\n2. Administer 30 mL/kg IV Crystalloid (1,500 mL Lactated Ringer's) bolus.\n3. Prepare Norepinephrine peripheral bridge at 0.05 mcg/kg/min for MAP maintenance >= 65 mmHg.\n4. Repeat stat arterial blood gas and lactate in 60 minutes.",
                accentColor = PrimaryCyan
            )
        }

        // Active Ward Alert Center
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = SecondaryCritical,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Real-Time Ward Alert Center (${alerts.count { !it.isAcknowledged }} Unread)",
                        style = Typography.titleLarge,
                        color = OnSurface
                    )
                }

                Button(
                    onClick = {
                        alerts.forEachIndexed { i, a ->
                            alerts[i] = a.copy(isAcknowledged = true)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = OnSurface
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(text = "ACKNOWLEDGE ALL", style = Typography.labelSmall)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                alerts.forEachIndexed { index, alert ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (alert.isAcknowledged) SurfaceContainerLowest.copy(alpha = 0.5f) else SurfaceContainerLowest)
                            .border(
                                1.dp,
                                if (alert.severity == "CRITICAL" && !alert.isAcknowledged) SecondaryCriticalContainer else SurfaceContainerHigh,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        if (alert.severity == "CRITICAL") SecondaryCriticalContainer.copy(alpha = 0.5f)
                                        else TertiaryAmberContainer.copy(alpha = 0.3f)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = alert.bed,
                                    style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (alert.severity == "CRITICAL") SecondaryCriticalFixed else TertiaryAmberFixed
                                )
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = alert.patientName,
                                        style = Typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurface
                                    )
                                    Text(
                                        text = "• ${alert.timeAgo}",
                                        style = TelemetryUnitStyle,
                                        color = OnSurfaceVariant
                                    )
                                }
                                Text(
                                    text = alert.alertText,
                                    style = Typography.bodySmall,
                                    color = if (alert.isAcknowledged) OnSurfaceVariant else OnSurface
                                )
                            }
                        }

                        if (!alert.isAcknowledged) {
                            Button(
                                onClick = {
                                    alerts[index] = alert.copy(isAcknowledged = true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceContainerHigh,
                                    contentColor = PrimaryCyan
                                ),
                                shape = RoundedCornerShape(4.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                    horizontal = 8.dp,
                                    vertical = 2.dp
                                ),
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text(text = "ACK", style = Typography.labelSmall.copy(fontSize = 10.sp))
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = PrimaryCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "ACK'D",
                                    style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                                    color = PrimaryCyan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SbarSection(
    letter: String,
    title: String,
    body: String,
    accentColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainerLowest)
            .border(1.dp, SurfaceContainerHigh, RoundedCornerShape(6.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(accentColor.copy(alpha = 0.15f))
                .border(1.dp, accentColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = accentColor
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = title,
                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = accentColor
            )
            Text(
                text = body,
                style = Typography.bodySmall,
                color = OnSurface,
                lineHeight = 16.sp
            )
        }
    }
}
