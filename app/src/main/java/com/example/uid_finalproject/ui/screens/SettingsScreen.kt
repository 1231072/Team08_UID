package com.example.uid_finalproject.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uid_finalproject.model.FamilyMember
import com.example.uid_finalproject.model.SettingToggleItem
import com.example.uid_finalproject.ui.components.*
import com.example.uid_finalproject.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val familyMembers = listOf(
        FamilyMember("Emma (5)", "👧"),
        FamilyMember("Alex (14)", "👦"),
        FamilyMember("Dad", "👨"),
        FamilyMember("Mom", "👩")
    )

    val notifications = listOf(
        SettingToggleItem("Security Alerts", "Doors, windows, motion", true),
        SettingToggleItem("Medication Reminders", "For family members", true),
        SettingToggleItem("Energy Reports", "Daily consumption", false),
        SettingToggleItem("System Updates", "New features", true)
    )

    val accessibilityToggles = listOf(
        SettingToggleItem("Large Text", "Easier to read", false),
        SettingToggleItem("High Contrast", "Better visibility", false),
        SettingToggleItem("Voice Assistant", "Audio guidance", true)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") } }
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
            item { FamilyProfileCard() }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FamilyMemberCard(familyMembers[0], Modifier.weight(1f))
                        FamilyMemberCard(familyMembers[1], Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FamilyMemberCard(familyMembers[2], Modifier.weight(1f))
                        FamilyMemberCard(familyMembers[3], Modifier.weight(1f))
                    }
                }
            }

            item { SectionHeader("Notifications") }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        notifications.forEach { item -> SettingSwitchRow(item) }
                    }
                }
            }

            item { SectionHeader("Accessibility") }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        SettingSwitchRow(accessibilityToggles[0])
                        SettingSliderRow("Font Size", 1f, "100%")
                        SettingSwitchRow(accessibilityToggles[1])
                        SettingSwitchRow(accessibilityToggles[2])
                        SettingSliderRow("System Volume", 0.75f, "75%")
                    }
                }
            }

            item { SectionHeader("Appearance") }
            item { AppearanceSelector() }

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
                SignOutButton()
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}