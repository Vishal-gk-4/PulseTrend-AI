package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.ui.components.CodeDispatchDialog
import com.example.ui.components.HotCallDialog
import com.example.ui.components.NavigationDrawerContent
import com.example.ui.components.TopCommandHeader
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.PatientDetailScreen
import com.example.ui.screens.SbarHandoffScreen
import com.example.ui.screens.WardTriageMatrixScreen
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.TelemetryViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: TelemetryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsState()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // Handle system back navigation to return to Ward Triage if on sub-screens
                BackHandler(enabled = uiState.currentScreen != ScreenDestination.WARD_TRIAGE) {
                    viewModel.navigateTo(ScreenDestination.WARD_TRIAGE)
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = DeepObsidian
                        ) {
                            NavigationDrawerContent(
                                currentDestination = uiState.currentScreen,
                                onNavigate = { destination ->
                                    viewModel.navigateTo(destination)
                                    scope.launch { drawerState.close() }
                                },
                                isAudioArmed = uiState.isAudioArmed,
                                onToggleAudio = { viewModel.toggleAudio() }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DeepObsidian)
                            .windowInsetsPadding(WindowInsets.safeDrawing),
                        topBar = {
                            TopCommandHeader(
                                currentTimeUtc = uiState.currentUtcTime,
                                onMenuClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                onHotCallClick = { viewModel.triggerHotCall(true) }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(DeepObsidian)
                        ) {
                            when (uiState.currentScreen) {
                                ScreenDestination.WARD_TRIAGE -> WardTriageMatrixScreen(
                                    viewModel = viewModel,
                                    uiState = uiState
                                )
                                ScreenDestination.PATIENT_DETAIL -> PatientDetailScreen(
                                    viewModel = viewModel,
                                    uiState = uiState
                                )
                                ScreenDestination.SBAR_HANDOFF -> SbarHandoffScreen(
                                    viewModel = viewModel,
                                    uiState = uiState
                                )
                                ScreenDestination.ANALYTICS -> AnalyticsScreen(
                                    viewModel = viewModel,
                                    uiState = uiState
                                )
                            }
                        }
                    }
                }

                // Emergency Dialogs
                if (uiState.showCodeDispatchDialog) {
                    CodeDispatchDialog(
                        onDismiss = { viewModel.triggerCodeDispatch(false) },
                        onConfirmDispatch = { viewModel.triggerCodeDispatch(false) }
                    )
                }

                if (uiState.showHotCallDialog) {
                    HotCallDialog(
                        onDismiss = { viewModel.triggerHotCall(false) }
                    )
                }
            }
        }
    }
}
