package rmc.application.usecases.user

import org.mindrot.jbcrypt.BCrypt
import rmc.application.exceptions.UserAlreadyExistsException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.user.CreateUser

class SignupUsecase(
    private val userRepository: UserRepositoryInterface,
) {
    operator fun invoke(userRequest: CreateUser): UserEntity {
        if (userRepository.findByEmail(userRequest.email) != null) {
            throw UserAlreadyExistsException("User with email ${userRequest.email} already exists")
        }

        val address =
            AddressEntity(
                city = userRequest.address.city,
                street = userRequest.address.city,
                houseNumber = userRequest.address.houseNumber,
                subHouseNumber = userRequest.address.subHouseNumber,
                postalCode = userRequest.address.postalCode,
            )

        val user =
            UserEntity(
                userType = userRequest.userType,
                address = address,
                email = userRequest.email,
                password = BCrypt.hashpw(userRequest.password, BCrypt.gensalt()),
                firstName = userRequest.firstName,
                lastName = userRequest.lastName,
                phone = userRequest.phone,
                userPoints = userRequest.userPoints,
            )

        return userRepository.save(user)
    }
}
