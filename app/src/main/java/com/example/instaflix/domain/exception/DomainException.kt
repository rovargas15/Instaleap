package com.example.instaflix.domain.exception

sealed class DomainException(
    message: String? = null,
    cause: Throwable? = null,
) : Throwable(message, cause)

data class PermissionDeniedException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)

data class UnknowException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)

data class InternetException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : DomainException(message, cause)
