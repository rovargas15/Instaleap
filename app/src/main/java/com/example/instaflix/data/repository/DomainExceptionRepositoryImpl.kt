package com.example.instaflix.data.repository

import com.example.instaflix.data.remote.exception.HttpErrors
import com.example.instaflix.domain.exception.CommonErrors
import com.example.instaflix.domain.exception.DomainException
import com.example.instaflix.domain.repository.DomainExceptionRepository
import retrofit2.HttpException

class DomainExceptionRepositoryImpl(
    private val commonErrors: CommonErrors,
    private val httpErrors: HttpErrors,
) : DomainExceptionRepository {

    override fun manageError(error: Throwable): DomainException {
        return if (error is HttpException) {
            httpErrors.getHttpError(error)
        } else {
            commonErrors.manageException(error)
        }
    }
}
