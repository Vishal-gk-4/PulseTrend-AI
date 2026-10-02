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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grid3x3
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.Typography
import com.example.viewmodel.ScreenDestination

@Composable
fun NavigationDrawerContent(
    currentDestination: ScreenDestination,
    onNavigate: (ScreenDestination) -> Unit,
    isAudioArmed: Boolean,
    onToggleAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "poll_beacon")
    val beaconScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "beacon_scale"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(SurfaceContainerLowest)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Brand Logo & Telemetry Engine Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
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
                        contentDescription = "PulseTrend AI Logo",
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column {
                    Text(
                        text = "PulseTrend AI",
                        style = Typography.titleLarge,
                        color = PrimaryCyan
                    )
                    Text(
                        text = "TELEMETRY ENGINE",
                        style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                        color = OnSurfaceVariant
                    )
                }
            }

            // Active Unit Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerLow)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "ACTIVE UNIT",
                    style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                    color = OnSurfaceVariant
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ICU Ward Alpha",
                        style = Typography.headlineSmall,
                        color = OnSurface
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .scale(beaconScale)
                            .clip(CircleShape)
                            .background(PrimaryCyan)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "12 Beds Monitored",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = "0.4s POLL",
                        style = TelemetryUnitStyle,
                        color = PrimaryCyan
                    )
                }
            }

            // Navigation Links
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                NavItem(
                    title = "Ward Triage Matrix",
                    icon = Icons.Default.Grid3x3,
                    isSelected = currentDestination == ScreenDestination.WARD_TRIAGE,
                    onClick = { onNavigate(ScreenDestination.WARD_TRIAGE) },
                    testTag = "nav_ward_triage"
                )

                NavItem(
                    title = "Patient Detail (Bed 104)",
                    icon = Icons.Default.MonitorHeart,
                    isSelected = currentDestination == ScreenDestination.PATIENT_DETAIL,
                    onClick = { onNavigate(ScreenDestination.PATIENT_DETAIL) },
                    testTag = "nav_patient_detail"
                )

                NavItem(
                    title = "SBAR Handoff & Alerts",
                    icon = Icons.Default.NotificationsActive,
                    isSelected = currentDestination == ScreenDestination.SBAR_HANDOFF,
                    onClick = { onNavigate(ScreenDestination.SBAR_HANDOFF) },
                    testTag = "nav_sbar_handoff"
                )

                NavItem(
                    title = "Analytics & Protocols",
                    icon = Icons.Default.QueryStats,
                    isSelected = currentDestination == ScreenDestination.ANALYTICS,
                    onClick = { onNavigate(ScreenDestination.ANALYTICS) },
                    testTag = "nav_analytics"
                )
            }
        }

        // Bottom Controls: Audio Chime & Clinician Profile
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            HorizontalDivider(color = SurfaceContainerHigh)

            // Audio Chime Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerLow)
                    .clickable { onToggleAudio() }
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Audio Chime Toggle",
                        tint = if (isAudioArmed) PrimaryCyan else OnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AUDIO CHIME",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                }
                Text(
                    text = if (isAudioArmed) "ARMED" else "MUTED",
                    style = TelemetryUnitStyle,
                    color = if (isAudioArmed) PrimaryCyan else SecondaryCritical
                )
            }

            // Clinician Profile Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PrimaryCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = "Dr. M. Vance, MD",
                        style = Typography.titleMedium,
                        color = OnSurface
                    )
                    Text(
                        text = "Chief Intensivist",
                        style = Typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun NavItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val bg = if (isSelected) SurfaceContainerHigh else SurfaceContainerLowest
    val textColor = if (isSelected) PrimaryCyan else OnSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = title,
            style = Typography.titleMedium,
            color = textColor
        )
    }
}
