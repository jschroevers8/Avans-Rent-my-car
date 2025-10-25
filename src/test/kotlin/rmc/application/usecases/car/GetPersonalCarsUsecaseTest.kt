package rmc.application.usecases.car

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rmc.domain.entities.*
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class GetPersonalCarsUsecaseTest {

    private val carRepository: CarRepositoryInterface = mockk()
    private val carImageRepository: CarImageRepositoryInterface = mockk()
    private val usecase = GetPersonalCarsUsecase(carRepository, carImageRepository)

    @Test
    fun `should return personal cars with images successfully`() {
        val userId = 1
        val car1 = CarEntity(
            id = 10,
            fuelType = FuelType.ICE,
            userId = userId,
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "ABC123",
            mileage = "10000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )
        val car2 = CarEntity(
            id = 20,
            fuelType = FuelType.FCEV,
            userId = userId,
            bodyType = BodyType.SUV,
            brand = "Honda",
            model = "CR-V",
            modelYear = "2021",
            licensePlate = "XYZ789",
            mileage = "5000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val imagesCar1 = listOf(CarImageEntity(1, 10, image = "img1.jpg", weight = 1))
        val imagesCar2 = listOf(CarImageEntity(2, 20, image = "img2.jpg", weight = 2))

        every { carRepository.getAllCarsByUserId(userId) } returns listOf(car1, car2)
        every { carImageRepository.findByCarId(10) } returns imagesCar1
        every { carImageRepository.findByCarId(20) } returns imagesCar2

        val result = usecase(userId)

        assertEquals(listOf(
            car1.copy(carImages = imagesCar1),
            car2.copy(carImages = imagesCar2)
        ), result)

        verify { carRepository.getAllCarsByUserId(userId) }
        verify { carImageRepository.findByCarId(10) }
        verify { carImageRepository.findByCarId(20) }
    }

    @Test
    fun `should throw IllegalArgumentException when a car has null id`() {
        val userId = 1
        val car = CarEntity(
            id = null,
            fuelType = FuelType.ICE,
            userId = userId,
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "ABC123",
            mileage = "10000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )

        every { carRepository.getAllCarsByUserId(userId) } returns listOf(car)

        val exception = assertThrows<IllegalArgumentException> {
            usecase(userId)
        }

        assert(exception.message!!.contains("Cannot get car without ID"))

        verify { carRepository.getAllCarsByUserId(userId) }
    }

    @Test
    fun `should return empty list when user has no cars`() {
        val userId = 1
        every { carRepository.getAllCarsByUserId(userId) } returns emptyList()

        val result = usecase(userId)

        assertEquals(emptyList<CarEntity>(), result)
        verify { carRepository.getAllCarsByUserId(userId) }
    }
}
