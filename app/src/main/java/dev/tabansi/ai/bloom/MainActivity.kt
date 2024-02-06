package dev.tabansi.ai.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.ai.client.generativeai.GenerativeModel
import dev.tabansi.ai.bloom.sample.gemini.starter.SummarizeRoute
import dev.tabansi.ai.bloom.sample.gemini.starter.SummarizeViewModel
import dev.tabansi.ai.bloom.sample.palm.SamplePalmChatUi
import dev.tabansi.ai.bloom.sample.singleChat.ChatScreen
import dev.tabansi.ai.bloom.ui.theme.BloomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen()
                }
            }
        }
    }
}
