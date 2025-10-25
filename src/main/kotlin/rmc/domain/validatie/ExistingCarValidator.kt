package rmc.domain.validatie

import rmc.domain.exceptions.CarAlreadyExistsException
import rmc.domain.repositories.CarRepositoryInterface

class ExistingCarValidator(private val carRepository: CarRepositoryInterface) {
    operator fun invoke(licensePlate: String) {
        if (carRepository.findByLicensePlate(licensePlate) != null) {
            throw CarAlreadyExistsException("Car with this license plate already exists")
        }
    }
}
