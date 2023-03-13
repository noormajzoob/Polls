package com.alnoor.polls.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    onItemClick: (Int)-> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    titleColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconsTintColor: Color = titleColor,
    cornerRadius: Dp = 35.dp
) {

    val weight = 1f / items.size

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
    ){

    }

}

@Composable
fun BottomNavItemView(
    modifier: Modifier = Modifier,
    bottomNavItem: BottomNavItem,
) {
    Column(modifier = modifier.padding(6.dp)) {

    }
}

data class BottomNavItem(
    val title: String,
    val icon: Int
)