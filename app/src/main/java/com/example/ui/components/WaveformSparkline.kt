package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.model.EcgRhythm
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle

@Composable
fun WaveformSparkline(
    ecg: EcgRhythm,
    modifier: Modifier = Modifier,
    lineColor: Color = if (ecg.isCritical) SecondaryCritical else PrimaryCyan
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ecg_sweep")
    val sweepProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep_progress"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(34.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(SurfaceContainerLowest)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ecg.lead,
            style = TelemetryUnitStyle,
            color = OnSurfaceVariant
        )

        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(26.dp)
                .padding(horizontal = 8.dp)
        ) {
            val width = size.width
            val height = size.height
            val midY = height / 2f

            val path = Path()
            path.moveTo(0f, midY)

            // Dynamic ECG rhythmic wave coordinates mapped to canvas width
            val points = listOf(
                0.00f to 0.50f,
                0.10f to 0.50f,
                0.14f to 0.20f,
                0.16f to 0.85f,
                0.19f to 0.05f,
                0.22f to 0.95f,
                0.25f to 0.50f,
                0.38f to 0.50f,
                0.42f to 0.15f,
                0.44f to 0.85f,
                0.47f to 0.08f,
                0.50f to 0.92f,
                0.53f to 0.50f,
                0.66f to 0.50f,
                0.70f to 0.18f,
                0.72f to 0.88f,
                0.75f to 0.05f,
                0.78f to 0.95f,
                0.81f to 0.50f,
                1.00f to 0.50f
            )

            for (pt in points) {
                val x = pt.first * width
                val y = pt.second * height
                path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = lineColor.copy(alpha = 0.85f),
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Animated live sweep indicator dot
            val dotX = sweepProgress * width
            drawCircle(
                color = lineColor,
                radius = 3.dp.toPx(),
                center = Offset(dotX, midY)
            )
        }

        Text(
            text = ecg.stSegment,
            style = TelemetryUnitStyle,
            color = if (ecg.isCritical) SecondaryCritical else PrimaryCyan
        )
    }
}
