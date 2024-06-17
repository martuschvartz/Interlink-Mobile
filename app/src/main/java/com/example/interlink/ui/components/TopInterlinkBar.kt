package com.example.interlink.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interlink.R
import com.example.interlink.ui.theme.md_theme_light_intergreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopInterlinkBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = stringResource(R.string.inter_title),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = md_theme_light_intergreen,
                titleContentColor = Color.Black,
            ),
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.interlink),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .width(95.dp)
                            .height(84.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(),

        )
        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomStart),
            thickness = 3.dp,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun TopInterlinkBarPreview(){
    TopInterlinkBar()
}
