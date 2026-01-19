package com.example.uid_finalproject.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingToggleItem(
    val title: String,
    val subtitle: String? = null,
    val isChecked: Boolean
)

data class SettingLinkItem(
    val title: String,
    val icon: ImageVector? = null
)

data class FamilyMember(
    val name: String,
    val emoji: String
)