package com.example.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BedPatient
import com.example.ui.components.BedTelemetryCard
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
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.TertiaryAmber
import com.example.ui.theme.TertiaryAmberContainer
import com.example.ui.theme.TertiaryAmberFixed
import com.example.ui.theme.Typography
import com.example.viewmodel.SortCriteria
import com.example.viewmodel.TelemetryUiState
import com.example.viewmodel.TelemetryViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WardTriageMatrixScreen(
    viewModel: TelemetryViewModel,
    uiState: TelemetryUiState,
    modifier: Modifier = Modifier
) {
    val beds = viewModel.getFilteredAndSortedBeds()

    val infiniteTransition = rememberInfiniteTransition(label = "alert_pulse")
    val alertScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alert_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepObsidian)
    ) {
        // TOP COMMAND & CRITICAL TRIAGE BANNER STRIP
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLowest)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Ward Context & Sync Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "ICU Ward Alpha",
                        style = Typography.headlineMedium,
                        color = OnSurface
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "MED/SURG ICU",
                            style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                            color = PrimaryCyan
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryCyan)
                    )
                    Text(
                        text = "LIVE SYNC: ${uiState.currentUtcTime}",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant.copy(alpha = 0.4f)
                    )
                    Text(
                        text = "POLL: 0.4s",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant.copy(alpha = 0.4f)
                    )
                    Text(
                        text = "MODEL: BioPulse-v4.9",
                        style = TelemetryUnitStyle,
                        color = OnSurfaceVariant
                    )
                }
            }

            // Emergency Escalation Pill Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SecondaryCriticalContainer.copy(alpha = 0.35f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .scale(alertScale)
                            .clip(CircleShape)
                            .background(SecondaryCritical)
                    )
                    Text(
                        text = "2 CRITICAL DETERIORATION ALERTS",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SecondaryCritical
                    )
                    Text(
                        text = "— Immediate evaluation for Beds 104 & 108",
                        style = Typography.bodySmall,
                        color = SecondaryCriticalFixed,
                        maxLines = 1
                    )
                }

                Button(
                    onClick = { viewModel.triggerCodeDispatch(true) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryCritical,
                        contentColor = OnSecondary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.height(28.dp).testTag("dispatch_code_team_button")
                ) {
                    Text(
                        text = "DISPATCH CODE TEAM",
                        style = Typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }

            // Filters & Sorting Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filter Buttons Group
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainer)
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    FilterButton(
                        label = "All",
                        count = "12",
                        isActive = uiState.activeFilter == "ALL",
                        onClick = { viewModel.setFilter("ALL") },
                        testTag = "filter_all"
                    )
                    FilterButton(
                        label = "Critical",
                        count = "2",
                        badgeColor = SecondaryCritical,
                        isActive = uiState.activeFilter == "CRITICAL",
                        onClick = { viewModel.setFilter("CRITICAL") },
                        testTag = "filter_critical"
                    )
                    FilterButton(
                        label = "Warning",
                        count = "3",
                        badgeColor = TertiaryAmber,
                        isActive = uiState.activeFilter == "MODERATE",
                        onClick = { viewModel.setFilter("MODERATE") },
                        testTag = "filter_warning"
                    )
                    FilterButton(
                        label = "Stable",
                        count = "7",
                        badgeColor = PrimaryCyan,
                        isActive = uiState.activeFilter == "STABLE",
                        onClick = { viewModel.setFilter("STABLE") },
                        testTag = "filter_stable"
                    )
                }

                // Sort Dropdown
                SortMenu(
                    currentSort = uiState.sortCriteria,
                    onSelectSort = { viewModel.setSort(it) }
                )
            }
        }

        // 3x4 WARD TELEMETRY MATRIX GRID
        BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val columns = when {
                maxWidth >= 1200.dp -> 4
                maxWidth >= 800.dp -> 3
                maxWidth >= 500.dp -> 2
                else -> 1
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize().testTag("ward_matrix_grid")
            ) {
                items(beds, key = { it.bedNumber }) { patient ->
                    BedTelemetryCard(
                        patient = patient,
                        onCardClick = { viewModel.selectPatient(patient) },
                        onActionClick = { viewModel.selectPatient(patient) }
                    )
                }
            }
        }

        // STICKY OPERATIONAL TELEMETRY FOOTER BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLowest)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = PrimaryCyan,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "AI MODEL CONFIDENCE:",
                        style = Typography.labelSmall.copy(fontSize = 9.sp),
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = uiState.modelConfidenceRoc + " ROC-AUC",
                        style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryCyan
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timelapse,
                        contentDescription = null,
                        tint = TertiaryAmber,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "LEAD TIME:",
                        style = Typography.labelSmall.copy(fontSize = 9.sp),
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = uiState.meanLeadTimeHours + " HOURS",
                        style = TelemetryUnitStyle.copy(fontWeight = FontWeight.Bold),
                        color = TertiaryAmberFixed
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainer)
                        .clickable { viewModel.toggleAudio() }
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = null,
                        tint = if (uiState.isAudioArmed) PrimaryCyan else OnSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = if (uiState.isAudioArmed) "AUDIO: UNMUTED" else "AUDIO: MUTED",
                        style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                        color = OnSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(PrimaryCyan)
                    )
                    Text(
                        text = "0 PACKET DROPS",
                        style = TelemetryUnitStyle.copy(fontSize = 9.sp),
                        color = PrimaryCyan
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterButton(
    label: String,
    count: String,
    isActive: Boolean,
    onClick: () -> Unit,
    badgeColor: androidx.compose.ui.graphics.Color? = null,
    testTag: String
) {
    val bg = if (isActive) SurfaceContainerHigh else androidx.compose.ui.graphics.Color.Transparent
    val textCol = if (isActive) PrimaryCyan else OnSurfaceVariant

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (badgeColor != null) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(badgeColor)
            )
        }
        Text(text = label, style = Typography.labelSmall.copy(fontSize = 10.sp), color = textCol)
        Text(text = "($count)", style = TelemetryUnitStyle.copy(fontSize = 9.5.sp), color = OnSurfaceVariant)
    }
}

@Composable
private fun SortMenu(
    currentSort: SortCriteria,
    onSelectSort: (SortCriteria) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val sortLabel = when (currentSort) {
        SortCriteria.VELOCITY_DESC -> "Trajectory Velocity (Deterioration Rate ↓)"
        SortCriteria.SCORE_DESC -> "Risk Score (Highest First)"
        SortCriteria.BED_ASC -> "Bed Number (101 → 112)"
    }

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(SurfaceContainer)
                .clickable { expanded = true }
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .testTag("sort_menu_button"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Sort",
                tint = PrimaryCyan,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = "Sort:",
                style = Typography.labelSmall.copy(fontSize = 10.sp),
                color = OnSurfaceVariant
            )
            Text(
                text = sortLabel,
                style = Typography.labelSmall.copy(fontSize = 10.sp),
                color = OnSurface,
                maxLines = 1
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(SurfaceContainerHigh)
        ) {
            DropdownMenuItem(
                text = { Text("Trajectory Velocity (Deterioration Rate ↓)", color = OnSurface) },
                onClick = {
                    onSelectSort(SortCriteria.VELOCITY_DESC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Risk Score (Highest First)", color = OnSurface) },
                onClick = {
                    onSelectSort(SortCriteria.SCORE_DESC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Bed Number (101 → 112)", color = OnSurface) },
                onClick = {
                    onSelectSort(SortCriteria.BED_ASC)
                    expanded = false
                }
            )
        }
    }
}
