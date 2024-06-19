package com.example.interlink.ui.devices

import com.example.interlink.model.Door
import com.example.interlink.model.Error

// Este ui state es basicamente lo que el ui (i.e: tu device card) puede acceder, es lo que puede
// ver directamente haciendo tipo DoorUiState.currentDevice (la clase Door esta en interlink/model por si te interesa)
data class DoorUiState (
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Door? = null
)

val DoorUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading

// Ahora q leiste esto andate a DoorViewModel q tendria que estar ---------------¬
//                                                                               |
//                                                                               |
//                                                                               |
//                                                                               |
//                                                                               |
//                          Aca (si scrolleas hasta arriba lol)                  |
// <------------------------------------------------------------------------------