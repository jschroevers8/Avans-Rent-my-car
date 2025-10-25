package rmc.domain.exceptions

class CarAlreadyExistsException(message: String) : RuntimeException(message)

class UnauthorizedCarAccessException(message: String) : RuntimeException(message)

class AdvertisementAlreadyExistsForCarException(message: String) : RuntimeException(message)

class RentalNotPendingException(message: String) : RuntimeException(message)

class RentalCannotBeCancelledException(message: String) : RuntimeException(message)
