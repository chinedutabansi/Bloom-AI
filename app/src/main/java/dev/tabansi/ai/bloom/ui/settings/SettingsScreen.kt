package dev.tabansi.ai.bloom.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tabansi.ai.bloom.ui.AppViewModelProvider
import dev.tabansi.ai.bloom.ui.BloomDestination

object SettingsDestination : BloomDestination {
    override val route: String = "settings"
}

@Preview
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val radioOptions = listOf("System Theme", "Light", "Dark")
    var selectedTheme by remember { mutableStateOf(radioOptions[0]) }

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = "Theme",
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                ),
                style = MaterialTheme.typography.titleLarge
            )
            // Divider
            Column(
                modifier = Modifier.selectableGroup()
            ) {
                radioOptions.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = ( theme == selectedTheme ),
                                onClick = { selectedTheme = theme },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = ( theme == selectedTheme ),
                            onClick = null, // { selectedTheme = theme }
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Text(
                            text = theme,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Default Chat Settings",
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                ),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
