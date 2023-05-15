package com.example.instaflix.domain.repository

import com.example.instaflix.domain.exception.DomainException

interface DomainExceptionRepository {
    fun manageError(error: Throwable): DomainException
}
