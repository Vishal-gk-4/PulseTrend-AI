package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SsidChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TelemetryRepository
import com.example.model.BadgeType
import com.example.ui.components.TrajectoryChartCard
import com.example.ui.components.TrajectoryChartType
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.PrimaryCyanContainer
import com.example.ui.theme.PrimaryCyanFixed
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SecondaryCriticalContainer
import com.example.ui.theme.SecondaryCriticalFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.TertiaryAmberFixed
import com.example.ui.theme.Typography
import com.example.ui.theme.VitalDisplayLg
import com.example.ui.theme.VitalDisplayMd
import com.example.ui.theme.VitalDisplaySm
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TelemetryUiState
import com.example.viewmodel.TelemetryViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PatientDetailScreen(
    viewModel: TelemetryViewModel,
    uiState: TelemetryUiState,
    modifier: Modifier = Modifier
) {
    val patient = uiState.selectedPatient
    val cf = uiState.counterfactual
    val scrollState = rememberScrollState()

    val infiniteTransition = rememberInfiniteTransition(label = "patient_alert")
    val alertPing by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "patient_alert_ping"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
            .verticalScroll(scrollState)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // PATIENT HEADER BAR (Translucent Glassmorphism Panel)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, SecondaryCriticalContainer.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row 1: Identification & Code Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainerHighest)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = patient.bedId,
                            style = VitalDisplaySm.copy(fontSize = 15.sp),
                            color = PrimaryCyan
                        )
                    }

                    Text(
                        text = "${patient.name}, ${patient.ageSex}",
                        style = Typography.headlineMedium,
                        color = OnSurface
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "MRN ${patient.mrn}",
                            style = TelemetryUnitStyle,
                            color = OnSurfaceVariant
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SecondaryCriticalContainer.copy(alpha = 0.3f))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = patient.icuDay,
                            style = Typography.labelSmall.copy(fontSize = 10.sp),
                            color = SecondaryCritical
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(PrimaryCyanContainer)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = patient.codeStatus,
                            style = Typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                            color = OnPrimary
                        )
                    }
                }

                // Action Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.triggerHotCall(true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryCritical,
                            contentColor = OnSecondary
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("alert_rapid_response_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Text(
                                text = "ALERT RAPID RESPONSE (STAT)",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp)
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.navigateTo(ScreenDestination.SBAR_HANDOFF) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryCyan,
                            contentColor = OnPrimary
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("sbar_handoff_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(16.dp))
                            Text(
                                text = "SBAR HANDOFF",
                                style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp)
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.triggerStatLabOrder() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerHigh,
                            contentColor = OnSurface
                        ),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("order_stat_labs_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Biotech, contentDescription = null, tint = TertiaryAmber, modifier = Modifier.size(16.dp))
                            Text(
                                text = "ORDER STAT LABS",
                                style = Typography.labelSmall.copy(fontSize = 10.sp)
                            )
                        }
                    }
                }
            }

            // Stat Lab Success Toast Banner
            AnimatedVisibility(visible = uiState.showStatLabSuccess) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryCyanContainer)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(16.dp))
                    Text(
                        text = "Stat Labs Dispatched to LIS: Repeat Lactate, ABG, CBC, BMP queued for Bed 104.",
                        style = Typography.bodySmall,
                        color = OnPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Row 2: Sub-metadata & Circular Risk Arc Gauge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Dx: ${patient.fullDiagnosis}",
                        style = Typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Attending: ${patient.attending}",
                            style = Typography.bodySmall,
                            color = OnSurface
                        )
                        Text(text = "•", color = OnSurfaceVariant)
                        Text(
                            text = patient.monitorSource,
                            style = TelemetryUnitStyle,
                            color = PrimaryCyan
                        )
                    }
                }

                // Circular Risk Arc Gauge Container
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.size(54.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = SurfaceVariant,
                                radius = size.minDimension / 2.3f,
                                style = Stroke(width = 4.dp.toPx())
                            )
                            // Draw risk arc (82%)
                            drawArc(
                                color = SecondaryCritical,
                                startAngle = -90f,
                                sweepAngle = 360f * (patient.riskScore / 100f),
                                useCenter = false,
                                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${patient.riskScore}",
                                style = VitalDisplayMd.copy(fontSize = 20.sp),
                                color = SecondaryCritical
                            )
                            Text(
                                text = "/100",
                                style = TelemetryUnitStyle.copy(fontSize = 7.5.sp),
                                color = OnSurfaceVariant
                            )
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .scale(alertPing)
                                    .clip(CircleShape)
                                    .background(SecondaryCritical)
                            )
                            Text(
                                text = "CRITICAL DETERIORATION RISK",
                                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = SecondaryCritical
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = SecondaryCritical,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "+18 pts in past 90 mins",
                                style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                                color = SecondaryCritical
                            )
                            Text(
                                text = "| Horizon: 60m to Arrest",
                                style = TelemetryUnitStyle,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // MAIN 2-COLUMN ICU COCKPIT LAYOUT
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isWide = maxWidth >= 900.dp

            if (isWide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // LEFT COLUMN (60%): PHYSIOLOGIC TRAJECTORY STREAM
                    Column(
                        modifier = Modifier.weight(0.60f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TrajectoryStreamSection()
                    }

                    // RIGHT COLUMN (40%): XAI ATTRIBUTION & COUNTERFACTUAL SIMULATOR
                    Column(
                        modifier = Modifier.weight(0.40f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        XaiAttributionSection()
                        CounterfactualSimulatorSection(viewModel = viewModel, cf = cf)
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    TrajectoryStreamSection()
                    XaiAttributionSection()
                    CounterfactualSimulatorSection(viewModel = viewModel, cf = cf)
                }
            }
        }

        // TELEMETRY EVENT LOG & STAT MEDICATION TIMELINE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = PrimaryCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Telemetry Event Log & Stat Medication Timeline",
                        style = Typography.headlineSmall,
                        color = OnSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Bed 104 EMR Integration: ACTIVE",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = "• 0.2s Stream Sync",
                        style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryCyan
                    )
                }
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (event in TelemetryRepository.timelineEventsBed104) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column {
                            Text(
                                text = event.timeUtc,
                                style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                                color = OnSurfaceVariant
                            )
                            Text(
                                text = event.title,
                                style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = OnSurface
                            )
                        }

                        val badgeBg = when (event.badgeType) {
                            BadgeType.ALERT -> SecondaryCriticalContainer.copy(alpha = 0.35f)
                            BadgeType.INFO -> PrimaryCyanContainer.copy(alpha = 0.3f)
                            BadgeType.WARNING -> TertiaryAmber.copy(alpha = 0.2f)
                            BadgeType.NORMAL -> SurfaceContainerHigh
                        }
                        val badgeColor = when (event.badgeType) {
                            BadgeType.ALERT -> SecondaryCriticalFixed
                            BadgeType.INFO -> PrimaryCyan
                            BadgeType.WARNING -> TertiaryAmberFixed
                            BadgeType.NORMAL -> OnSurfaceVariant
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(badgeBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = event.badge,
                                style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                                color = badgeColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrajectoryStreamSection() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Master Control Ribbon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.SsidChart, contentDescription = null, tint = PrimaryCyan)
                Column {
                    Text(text = "Physiologic Trajectory Stream", style = Typography.headlineSmall, color = OnSurface)
                    Text(
                        text = "MULTIMODAL BIOSIGNAL SYNTHESIS (ECG + PPG + ARTERIAL TRACE)",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = OnSurfaceVariant
                    )
                }
            }

            // Legend
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerLowest)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(modifier = Modifier.size(10.dp, 2.dp).background(PrimaryCyan))
                Text(text = "4h Observation", style = TelemetryUnitStyle, color = OnSurface)
                Text(text = "|", color = SurfaceVariant)
                Box(modifier = Modifier.size(12.dp, 2.dp).background(SecondaryCritical))
                Text(text = "+1h AI Trajectory (95% CI)", style = TelemetryUnitStyle, color = SecondaryCritical)
            }
        }

        // Charts
        TrajectoryChartCard(chartType = TrajectoryChartType.HEART_RATE)
        TrajectoryChartCard(chartType = TrajectoryChartType.SPO2)
        TrajectoryChartCard(chartType = TrajectoryChartType.ARTERIAL_PRESSURE)
    }
}

