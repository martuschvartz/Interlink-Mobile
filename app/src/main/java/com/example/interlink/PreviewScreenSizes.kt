package com.example.interlink

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    group = "screen sizes",
    device = Devices.PHONE
)

@Preview(
    name = "tablet",
    group = "screen sizes",
    device = Devices.TABLET
)

annotation class PreviewScreenSizes()
