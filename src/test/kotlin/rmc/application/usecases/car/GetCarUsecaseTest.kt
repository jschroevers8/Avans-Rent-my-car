package rmc.application.usecases.car

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class GetCarUsecaseTest {

    private val carRepository: CarRepositoryInterface = mockk()
    private val carImageRepository: CarImageRepositoryInterface = mockk()
    private val usecase = GetCarUsecase(carRepository, carImageRepository)

    @Test
    fun `should return car with images successfully`() {
        val carId = 10
        val car = CarEntity(
            id = carId,
            fuelType = rmc.domain.entities.FuelType.BEV,
            userId = 1,
            bodyType = rmc.domain.entities.BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "ABC123",
            mileage = "10000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val images = listOf(
            CarImageEntity(id = 1, carId = carId, image = "image1.jpg", weight = 1),
            CarImageEntity(id = 2, carId = carId, image = "image2.jpg", weight = 2)
        )

        every { carRepository.findById(carId) } returns car
        every { carImageRepository.findByCarId(carId) } returns images

        val result = usecase(carId)

        assertEquals(car.copy(carImages = images), result)
        verify { carRepository.findById(carId) }
        verify { carImageRepository.findByCarId(carId) }
    }

    @Test
    fun `should throw CarNotFoundException when car does not exist`() {
        val carId = 10
        every { carRepository.findById(carId) } returns null

        assertThrows<CarNotFoundException> {
            usecase(carId)
        }

        verify { carRepository.findById(carId) }
    }

    @Test
    fun `should throw IllegalArgumentException when car id is null`() {
        val car = CarEntity(
            id = null,
            fuelType = rmc.domain.entities.FuelType.ICE,
            userId = 1,
            bodyType = rmc.domain.entities.BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "ABC123",
            mileage = "10000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )

        every { carRepository.findById(1) } returns car

        val exception = assertThrows<IllegalArgumentException> {
            usecase(1)
        }

        assert(exception.message!!.contains("Cannot get car without ID"))

        verify { carRepository.findById(1) }
    }
}
