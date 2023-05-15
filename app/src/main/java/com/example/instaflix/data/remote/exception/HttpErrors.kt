package com.example.instaflix.data.remote.exception

import com.example.instaflix.domain.exception.BadRequestException
import com.example.instaflix.domain.exception.DomainException
import com.example.instaflix.domain.exception.HttpErrorCode
import com.example.instaflix.domain.exception.InternalErrorException
import com.example.instaflix.domain.exception.NotFoundException
import com.example.instaflix.domain.exception.Unauthorized
import com.example.instaflix.domain.exception.UnknownError
import org.json.JSONException
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

class HttpErrors {

    private val typeError = mapOf(
        HttpsURLConnection.HTTP_BAD_REQUEST to BadRequestException,
        HttpsURLConnection.HTTP_NOT_FOUND to NotFoundException,
        HttpsURLConnection.HTTP_INTERNAL_ERROR to InternalErrorException,
        HttpsURLConnection.HTTP_UNAUTHORIZED to Unauthorized,
    )

    fun getHttpError(error: HttpException): DomainException {
        return if (typeError.containsKey(error.code())) {
            typeError.getValue(error.code())
        } else {
            HttpErrorCode(error.code(), getMessage(error).message)
        }
    }

    private fun getMessage(exception: HttpException): DomainException {
        return try {
            var jsonString = exception.response()?.errorBody()?.string()
            if (jsonString.isNullOrEmpty()) jsonString = "{}"
            DomainException(jsonString)
        } catch (exception: JSONException) {
            UnknownError(exception.message ?: String())
        }
    }
}
