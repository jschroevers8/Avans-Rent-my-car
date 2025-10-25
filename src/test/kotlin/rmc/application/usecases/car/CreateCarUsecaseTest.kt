package rmc.application.usecases.car

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.BodyType
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.entities.FuelType
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.domain.validations.ExistingCarValidator
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.dto.image.CreateCarImage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateCarUsecaseTest {
    private val carRepository = mockk<CarRepositoryInterface>()
    private val userRepository = mockk<UserRepositoryInterface>()
    private val existingCarValidator = mockk<ExistingCarValidator>(relaxed = true)
    private val carImageRepository = mockk<CarImageRepositoryInterface>()
    private val usecase = CreateCarUsecase(carRepository, userRepository, existingCarValidator, carImageRepository)

    @Test
    fun `should create car with images successfully`() {
        val userId = 1
        val carRequest =
            CreateCar(
                fuelType = FuelType.ICE,
                bodyType = BodyType.SEDAN,
                brand = "Toyota",
                model = "Corolla",
                modelYear = "2020",
                licensePlate = "ABC123",
                mileage = "10000",
                createdStamp = LocalDateTime(2025, 10, 25, 12, 0),
                carImages =
                    listOf(
                        CreateCarImage("image1.png"),
                        CreateCarImage("image2.png"),
                    ),
            )

        val savedCar =
            CarEntity(
                id = 42,
                fuelType = carRequest.fuelType,
                userId = userId,
                bodyType = carRequest.bodyType,
                brand = carRequest.brand,
                model = carRequest.model,
                modelYear = carRequest.modelYear,
                licensePlate = carRequest.licensePlate,
                mileage = carRequest.mileage,
                createdStamp = carRequest.createdStamp,
            )

        val savedImages =
            carRequest.carImages.mapIndexed { index, image ->
                CarImageEntity(
                    id = index + 1,
                    carId = savedCar.id!!,
                    image = image.image,
                    weight = index + 1,
                )
            }

        every { userRepository.findById(userId) } returns mockk(relaxed = true)
        every { carRepository.save(any()) } returns savedCar
        carRequest.carImages.forEachIndexed { index, image ->
            every { carImageRepository.save(any()) } returns savedImages[index]
        }

        val result = usecase(carRequest, userId)

        assertEquals(savedCar.copy(carImages = savedImages), result)
        verify { userRepository.findById(userId) }
        verify { existingCarValidator(carRequest.licensePlate) }
        verify { carRepository.save(any()) }
        carRequest.carImages.forEach { verify { carImageRepository.save(any()) } }
    }

    @Test
    fun `should throw UserNotFoundException when user does not exist`() {
        val userId = 1
        val carRequest =
            CreateCar(
                fuelType = FuelType.ICE,
                bodyType = BodyType.SEDAN,
                brand = "Toyota",
                model = "Corolla",
                modelYear = "2020",
                licensePlate = "ABC123",
                mileage = "10000",
                createdStamp = LocalDateTime(2025, 10, 25, 12, 0),
                carImages = emptyList(),
            )

        every { userRepository.findById(userId) } returns null

        assertFailsWith<UserNotFoundException> {
            usecase(carRequest, userId)
        }
    }
}
