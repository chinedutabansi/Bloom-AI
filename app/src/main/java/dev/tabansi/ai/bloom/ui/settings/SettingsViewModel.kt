package dev.tabansi.ai.bloom.ui.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
}

data class SettingsUiState(
    val darkTheme: Boolean
)