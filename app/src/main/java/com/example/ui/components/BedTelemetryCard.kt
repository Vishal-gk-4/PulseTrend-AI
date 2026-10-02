package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AcuityLevel
import com.example.model.BedPatient
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.PrimaryCyanContainer
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SecondaryCriticalContainer
import com.example.ui.theme.SecondaryCriticalFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.TertiaryAmberContainer
import com.example.ui.theme.TertiaryAmberFixed
import com.example.ui.theme.Typography
import com.example.ui.theme.VitalDisplaySm

@Composable
fun BedTelemetryCard(
    patient: BedPatient,
    onCardClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCritical = patient.acuity == AcuityLevel.CRITICAL
    val isModerate = patient.acuity == AcuityLevel.MODERATE

    val infiniteTransition = rememberInfiniteTransition(label = "critical_ping")
    val pingScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ping_scale"
    )

    val borderColor = when {
        isCritical -> SecondaryCriticalContainer.copy(alpha = 0.7f)
        isModerate -> TertiaryAmberContainer.copy(alpha = 0.4f)
        else -> Color.Transparent
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onCardClick() }
            .padding(12.dp)
            .testTag("bed_card_${patient.bedNumber}"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 1. Bed Header Bar: Bed # + MRN + Risk Score Pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = patient.bedId,
                    style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Text(
                    text = patient.mrn,
                    style = TelemetryUnitStyle,
                    color = OnSurfaceVariant
                )
            }

            // Risk Score Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isCritical) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .scale(pingScale)
                            .clip(CircleShape)
                            .background(SecondaryCritical)
                    )
                }

                val pillBg = when {
                    isCritical -> SecondaryCriticalContainer.copy(alpha = 0.5f)
                    isModerate -> TertiaryAmberContainer.copy(alpha = 0.35f)
                    else -> PrimaryCyan.copy(alpha = 0.15f)
                }
                val pillText = when {
                    isCritical -> SecondaryCriticalFixed
                    isModerate -> TertiaryAmberFixed
                    else -> PrimaryCyan
                }
                val arrowSymbol = when {
                    patient.velocity > 2.0 -> "↑↑"
                    patient.velocity > 0.0 -> "↑"
                    patient.velocity < 0.0 -> "↓"
                    else -> "→"
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(pillBg)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${patient.riskScore} $arrowSymbol ${patient.acuity.displayName.uppercase()}",
                        style = Typography.labelSmall.copy(fontSize = 10.sp),
                        color = pillText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 2. Patient Identity & Trajectory Velocity
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = patient.name,
                    style = Typography.headlineSmall,
                    color = OnSurface
                )
                Text(
                    text = "${patient.ageSex} • ${patient.diagnosis}",
                    style = Typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }

            val velocityColor = when {
                patient.velocity >= 2.0 -> SecondaryCriticalFixed
                patient.velocity > 0.0 -> TertiaryAmberFixed
                patient.velocity < 0.0 -> PrimaryCyan
                else -> OnSurfaceVariant
            }

            Column(horizontalAlignment = Alignment.End) {
                val sign = if (patient.velocity > 0) "+" else ""
                Text(
                    text = "$sign${patient.velocity} pts/hr",
                    style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                    color = velocityColor
                )
                Text(
                    text = patient.velocityLabel,
                    style = Typography.labelSmall.copy(fontSize = 9.sp),
                    color = velocityColor
                )
            }
        }

        // 3. Biometric Quad Grid (HR, SpO2, NIBP, RR)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceContainerLowest.copy(alpha = 0.8f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // HR
            VitalBox(
                label = "HR",
                value = "${patient.vitals.hr}",
                status = patient.vitals.hrTrend,
                isCritical = patient.vitals.hr > 110,
                modifier = Modifier.weight(1f)
            )
            // SpO2
            VitalBox(
                label = "SpO2",
                value = "${patient.vitals.spo2}%",
                status = patient.vitals.spo2Type,
                isCritical = patient.vitals.spo2 < 92,
                modifier = Modifier.weight(1f)
            )
            // NIBP
            VitalBox(
                label = "NIBP",
                value = "${patient.vitals.sbp}/${patient.vitals.dbp}",
                status = "MAP ${patient.vitals.map}",
                isCritical = patient.vitals.map < 65 || patient.vitals.map > 120,
                modifier = Modifier.weight(1f)
            )
            // RR
            VitalBox(
                label = "RR",
                value = "${patient.vitals.rr}",
                status = patient.vitals.rrTrend,
                isCritical = patient.vitals.rr > 25,
                modifier = Modifier.weight(1f)
            )
        }

        // 4. Waveform Sparkline
        WaveformSparkline(ecg = patient.ecg)

        // 5. AI Deterioration Reasoning
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceContainerLowest.copy(alpha = 0.6f))
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = if (isCritical) SecondaryCritical else TertiaryAmber,
                modifier = Modifier
                    .size(14.dp)
                    .padding(top = 2.dp)
            )
            Text(
                text = patient.aiReasoning,
                style = Typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 14.sp),
                color = OnSurfaceVariant
            )
        }

        // 6. Action Ribbon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = patient.subActionText,
                style = Typography.labelSmall.copy(fontSize = 10.sp),
                color = OnSurfaceVariant
            )

            if (patient.bedNumber == 104) {
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryCyan,
                        contentColor = OnPrimary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    modifier = Modifier.height(30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "REVIEW TRAJECTORY",
                            style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            } else if (patient.bedNumber == 108) {
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryCritical,
                        contentColor = OnSecondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "ESCALATE ACS",
                        style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    )
                }
            } else {
                Button(
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = OnSurfaceVariant
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = patient.actionText.uppercase(),
                        style = Typography.labelSmall.copy(fontSize = 10.sp)
                    )
                }
            }
        }
    }
}

@Composable
private fun VitalBox(
    label: String,
    value: String,
    status: String,
    isCritical: Boolean,
    modifier: Modifier = Modifier
) {
    val bg = if (isCritical) SecondaryCriticalContainer.copy(alpha = 0.25f) else SurfaceContainer.copy(alpha = 0.6f)
    val textColor = if (isCritical) SecondaryCriticalFixed else OnSurface

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .padding(horizontal = 4.dp, vertical = 3.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = TelemetryUnitStyle.copy(fontSize = 9.sp),
            color = if (isCritical) SecondaryCriticalFixed else OnSurfaceVariant
        )
        Text(
            text = value,
            style = VitalDisplaySm.copy(fontSize = 15.sp),
            color = textColor,
            maxLines = 1
        )
        Text(
            text = status,
            style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
            color = if (isCritical) SecondaryCriticalFixed else OnSurfaceVariant,
            maxLines = 1
        )
    }
}
