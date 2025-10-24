package rmc.application.exceptions

class InvalidCredentialsException(message: String) : RuntimeException(message)

class CarAlreadyExistsException(message: String) : RuntimeException(message)

class CarNotFoundException(message: String) : RuntimeException(message)

class CarHasActiveRentalException(message: String) : RuntimeException(message)

class UserNotFoundException(message: String) : RuntimeException(message)

class UserAlreadyExistsException(message: String) : RuntimeException(message)

class UnauthorizedCarAccessException(message: String) : RuntimeException(message)

class AdvertisementAlreadyExistsForCarException(message: String) : RuntimeException(message)

class AdvertisementNotFoundException(message: String) : RuntimeException(message)

class AdvertisementHasActiveRentalException(message: String) : RuntimeException(message)

class RentalNotFoundException(message: String) : RuntimeException(message)

class RentalNotPendingException(message: String) : RuntimeException(message)

class RentalCannotBeCancelledException(message: String) : RuntimeException(message)
