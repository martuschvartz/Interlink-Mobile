package com.example.interlink.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interlink.DataSourceException
import com.example.interlink.model.Speaker
import com.example.interlink.model.Error
import com.example.interlink.repository.DeviceRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpeakerViewModel(
    private val repository: DeviceRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState = _uiState.asStateFlow()

//    init {
//        collectOnViewModelScope(
//            repository.currentDevice
//        ) { state, response -> state.copy(currentDevice = response as Speaker?) }
//    }

    fun setCurrentDevice(device: Speaker) {
        _uiState.update { it.copy(currentDevice = device) }
    }

    fun play() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PLAY_ACTION) },
        { state, response : Boolean -> state }
    )

    fun stop() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.STOP_ACTION) },
        { state, response : Boolean -> state }
    )

    fun pause() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PAUSE_ACTION) },
        { state, response : Boolean -> state }
    )

    fun resume() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.RESUME_ACTION) },
        { state, response : Boolean -> state }
    )

    fun setVolume(volume: Int) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.SET_VOLUME_ACTION, arrayOf(volume)) },
        { state, response : Int -> state }
    )

    fun setGenre(genre: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.SET_GENRE_ACTION, arrayOf(genre)) },
        { state, response : String -> state }
    )


    fun nextSong() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.NEXT_SONG_ACTION) },
        { state, response : Boolean -> state }
    )

    fun previousSong() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PREVIOUS_SONG_ACTION) },
        { state, response : Boolean -> state }
    )

    fun getPlaylist() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.GET_PLAYLIST_ACTION) },
        { state, response : JsonObject -> state }
    )



    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (SpeakerUiState, T) -> SpeakerUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (SpeakerUiState, R) -> SpeakerUiState
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { updateState(it, response).copy(loading = false) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

}