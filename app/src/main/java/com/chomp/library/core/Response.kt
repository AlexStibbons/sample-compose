package com.chomp.library.core

import com.chomp.library.core.Failure as FailureRes

val EMPTY_SUCCESS =
    Response.success(Unit)

sealed class Response<out F: FailureRes, out S: Any> {

    data class Failure<out F : FailureRes>(val failure: F): Response<F, Nothing>()

    data class Success<out S: Any>(val success: S): Response<Nothing, S>()

    val isSuccess get() = this is Success<S>

    val isFailure get() = this is Failure<F>

    companion object {

        fun <F: FailureRes> failure(f: F) =
            Failure(f)

        fun <S: Any> success(s: S) =
            Success(s)
    }

    fun doOnSuccess(fn: (S: Any) -> Unit) {
        if (this is Success) fn(success)
    }

    fun doOnFailure(fn: (F: FailureRes)-> Unit) {
        if (this is Failure) fn(failure)
    }
}

fun <F: FailureRes, S: Any> Response<F, S>.getSuccessOrElse(value: S): S = when (this) {
    is Response.Failure -> value
    is Response.Success -> this.success
}


fun <F: FailureRes, S: Any> Response<F, S>.getSuccessOrNull(): S? = when (this) {
    is Response.Failure -> null
    is Response.Success -> this.success
}

fun <F: FailureRes, S: Any> Response<F, S>.getSuccessOrThrow(message: String = "Response failed with: "): S = when (this) {
    is Response.Failure -> throw error("$message ${this.failure}")
    is Response.Success -> this.success
}

fun <F: FailureRes, S: Any, T: Any> Response<F, S>.mapSuccessTo(onSuccess: (S) -> (T)): Response<F, T> {
    return when (this) {
        is Response.Failure -> this
        is Response.Success -> Response.success(onSuccess(this.success))
    }
}