@Composable
private fun XaiAttributionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = TertiaryAmber)
                Column {
                    Text(text = "Predictive Feature Attribution", style = Typography.headlineSmall, color = OnSurface)
                    Text(
                        text = "SHAP FACTOR CONTRIBUTION MATRIX (SCORE: 82)",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = OnSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerHighest)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(text = "v4.2 PROBABILISTIC", style = TelemetryUnitStyle, color = PrimaryCyan)
            }
        }

        // SHAP Factors
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (feature in TelemetryRepository.shapFeaturesBed104) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "${feature.rank}. ${feature.name}",
                                style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                color = OnSurface
                            )
                            Text(
                                text = feature.detail,
                                style = TelemetryUnitStyle,
                                color = if (feature.impactPct >= 30) SecondaryCritical else TertiaryAmber
                            )
                        }

                        Text(
                            text = "+${feature.impactPct}%",
                            style = VitalDisplaySm.copy(fontSize = 13.sp),
                            color = if (feature.impactPct >= 30) SecondaryCritical else TertiaryAmber
                        )
                    }

                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(SurfaceContainer)
                    ) {
                        val barColor = when {
                            feature.impactPct >= 35 -> SecondaryCritical
                            feature.impactPct >= 30 -> SecondaryCriticalContainer
                            feature.impactPct >= 15 -> TertiaryAmber
                            else -> PrimaryCyan
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(feature.impactPct / 100f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(barColor)
                        )
                    }
                }
            }
        }

        // Diagnostic Phenotype Correlation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerLowest)
                .border(1.dp, SecondaryCritical.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = SecondaryCritical,
                modifier = Modifier.size(18.dp).padding(top = 2.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "Diagnostic Phenotype Correlation",
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = OnSurface
                )
                Text(
                    text = "Composite trajectory pattern indicates hyperdynamic septic shock with incipient cardiorespiratory exhaustion. Probability of sustained hemodynamic collapse within 45 minutes without volume or inotrope therapy is 91.4%.",
                    style = Typography.bodySmall,
                    color = OnSurfaceVariant,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
private fun CounterfactualSimulatorSection(
    viewModel: TelemetryViewModel,
    cf: com.example.model.CounterfactualState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Science, contentDescription = null, tint = PrimaryCyan)
                Column {
                    Text(text = "Counterfactual Simulator", style = Typography.headlineSmall, color = OnSurface)
                    Text(
                        text = "DIGITAL TWIN LIVE INTERVENTION PREVIEW",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = OnSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(PrimaryCyan.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(text = "PREVIEW ACTIVE", style = TelemetryUnitStyle, color = PrimaryCyan)
            }
        }

        // Simulator Controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Control 1: O2 Modality Slider
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Respiratory Support (O2 Modality)", style = Typography.bodySmall, color = OnSurface)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = cf.o2Label, style = TelemetryUnitStyle, color = PrimaryCyan)
                    }
                }

                Slider(
                    value = cf.o2Step.toFloat(),
                    onValueChange = { viewModel.setO2Step(it.toInt()) },
                    valueRange = 0f..3f,
                    steps = 2,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryCyan,
                        activeTrackColor = PrimaryCyan,
                        inactiveTrackColor = SurfaceVariant
                    ),
                    modifier = Modifier.testTag("o2_modality_slider")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Room Air", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "2L NC", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "HFNC 40L/60%", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = PrimaryCyan)
                    Text(text = "NIV / ETT", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                }
            }

            // Control 2: Crystalloid Fluid Resuscitation Bolus
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "IV Crystalloid Resuscitation", style = Typography.bodySmall, color = OnSurface)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = cf.fluidLabel, style = TelemetryUnitStyle, color = PrimaryCyan)
                    }
                }

                Slider(
                    value = cf.fluidMl.toFloat(),
                    onValueChange = { viewModel.setFluidMl(it.toInt()) },
                    valueRange = 0f..2000f,
                    steps = 7,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryCyan,
                        activeTrackColor = PrimaryCyan,
                        inactiveTrackColor = SurfaceVariant
                    ),
                    modifier = Modifier.testTag("fluid_bolus_slider")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0 mL", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "500 mL", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "1,000 mL", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "1,500 mL", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = PrimaryCyan)
                    Text(text = "2,000 mL", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                }
            }

            // Control 3: Early Norepinephrine Infusion Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Early Norepinephrine Infusion", style = Typography.bodySmall, color = OnSurface)
                    Text(text = "0.05 mcg/kg/min Peripheral Bridge", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                }

                Switch(
                    checked = cf.vasoActive,
                    onCheckedChange = { viewModel.toggleVaso(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = OnPrimary,
                        checkedTrackColor = PrimaryCyan,
                        uncheckedThumbColor = OnSurfaceVariant,
                        uncheckedTrackColor = SurfaceContainerHigh
                    ),
                    modifier = Modifier.testTag("vasopressor_switch")
                )
            }
        }

        // Live Simulated Trajectory Outcome Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerHigh)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SIMULATED TRAJECTORY OUTCOME",
                    style = Typography.labelSmall.copy(fontSize = 9.sp),
                    color = OnSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.AutoFixHigh, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(13.dp))
                    Text(text = "COUNTERFACTUAL MODEL 98.2% CONV", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = PrimaryCyan)
                }
            }

            // Risk Shift
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerLowest)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "CURRENT STATUS", style = TelemetryUnitStyle.copy(fontSize = 8.5.sp), color = OnSurfaceVariant)
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "82", style = VitalDisplayMd, color = SecondaryCritical)
                        Text(text = "CRITICAL", style = Typography.labelSmall, color = SecondaryCritical)
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = PrimaryCyan,
                    modifier = Modifier.size(24.dp)
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "PROJECTED WITH PLAN", style = TelemetryUnitStyle.copy(fontSize = 8.5.sp), color = PrimaryCyan)
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "${cf.projectedRisk}", style = VitalDisplayMd, color = PrimaryCyan)
                        Text(text = cf.projectedStatus, style = Typography.labelSmall, color = PrimaryCyan)
                    }
                }
            }

            // Projected Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // HR
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerLowest)
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "HR PROJECTION", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "128 → ${cf.projectedHr}", style = VitalDisplaySm.copy(fontSize = 13.sp), color = OnSurface)
                    val hrDelta = cf.projectedHr - 128
                    Text(
                        text = "$hrDelta bpm",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = if (hrDelta <= 0) PrimaryCyan else SecondaryCritical
                    )
                }

                // MAP
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerLowest)
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "MAP PROJECTION", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "65 → ${cf.projectedMap}", style = VitalDisplaySm.copy(fontSize = 13.sp), color = OnSurface)
                    val mapDelta = cf.projectedMap - 65
                    val sign = if (mapDelta >= 0) "+" else ""
                    Text(
                        text = "$sign$mapDelta mmHg",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = if (cf.projectedMap >= 65) PrimaryCyan else SecondaryCritical
                    )
                }

                // SpO2
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerLowest)
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "SPO2 PROJECTION", style = TelemetryUnitStyle.copy(fontSize = 8.sp), color = OnSurfaceVariant)
                    Text(text = "88 → ${cf.projectedSpo2}%", style = VitalDisplaySm.copy(fontSize = 13.sp), color = OnSurface)
                    val spo2Delta = cf.projectedSpo2 - 88
                    val sign = if (spo2Delta >= 0) "+" else ""
                    Text(
                        text = "$sign$spo2Delta% sat",
                        style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                        color = if (cf.projectedSpo2 >= 92) PrimaryCyan else SecondaryCritical
                    )
                }
            }

            // Apply Simulation Button
            Button(
                onClick = { viewModel.applySimulationPlan() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (cf.isDispatched) PrimaryCyanContainer else PrimaryCyan,
                    contentColor = if (cf.isDispatched) OnPrimaryContainer else OnPrimary
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .testTag("apply_simulation_plan_btn")
            ) {
                if (cf.isDispatched) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "ORDERS DISPATCHED TO CPOE (STAT RESUSCITATION)",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.5.sp)
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "APPLY SIMULATION PLAN TO ORDERS",
                            style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.5.sp)
                        )
                    }
                }
            }
        }
    }
}
