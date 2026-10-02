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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSecondary
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
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.TertiaryAmberFixed
import com.example.ui.theme.Typography

const val BRAND_LOGO_URL =
    "https://lh3.googleusercontent.com/aida/AEtjO1XXieAKNYo0umqLT7GLHQuSDrIRQuk4N1MZr9Zu09TZpMV6vE1EyCx1qEqq4ESjpmqW1f-7xliyff7rHDevaNla4TWq6OZkmnDED_oI-wDPQRmjLPMzx6f9twf5x_ZCkqyLz0OtBMJWXyFi2awymyrVnBXp_aBZsJ7albhYPWvMXKJhrfjtRYSQZ5EIqfG62wfzMkIHTLOersQtiw8wuo0LCoVIsO49Di5S0rDDdbjokWeL5b-UtRlS9L4"

@Composable
fun TopCommandHeader(
    currentTimeUtc: String,
    onMenuClick: () -> Unit,
    onHotCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_alert")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceContainerLow.copy(alpha = 0.95f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Menu & Brand Identity
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainer)
                    .testTag("menu_drawer_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Navigation Menu",
                    tint = PrimaryCyan
                )
            }

            // Brand Logo
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(DeepObsidian)
                    .border(1.dp, SurfaceContainerHigh, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(BRAND_LOGO_URL)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.ic_launcher_pulsetrend),
                    error = painterResource(id = R.drawable.ic_launcher_pulsetrend),
                    contentDescription = "PulseTrend AI Brand Logo",
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "PulseTrend AI",
                        style = Typography.headlineSmall,
                        color = OnSurface
                    )
                }
                Text(
                    text = "ICU CENTRAL COMMAND STATION",
                    style = TelemetryUnitStyle.copy(fontSize = 8.5.sp),
                    color = OnSurfaceVariant
                )
            }
        }

        // Center / Right Status Pill Clusters
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Live UTC Clock
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerHighest)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = PrimaryCyan,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = currentTimeUtc,
                    style = TelemetryUnitStyle,
                    color = PrimaryCyan
                )
            }

            // Rapid Response Hot-Call Button
            Button(
                onClick = onHotCallClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryCriticalContainer,
                    contentColor = OnSecondary
                ),
                shape = RoundedCornerShape(4.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = 10.dp,
                    vertical = 4.dp
                ),
                modifier = Modifier
                    .height(32.dp)
                    .testTag("rapid_response_hotcall_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = "Rapid Response Emergency",
                        modifier = Modifier
                            .size(15.dp)
                            .scale(pulseScale)
                    )
                    Text(
                        text = "HOT-CALL",
                        style = Typography.labelSmall.copy(fontSize = 10.sp),
                        color = SecondaryCriticalFixed
                    )
                }
            }

            // User Profile Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PrimaryCyan)
                    .clickable { /* profile info */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Dr. M. Vance, MD",
                    tint = OnPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
