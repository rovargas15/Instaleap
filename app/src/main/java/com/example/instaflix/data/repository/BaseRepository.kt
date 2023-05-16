package com.example.instaflix.data.repository

import com.example.instaflix.domain.exception.DomainException
import com.example.instaflix.domain.exception.PermissionDeniedException
import com.example.instaflix.domain.exception.TimeOutException
import com.example.instaflix.domain.exception.UnknowException
import java.net.HttpURLConnection

open class BaseRepository {
    inline fun <T, R> T.launchSafe(block: T.() -> R): R {
        return runCatching {
            block()
        }.getOrElse { throw getDomainException(it) }
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
        return if (error is java.net.SocketTimeoutException) {
            TimeOutException()
        } else {
            UnknowException()
        }
    }
}
