package rmc.application.usecases.user

import io.ktor.client.request.request
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

        return userRepository.save(createUserEntity(userRequest))
    }

    private fun createUserEntity(request: CreateUser) =
        UserEntity(
            userType = request.userType,
            address =
                with(request.address) {
                    AddressEntity(
                        city = city,
                        street = street,
                        houseNumber = houseNumber,
                        subHouseNumber = subHouseNumber,
                        postalCode = postalCode,
                    )
                },
            email = request.email,
            password = BCrypt.hashpw(request.password, BCrypt.gensalt()),
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            userPoints = request.userPoints,
        )
}
