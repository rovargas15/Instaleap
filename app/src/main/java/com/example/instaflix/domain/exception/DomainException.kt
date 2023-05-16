package com.example.instaflix.domain.exception

sealed class DomainException(
    message: String? = null,
    cause: Throwable? = null,
) : Throwable(message, cause)

class PermissionDeniedException(
    message: String? = null,
    cause: Throwable? = null,
) : DomainException(message, cause)

class UnknowException(
    message: String? = null,
    cause: Throwable? = null,
) : DomainException(message, cause)

class TimeOutException(
    message: String? = null,
    cause: Throwable? = null,
) : DomainException(message, cause)
