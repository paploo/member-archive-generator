package org.shintoinari.memberarchivegenerator.util

/**
 * Monadic flat mapping on the `Result` monad.
 *
 * Transforms the successful result using the provided transformation function,
 * or propagates the failure as is.
 *
 * Exceptions thrown by the transform are NOT caught.
 */
inline fun <R, T> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )

/**
 * Like functor map, but switches to transform the failure and passes the success through unchanged.
 *
 * Exceptions thrown by the transform are NOT caught.
 */
inline fun <T> Result<T>.mapFailure(transform: (Throwable) -> Throwable): Result<T> =
    fold(
        onSuccess = { Result.success(it) },
        onFailure = { Result.failure(transform(it)) }
    )