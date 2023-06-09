package com.example.instaflix.data.repository

import com.example.instaflix.domain.exception.DomainException
import com.example.instaflix.domain.exception.InternetException
import com.example.instaflix.domain.exception.PermissionDeniedException
import com.example.instaflix.domain.exception.UnknowException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {
    inline fun <T, R> T.launchResultSafe(block: T.() -> R): Result<R> {
        return runCatching {
            block()
        }.onFailure()
    }

    inline fun <T, R> T.launchSafe(block: T.() -> R): R {
        return runCatching {
            block()
        }.getOrElse { throw getDomainException(it) }
    }

    /**
     * The method onFailure
     * uses methods getDomainExceptionFromRetrofitException
     * and getDomainNativeException to convert the appropriate
     * type of exception to a domain exception.
     */
    fun <T> Result<T>.onFailure(): Result<T> {
        return exceptionOrNull()?.let { error ->
            when (error) {
                is retrofit2.HttpException -> {
                    Result.failure(getDomainExceptionFromRetrofitException(error))
                }

                is UnknownHostException -> {
                    Result.failure(InternetException())
                }

                else -> {
                    Result.failure(getDomainExceptionFromNativeException(error))
                }
            }
        } ?: run {
            return this
        }
    }

    /**
     * The method getDomainException
     * uses methods getDomainExceptionFromRetrofitException
     * and getDomainNativeException to convert the appropriate
     * type of exception to a domain exception.
     */
    fun getDomainException(error: Throwable): DomainException {
        return when (error) {
            is retrofit2.HttpException -> {
                getDomainExceptionFromRetrofitException(error)
            }

            else -> {
                getDomainExceptionFromNativeException(error)
            }
        }
    }

    private fun getDomainExceptionFromRetrofitException(
        error: retrofit2.HttpException,
    ): DomainException {
        val httpCode = error.code()
        return if (httpCode == HttpURLConnection.HTTP_UNAUTHORIZED || httpCode == HttpURLConnection.HTTP_FORBIDDEN) {
            PermissionDeniedException()
        } else {
            UnknowException()
        }
    }

    private fun getDomainExceptionFromNativeException(error: Throwable): DomainException {
        return if (error is SocketTimeoutException || error is ConnectException || error is UnknownHostException) {
            InternetException()
        } else {
            UnknowException()
        }
    }
}
