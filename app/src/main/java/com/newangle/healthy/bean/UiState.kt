package com.newangle.healthy.bean


sealed interface UiState<out T> {
    object INIT : UiState<Nothing>
    object LOADING : UiState<Nothing>
    data class SUCCESS<out T>(val data: T) :UiState<T>
    data class FAILED(val code : Int, val msg : String) : UiState<Nothing>

}