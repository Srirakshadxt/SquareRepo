package com.sriraksha.squarerepo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sriraksha.squarerepo.R
import com.sriraksha.squarerepo.data.model.SquareRepo
import com.sriraksha.squarerepo.presentation.SquareRepoViewModel
import com.sriraksha.squarerepo.presentation.SquareUiState
import com.sriraksha.squarerepo.presentation.utils.DateUtils.Companion.DATE_FORMAT_EEEE_DD_MMMM_YYYY
import com.sriraksha.squarerepo.presentation.utils.DateUtils.Companion.dateToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquareRepoScreen(
    viewModel: SquareRepoViewModel = hiltViewModel()
) {
    val squareUiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.square_repositories),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            )
        },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (squareUiState) {
                is SquareUiState.Loading -> LoadingScreen()
                is SquareUiState.Error -> ErrorScreen(
                    (squareUiState as SquareUiState.Error).errorMessage,
                    onRetry = viewModel::loadSquareRepositories
                )

                is SquareUiState.Success -> SquareRepoList(
                    repositories = (squareUiState as SquareUiState.Success).squareRepos
                )
            }
        }
    }
}

@Composable
fun SquareRepoList(
    repositories: List<SquareRepo>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()

    ) {
        items(repositories) { repo ->
            SquareRepoListItem(
                repo = repo,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun SquareRepoListItem(
    repo: SquareRepo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.onPrimary.copy(0.10f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.onBackground.copy(0.10f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = repo.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RectangleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = repo.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = repo.description,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                SquareRepoMetadata(
                    icon = Icons.Default.DateRange,
                    label = stringResource(id = R.string.Created),
                    value = dateToString(repo.createdAt, DATE_FORMAT_EEEE_DD_MMMM_YYYY)
                )
                Spacer(modifier = Modifier.width(4.dp))
                SquareRepoMetadata(
                    icon = Icons.Default.CheckCircle,
                    label = stringResource(id = R.string.Updated),
                    value = dateToString(repo.updatedAt, DATE_FORMAT_EEEE_DD_MMMM_YYYY)
                )
                Spacer(modifier = Modifier.width(4.dp))
                SquareRepoMetadata(
                    icon = Icons.Default.CheckCircle,
                    label = stringResource(id = R.string.Pushed),
                    value = dateToString(repo.pushedAt, DATE_FORMAT_EEEE_DD_MMMM_YYYY)
                )
                Spacer(modifier = Modifier.width(4.dp))
                SquareRepoMetadata(
                    icon = Icons.Default.Face,
                    label = stringResource(id = R.string.Watchers),
                    value = repo.watchersCount.toString()
                )
            }
        }
    }
}

@Composable
fun SquareRepoMetadata(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
            )
            Text(
                text = value,
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}