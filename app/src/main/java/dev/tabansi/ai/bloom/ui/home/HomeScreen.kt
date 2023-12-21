package dev.tabansi.ai.bloom.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tabansi.ai.bloom.R
import dev.tabansi.ai.bloom.ui.AppViewModelProvider
import dev.tabansi.ai.bloom.ui.BloomDestination

object HomeDestination : BloomDestination {
    override val route: String = "home"
}

@Composable
fun HomeScreen(
    navigateToChat: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(homeUiState.chats) { chat ->
            ChatCard(
                id = chat.id,
                title = chat.title,
                lastUpdate = chat.lastUpdate,
                onChatClick = { navigateToChat() }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    var id = 0
    var time = "12:00 am"
//    HomeScreen(
//
//    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatCard(
    id: Int,
    title: String,
    lastUpdate: String,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onChatClick,
        modifier = modifier.padding(
            horizontal = 16.dp, // todo try 16 with 8
            vertical = 8.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
            ) {
                Text(
                    text = title,
                    modifier = Modifier,
//                        .align(Alignment.End)
//                        .padding(
//                            start = 16.dp,
//                            top = 16.dp,
//                            bottom = 16.dp,
//                            end = 0.dp
//                        )
//                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    minLines = 2,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = lastUpdate,
                    maxLines = 1
                )
            }
            IconButton(onClick = { /*TODO add options*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.options),
                    contentDescription = "Options",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatCardPreview() {
    ChatCard(
        id = 1,
        title = "Chat title",
        lastUpdate = "12:00 am",
        onChatClick = {}
    )
}