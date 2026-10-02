package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.PrimaryCyan
import com.example.ui.theme.SecondaryCritical
import com.example.ui.theme.SecondaryCriticalContainer
import com.example.ui.theme.SecondaryCriticalFixed
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TelemetryUnitStyle
import com.example.ui.theme.Typography

@Composable
fun CodeDispatchDialog(
    onDismiss: () -> Unit,
    onConfirmDispatch: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .border(2.dp, SecondaryCritical, RoundedCornerShape(12.dp))
            .padding(16.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Emergency,
                    contentDescription = null,
                    tint = SecondaryCritical,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "DISPATCH MEDICAL CODE TEAM",
                    style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryCritical
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "High-priority activation request for ICU Ward Alpha deterioration protocol.",
                    style = Typography.bodyMedium,
                    color = OnSurface
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(SecondaryCriticalContainer.copy(alpha = 0.3f))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "TARGET 1: BED 104 • ELENA ROSTOVA (64F)",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SecondaryCriticalFixed
                    )
                    Text(
                        text = "Severe Sepsis Decompensation / MAP 65 Refractory",
                        style = Typography.bodySmall,
                        color = OnSurface
                    )
                    Text(
                        text = "TARGET 2: BED 108 • ARTHUR PENDELTON (71M)",
                        style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SecondaryCriticalFixed
                    )
                    Text(
                        text = "Acute Coronary Syndrome / ST Dep -2.1mm",
                        style = Typography.bodySmall,
                        color = OnSurface
                    )
                }
                Text(
                    text = "Paging ICU Respiratory Therapist, Senior Anesthesiologist, and Code Resuscitation Team.",
                    style = TelemetryUnitStyle,
                    color = OnSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmDispatch,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryCritical,
                    contentColor = OnSecondary
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.testTag("confirm_dispatch_btn")
            ) {
                Text(text = "CONFIRM CODE DISPATCH", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainerHigh,
                    contentColor = OnSurface
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "CANCEL", style = Typography.labelSmall)
            }
        }
    )
}

@Composable
fun HotCallDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
            .border(2.dp, SecondaryCritical, RoundedCornerShape(12.dp))
            .padding(16.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = SecondaryCritical,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "RAPID RESPONSE HOT-CALL",
                    style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryCritical
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Direct telemetry voice bridge connected to Chief Intensivist Dr. M. Vance, MD.",
                    style = Typography.bodyMedium,
                    color = OnSurface
                )
                Text(
                    text = "• Ward Alpha Alarm Chime: BROADCAST ON OVERRIDE\n• Bed 104 Live Telemetry Link: STREAMING TO MOBILE DEVICE\n• Stat pharmacy vasopressor kit queued.",
                    style = Typography.bodySmall,
                    color = OnSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryCyan,
                    contentColor = com.example.ui.theme.OnPrimary
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "DISMISS CALL", style = Typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
        }
    )
}
