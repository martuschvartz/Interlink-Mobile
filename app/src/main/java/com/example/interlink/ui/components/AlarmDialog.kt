package com.example.interlink.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.interlink.ui.theme.md_theme_light_background


@Composable
fun AlarmDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
){
    Dialog(onDismissRequest){
        ElevatedCard(
           modifier = Modifier
               .size(310.dp, 226.dp)
               .border(3.dp, Color.Black, RoundedCornerShape(10.dp)),
           shape = RoundedCornerShape(10.dp),
           colors = CardDefaults.cardColors(
               containerColor = md_theme_light_background,
               contentColor = Color.Black
           ),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ingrese el PIN",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview()
@Composable
fun AlarmDialogPreview(){
    Scaffold (
        containerColor = Color.White
    ){
        AlarmDialog(onDismissRequest = { /*TODO*/ }, {  })
    }

}