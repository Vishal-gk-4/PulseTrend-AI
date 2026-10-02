package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SecondaryCriticalContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.Typography
import com.example.ui.theme.VitalDisplayLg
import com.example.ui.theme.VitalDisplaySm

enum class TrajectoryChartType {
    HEART_RATE,
    SPO2,
    ARTERIAL_PRESSURE
}

@Composable
fun TrajectoryChartCard(
    chartType: TrajectoryChartType,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val badgeText = when (chartType) {
                    TrajectoryChartType.HEART_RATE -> "ECG CH-II"
                    TrajectoryChartType.SPO2 -> "PLETH PPG"
                    TrajectoryChartType.ARTERIAL_PRESSURE -> "ART-LINE RADIAL"
                }
                val badgeColor = when (chartType) {
                    TrajectoryChartType.HEART_RATE -> SecondaryCritical
                    TrajectoryChartType.SPO2 -> PrimaryCyan
                    TrajectoryChartType.ARTERIAL_PRESSURE -> SecondaryCritical
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainer)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = badgeText, style = TelemetryUnitStyle, color = badgeColor)
                }

                val titleText = when (chartType) {
                    TrajectoryChartType.HEART_RATE -> "Heart Rate (HR)"
                    TrajectoryChartType.SPO2 -> "Oxygen Saturation (SpO2)"
                    TrajectoryChartType.ARTERIAL_PRESSURE -> "Arterial Pressure (MAP / SBP)"
                }
                Text(text = titleText, style = Typography.headlineSmall, color = OnSurface)

                val subText = when (chartType) {
                    TrajectoryChartType.HEART_RATE -> "NORMAL: 60 - 100 BPM"
                    TrajectoryChartType.SPO2 -> "CRITICAL HYPOXEMIA < 92%"
                    TrajectoryChartType.ARTERIAL_PRESSURE -> "TARGET MAP ≥ 65 MMHG"
                }
                Text(text = subText, style = TelemetryUnitStyle, color = OnSurfaceVariant)
            }

            // Current & Proj Value
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "CURRENT", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                when (chartType) {
                    TrajectoryChartType.HEART_RATE -> {
                        Text(text = "128", style = VitalDisplayLg, color = SecondaryCritical)
                        Text(text = "BPM", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SecondaryCriticalContainer.copy(alpha = 0.3f))
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = null,
                                tint = SecondaryCritical,
                                modifier = Modifier.height(12.dp)
                            )
                            Text(text = "Proj 142", style = TelemetryUnitStyle, color = SecondaryCritical)
                        }
                    }
                    TrajectoryChartType.SPO2 -> {
                        Text(text = "88", style = VitalDisplayLg, color = TertiaryAmber)
                        Text(text = "%", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SecondaryCriticalContainer.copy(alpha = 0.3f))
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = null,
                                tint = SecondaryCritical,
                                modifier = Modifier.height(12.dp)
                            )
                            Text(text = "Proj 82%", style = TelemetryUnitStyle, color = SecondaryCritical)
                        }
                    }
                    TrajectoryChartType.ARTERIAL_PRESSURE -> {
                        Text(text = "88/54", style = VitalDisplayLg, color = SecondaryCritical)
                        Text(text = "(MAP 65)", style = VitalDisplaySm, color = TertiaryAmber)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SecondaryCriticalContainer.copy(alpha = 0.3f))
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint = SecondaryCritical,
                                modifier = Modifier.height(12.dp)
                            )
                            Text(text = "MAP Proj 58", style = TelemetryUnitStyle, color = SecondaryCritical)
                        }
                    }
                }
            }
        }

        // Time Series Canvas Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(SurfaceContainerLowest.copy(alpha = 0.85f))
                .padding(6.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val nowX = w * 0.70f // NOW line position (70% across)

                // Background Corridor or Hazard Zone
                when (chartType) {
                    TrajectoryChartType.HEART_RATE -> {
                        // Eunomic Corridor (60-100 bpm)
                        val corridorTop = h * 0.35f
                        val corridorBottom = h * 0.75f
                        drawRect(
                            color = PrimaryCyan.copy(alpha = 0.06f),
                            topLeft = Offset(40f, corridorTop),
                            size = Size(w - 40f, corridorBottom - corridorTop)
                        )
                    }
                    TrajectoryChartType.SPO2 -> {
                        // Desaturation Hazard Zone (<92%)
                        val hazardTop = h * 0.50f
                        drawRect(
                            color = SecondaryCriticalContainer.copy(alpha = 0.15f),
                            topLeft = Offset(40f, hazardTop),
                            size = Size(w - 40f, h - hazardTop)
                        )
                    }
                    TrajectoryChartType.ARTERIAL_PRESSURE -> {
                        // Hypoperfusion Floor line (MAP 65)
                        val floorY = h * 0.62f
                        drawLine(
                            color = SecondaryCritical.copy(alpha = 0.6f),
                            start = Offset(40f, floorY),
                            end = Offset(w, floorY),
                            strokeWidth = 1.5f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f)
                        )
                    }
                }

                // Gridlines
                val gridY1 = h * 0.25f
                val gridY2 = h * 0.50f
                val gridY3 = h * 0.75f
                drawLine(
                    color = SurfaceVariant,
                    start = Offset(40f, gridY1),
                    end = Offset(w, gridY1),
                    strokeWidth = 0.8f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
                drawLine(
                    color = SurfaceVariant,
                    start = Offset(40f, gridY2),
                    end = Offset(w, gridY2),
                    strokeWidth = 0.8f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
                drawLine(
                    color = SurfaceVariant,
                    start = Offset(40f, gridY3),
                    end = Offset(w, gridY3),
                    strokeWidth = 0.8f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )

                // NOW vertical divider
                drawLine(
                    color = PrimaryCyan.copy(alpha = 0.8f),
                    start = Offset(nowX, 4f),
                    end = Offset(nowX, h - 4f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
                )

                // Draw curves based on chartType
                when (chartType) {
                    TrajectoryChartType.HEART_RATE -> {
                        // Confidence band (polygon from NOW to end)
                        val bandPath = Path().apply {
                            moveTo(nowX, h * 0.38f)
                            lineTo(w * 0.85f, h * 0.20f)
                            lineTo(w, h * 0.08f)
                            lineTo(w, h * 0.36f)
                            lineTo(w * 0.85f, h * 0.46f)
                            lineTo(nowX, h * 0.38f)
                            close()
                        }
                        drawPath(
                            path = bandPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    SecondaryCritical.copy(alpha = 0.35f),
                                    SecondaryCritical.copy(alpha = 0.05f)
                                )
                            )
                        )

                        // Historical Line
                        val histPath = Path().apply {
                            moveTo(40f, h * 0.74f)
                            cubicTo(w * 0.2f, h * 0.70f, w * 0.4f, h * 0.58f, w * 0.55f, h * 0.46f)
                            lineTo(nowX, h * 0.38f)
                        }
                        drawPath(
                            path = histPath,
                            brush = Brush.horizontalGradient(
                                colors = listOf(PrimaryCyan, TertiaryAmber, SecondaryCritical)
                            ),
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Projected Forecast Dotted Line
                        val projPath = Path().apply {
                            moveTo(nowX, h * 0.38f)
                            cubicTo(w * 0.80f, h * 0.26f, w * 0.90f, h * 0.18f, w, h * 0.12f)
                        }
                        drawPath(
                            path = projPath,
                            color = SecondaryCritical,
                            style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
                            )
                        )

                        // Current point & projected endpoint
                        drawCircle(color = SecondaryCritical, radius = 5.dp.toPx(), center = Offset(nowX, h * 0.38f))
                        drawCircle(color = SecondaryCritical, radius = 4.dp.toPx(), center = Offset(w, h * 0.12f))
                    }

                    TrajectoryChartType.SPO2 -> {
                        // SpO2 Confidence band
                        val bandPath = Path().apply {
                            moveTo(nowX, h * 0.62f)
                            lineTo(w * 0.85f, h * 0.78f)
                            lineTo(w, h * 0.90f)
                            lineTo(w, h * 0.72f)
                            lineTo(w * 0.85f, h * 0.64f)
                            lineTo(nowX, h * 0.62f)
                            close()
                        }
                        drawPath(
                            path = bandPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(TertiaryAmber.copy(alpha = 0.25f), SecondaryCriticalContainer.copy(alpha = 0.3f))
                            )
                        )

                        // Historical Curve (96% down to 88%)
                        val histPath = Path().apply {
                            moveTo(40f, h * 0.24f)
                            cubicTo(w * 0.2f, h * 0.28f, w * 0.4f, h * 0.40f, w * 0.55f, h * 0.52f)
                            lineTo(nowX, h * 0.62f)
                        }
                        drawPath(
                            path = histPath,
                            color = PrimaryCyan,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Projected Curve (down to 82%)
                        val projPath = Path().apply {
                            moveTo(nowX, h * 0.62f)
                            cubicTo(w * 0.80f, h * 0.72f, w * 0.90f, h * 0.80f, w, h * 0.86f)
                        }
                        drawPath(
                            path = projPath,
                            color = TertiaryAmber,
                            style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
                            )
                        )

                        drawCircle(color = TertiaryAmber, radius = 5.dp.toPx(), center = Offset(nowX, h * 0.62f))
                        drawCircle(color = SecondaryCritical, radius = 4.dp.toPx(), center = Offset(w, h * 0.86f))
                    }

                    TrajectoryChartType.ARTERIAL_PRESSURE -> {
                        // SBP curve (124 down to 88)
                        val sbpPath = Path().apply {
                            moveTo(40f, h * 0.26f)
                            cubicTo(w * 0.25f, h * 0.32f, w * 0.5f, h * 0.50f, nowX, h * 0.66f)
                        }
                        drawPath(
                            path = sbpPath,
                            color = SecondaryCritical,
                            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                        )
                        val sbpProj = Path().apply {
                            moveTo(nowX, h * 0.66f)
                            lineTo(w, h * 0.82f)
                        }
                        drawPath(
                            path = sbpProj,
                            color = SecondaryCritical,
                            style = Stroke(
                                width = 2.5.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                            )
                        )

                        // MAP curve (94 down to 65 -> proj 58)
                        val mapPath = Path().apply {
                            moveTo(40f, h * 0.44f)
                            cubicTo(w * 0.25f, h * 0.48f, w * 0.5f, h * 0.58f, nowX, h * 0.66f)
                        }
                        drawPath(
                            path = mapPath,
                            color = TertiaryAmber,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                        val mapProj = Path().apply {
                            moveTo(nowX, h * 0.66f)
                            lineTo(w, h * 0.84f)
                        }
                        drawPath(
                            path = mapProj,
                            color = TertiaryAmber,
                            style = Stroke(
                                width = 3.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
                            )
                        )

                        drawCircle(color = TertiaryAmber, radius = 5.dp.toPx(), center = Offset(nowX, h * 0.66f))
                        drawCircle(color = SecondaryCritical, radius = 4.dp.toPx(), center = Offset(w, h * 0.84f))
                    }
                }
            }
        }

        // Bottom Scale Timestamps
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            when (chartType) {
                TrajectoryChartType.HEART_RATE -> {
                    Text(text = "-4h (10:28)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-3h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-2h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-1h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "NOW (14:28)", style = TelemetryUnitStyle, color = PrimaryCyan)
                    Text(text = "+30m", style = TelemetryUnitStyle, color = SecondaryCritical)
                    Text(text = "+60m (15:28)", style = TelemetryUnitStyle, color = SecondaryCritical)
                }
                TrajectoryChartType.SPO2 -> {
                    Text(text = "-4h (96%)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-3h (94%)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-2h (92%)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-1h (90%)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "NOW (88%)", style = TelemetryUnitStyle, color = PrimaryCyan)
                    Text(text = "+30m (85%)", style = TelemetryUnitStyle, color = TertiaryAmber)
                    Text(text = "+60m (82%)", style = TelemetryUnitStyle, color = SecondaryCritical)
                }
                TrajectoryChartType.ARTERIAL_PRESSURE -> {
                    Text(text = "-4h (124/80 - MAP 94)", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-3h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-2h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "-1h", style = TelemetryUnitStyle, color = OnSurfaceVariant)
                    Text(text = "NOW (88/54 - MAP 65)", style = TelemetryUnitStyle, color = PrimaryCyan)
                    Text(text = "+30m", style = TelemetryUnitStyle, color = TertiaryAmber)
                    Text(text = "+60m (MAP 58 Refractory)", style = TelemetryUnitStyle, color = SecondaryCritical)
                }
            }
        }
    }
}
