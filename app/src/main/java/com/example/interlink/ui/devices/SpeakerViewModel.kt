package com.example.interlink.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interlink.DataSourceException
import com.example.interlink.model.Speaker
import com.example.interlink.model.Error
import com.example.interlink.repository.DeviceRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

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
        { state, response : List<Any> -> state },
    )

    suspend fun fetchPlaylist() : List<Any?>?{
        val resultDeferred = getPlaylist()
        return resultDeferred.await()
    }


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
    ): CompletableDeferred<R?> {
        val resultDeferred = CompletableDeferred<R?>()

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }
            runCatching {
                block()
            }.onSuccess { response ->
                _uiState.update { updateState(it, response).copy(loading = false) }
                Log.d("DEBUG", "Mandamos $response")
                resultDeferred.complete(response)
            }.onFailure { e ->
                _uiState.update { it.copy(loading = false, error = handleError(e)) }
                resultDeferred.complete(null)
            }
        }

        return resultDeferred
    }

//    private fun <R> runOnViewModelScope(
//        block: suspend () -> R,
//        updateState: (SpeakerUiState, R) -> SpeakerUiState
//    ): Job = viewModelScope.launch {
//        _uiState.update { it.copy(loading = true, error = null) }
//        runCatching {
//            block()
//        }.onSuccess { response ->
//            val result = if (response is List<*>) response else emptyList<Any>()
//
//            _uiState.update { updateState(it, response).copy(loading = false, playlist = result) }
//            Log.d("DEBUG", "${_uiState.value}")
//        }.onFailure { e ->
//            _uiState.update { it.copy(loading = false, error = handleError(e)) }
//        }
//    }

    private fun <R> updateOnViewModelScope(
        block: suspend () -> R,
        updateState: (SpeakerUiState, R) -> SpeakerUiState,
        stateToChange: MutableStateFlow<SpeakerUiState>
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        stateToChange.update { it.copy(loading = true, error = null) }
        Log.d("DEBUG", "${stateToChange}")
        runCatching {
            block()
        }.onSuccess { response ->
            val result = if (response is List<*>) response else emptyList<Any>()
            stateToChange.update { updateState(it, response).copy(loading = false, playlist = result) }
            Log.d("DEBUG", "$stateToChange")
            _uiState.update { updateState(it, response).copy(loading = false, playlist = result) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
            stateToChange.update { it.copy(loading = false, error = handleError(e)) }
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