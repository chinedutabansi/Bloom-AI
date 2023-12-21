package dev.tabansi.ai.bloom.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.tabansi.ai.bloom.BloomApplication
import dev.tabansi.ai.bloom.ui.chat.ChatViewModel
import dev.tabansi.ai.bloom.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(bloomApplication().container.chatRepository)
        }

        // Initializer for ChatViewModel
        initializer {
            ChatViewModel(
                bloomApplication().container.chatRepository,
                bloomApplication().container.messageRepository,
                this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.bloomApplication(): BloomApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloomApplication