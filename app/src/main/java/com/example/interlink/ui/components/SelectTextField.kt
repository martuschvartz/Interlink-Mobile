
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.interlink.ui.theme.md_theme_light_background
import com.example.interlink.ui.theme.md_theme_light_coffee
import com.example.interlink.ui.theme.md_theme_light_interblue


// initialValue es el valor inicial o por default que va a tener seleccionado,
// la idea es que pair tenga si o si un texto y opcionalmente un imageVector, que se pone
// por ejemplo en el caso del modo del AC
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTextField(
    modifier: Modifier = Modifier,
    initialValue: Pair<Pair<String,String>, ImageVector?>,
    options: List<Pair<Pair<String,String>, ImageVector?>>,
    showIcon: Boolean,
    onValueChanged: (String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(initialValue) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ){

        if(showIcon){
            OutlinedTextField(
                modifier = modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = "",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    selectedValue.second?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                trailingIcon = { MyIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = md_theme_light_background,
                    unfocusedContainerColor = md_theme_light_background,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = md_theme_light_interblue,
                    unfocusedBorderColor = md_theme_light_coffee
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        else{
            OutlinedTextField(
                modifier = modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = selectedValue.first.second,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = { MyIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = md_theme_light_background,
                    unfocusedContainerColor = md_theme_light_background,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = md_theme_light_interblue,
                    unfocusedBorderColor = md_theme_light_coffee
                ),
                shape = RoundedCornerShape(10.dp),
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded=false }
        ) {
            options.forEach{option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.first.second,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    leadingIcon = {
                        option.second?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    },
                    onClick = {
                        selectedValue = option
                        expanded = false
                        onValueChanged(selectedValue.first.first)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun MyIcon(
    expanded: Boolean,
    modifier: Modifier = Modifier
){
    Icon(
        Icons.Filled.KeyboardArrowDown,
        null,
        modifier
            .size(50.dp)
            .rotate(if (expanded) 180f else 0f),
        tint = Color.Black
    )
}