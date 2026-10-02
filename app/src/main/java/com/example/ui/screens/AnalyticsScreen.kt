package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.PrimaryCyanContainer
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.Typography
import com.example.ui.theme.VitalDisplayMd
import com.example.ui.theme.VitalDisplaySm
import com.example.viewmodel.TelemetryUiState
import com.example.viewmodel.TelemetryViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnalyticsScreen(
    viewModel: TelemetryViewModel,
    uiState: TelemetryUiState,
    modifier: Modifier = Modifier
) {
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
                    imageVector = Icons.Default.QueryStats,
                    contentDescription = null,
                    tint = PrimaryCyan,
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(text = "Clinical Analytics & Protocol Audits", style = Typography.headlineMedium, color = OnSurface)
                    Text(text = "VALIDATION BENCHMARKS • SEPSIS BUNDLE ADHERENCE • PREDICTION HORIZON", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                }
            }
        }

        // Top Metrics Grid
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Model ROC-AUC",
                value = "99.4%",
                sub = "Prospective ICU Cohort (n=2,410)",
                accentColor = PrimaryCyan,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Early Warning Horizon",
                value = "3.8 Hours",
                sub = "Median Pre-Arrest Prediction Lead Time",
                accentColor = TertiaryAmber,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "False Alarm Suppression",
                value = "74.2%",
                sub = "Nuisance Alert Filter Active",
                accentColor = PrimaryCyan,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Packet Sync Reliability",
                value = "99.98%",
                sub = "0 Drops in last 24h streaming",
                accentColor = PrimaryCyan,
                modifier = Modifier.weight(1f)
            )
        }

        // ICU Protocol Adherence Rates
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Rule, contentDescription = null, tint = PrimaryCyan)
                Text(text = "ICU Ward Alpha Protocol Compliance", style = Typography.titleLarge, color = OnSurface)
            }

            ProtocolBar(name = "Hour-1 Sepsis Resuscitation Bundle", score = 96, target = "Target: ≥90%")
            ProtocolBar(name = "Target MAP (≥ 65 mmHg) Inotrope Titration", score = 94, target = "Target: ≥85%")
            ProtocolBar(name = "Ventilator-Associated Pneumonia (VAP) Prevention", score = 100, target = "Target: 100%")
            ProtocolBar(name = "Post-Revascularization ABG Serial Sampling", score = 91, target = "Target: ≥90%")
            ProtocolBar(name = "Continuous Arterial Line Damping & Zeroing Check", score = 98, target = "Target: ≥95%")
        }

        // Audit Trail Logs
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryCyan)
                    Text(text = "Automated Protocol Audit Stream", style = Typography.titleLarge, color = OnSurface)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerHighest)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = "REAL-TIME EMR FEED", style = TelemetryUnitStyle, color = PrimaryCyan)
                }
            }

            val audits = listOf(
                Triple("14:20 UTC", "Bed 104 • Elena Rostova", "Lactate drawn within 45m of MAP trigger — COMPLIANT"),
                Triple("13:55 UTC", "Bed 104 • Elena Rostova", "IV Broad-spectrum antibiotics administered within 1 hr — COMPLIANT"),
                Triple("13:40 UTC", "Bed 108 • Arthur Pendelton", "Stat 12-lead ECG obtained under 10m of ST divergence — COMPLIANT"),
                Triple("13:10 UTC", "Bed 102 • Marcus Sterling", "Post-op warming target reached (36.8°C) without rebound — COMPLIANT"),
                Triple("12:30 UTC", "Bed 106 • Henry Wu", "Negative fluid balance achieved target (-1.8L) — COMPLIANT")
            )

            audits.forEach { (time, bed, message) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainerLowest)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = time, style = TelemetryUnitStyle, color = OnSurfaceVariant)
                        Text(text = bed, style = Typography.bodySmall.copy(fontWeight = FontWeight.SemiBold), color = PrimaryCyan)
                        Text(text = message, style = Typography.bodySmall, color = OnSurface)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(PrimaryCyanContainer.copy(alpha = 0.3f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "VERIFIED", style = TelemetryUnitStyle, color = PrimaryCyan)
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    sub: String,
    accentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, SurfaceContainerHigh, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, style = TelemetryUnitStyle, color = OnSurfaceVariant)
        Text(text = value, style = VitalDisplayMd, color = accentColor)
        Text(text = sub, style = Typography.bodySmall.copy(fontSize = 10.5.sp), color = OnSurfaceVariant)
    }
}

@Composable
private fun ProtocolBar(
    name: String,
    score: Int,
    target: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium), color = OnSurface)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = target, style = TelemetryUnitStyle, color = OnSurfaceVariant)
                Text(text = "$score%", style = VitalDisplaySm.copy(fontSize = 13.sp), color = PrimaryCyan)
            }
        }
        LinearProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = PrimaryCyan,
            trackColor = SurfaceContainer
        )
    }
}
