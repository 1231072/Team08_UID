package com.example.uid_finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.uid_finalproject.MainViewModel
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {

    val familyMembers = viewModel.familyMembers
    val notifications = viewModel.notificationSettings
    val accessibilityToggles = viewModel.accessibilitySettings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                actions = {
                    IconButton(onClick = { viewModel.addFamilyMember("Grandson", "👶") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        },
        bottomBar = {
            SmartHomeBottomBar(navController = navController, currentRoute = Routes.SETTINGS)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AlertBanner("Alert: Window Open in Kids Room")
            }

            item { SectionHeader("Family Profile") }
            item { FamilyProfileCard(name = viewModel.loggedInUser, email = viewModel.userEmail) }

            items(familyMembers.chunked(2)) { rowMembers ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowMembers.forEach { member ->
                        FamilyMemberCard(member, Modifier.weight(1f))
                    }
                    if (rowMembers.size < 2) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            item { SectionHeader("Notifications") }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        notifications.forEach { item ->
                            SettingSwitchRow(item = item, onCheckedChange = { isEnabled ->
                                viewModel.onNotificationSettingChanged(item, isEnabled)
                            })
                        }
                    }
                }
            }

            item { SectionHeader("Accessibility") }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SettingSwitchRow(item = accessibilityToggles[0], onCheckedChange = { isEnabled ->
                            viewModel.onAccessibilitySettingChanged(accessibilityToggles[0], isEnabled)
                        })
                        SettingSliderRow(
                            title = "Font Size",
                            value = viewModel.fontSize,
                            valueText = "${(viewModel.fontSize * 100).toInt()}%",
                            onValueChange = { newSize -> viewModel.fontSize = newSize }
                        )
                        SettingSwitchRow(item = accessibilityToggles[1], onCheckedChange = { isEnabled ->
                            viewModel.onAccessibilitySettingChanged(accessibilityToggles[1], isEnabled)
                        })
                        SettingSwitchRow(item = accessibilityToggles[2], onCheckedChange = { isEnabled ->
                            viewModel.onAccessibilitySettingChanged(accessibilityToggles[2], isEnabled)
                        })
                        SettingSliderRow(
                            title = "System Volume",
                            value = viewModel.systemVolume,
                            valueText = "${(viewModel.systemVolume * 100).toInt()}%",
                            onValueChange = { newVolume -> viewModel.systemVolume = newVolume }
                        )
                    }
                }
            }

            item { SectionHeader("Appearance") }
            item { AppearanceSelector(isDarkTheme = viewModel.isDarkTheme, onThemeChange = { isDarkTheme -> viewModel.onThemeChanged(isDarkTheme) }) }

            item { SectionHeader("Security & Privacy") }
            item {
                Column {
                    SettingLinkRow("Change PIN")
                    SettingLinkRow("Privacy Settings")
                    SettingLinkRow("Camera Permissions")
                }
            }

            item { SectionHeader("Help & Support") }
            item {
                Column {
                    SettingLinkRow("Quick Start Guide")
                    SettingLinkRow("Contact Support")
                    SettingLinkRow("About")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SignOutButton(onClick = {
                    viewModel.logout()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                })
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}