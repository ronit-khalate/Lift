package com.ronit.liftlog.core.domain

sealed interface RealmResponse<T> {

    data class Success<T>(val data:T):RealmResponse<T>
    data class Error<T>(val error:Exception):RealmResponse<T>
}