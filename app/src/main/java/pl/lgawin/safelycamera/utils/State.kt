package pl.lgawin.safelycamera.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CancellationException
import java.lang.Exception

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Failure(val error: Throwable) : State<Nothing>()
}

suspend fun <T> MutableLiveData<State<T>>.loadData(provider: suspend () -> T) {
    try {
        value = State.Loading
        value = State.Success(provider())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        value = State.Failure(e)
    }
}
