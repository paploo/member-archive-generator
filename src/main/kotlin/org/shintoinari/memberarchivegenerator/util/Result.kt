package org.shintoinari.memberarchivegenerator.util

inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )

inline fun <T> Result<T>.mapFailure(transform: (Throwable) -> Throwable): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(transform(it)) }
    )