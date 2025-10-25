package rmc.application.usecases.car

import io.mockk.*
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetCarUsecaseTest {
    private lateinit var carRepository: CarRepositoryInterface
    private lateinit var carImageRepository: CarImageRepositoryInterface
    private lateinit var getCarUsecase: GetCarUsecase

    @BeforeTest
    fun setUp() {
        carRepository = mockk()
        carImageRepository = mockk()
        getCarUsecase = GetCarUsecase(carRepository, carImageRepository)
    }

    @Test
    fun `should return car with images`() {
        val carId = 1
        val car =
            spyk(
                CarEntity(
                    id = carId,
                    fuelType = rmc.domain.entities.FuelType.ICE,
                    userId = 1,
                    bodyType = rmc.domain.entities.BodyType.SEDAN,
                    brand = "Brand",
                    model = "Model",
                    modelYear = "2020",
                    licensePlate = "TEST123",
                    mileage = "1000",
                    createdStamp = LocalDateTime(2025, 10, 25, 10, 0),
                ),
            )

        val images =
            listOf(
                CarImageEntity(id = 1, carId = carId, image = "image1.png", weight = 1),
                CarImageEntity(id = 2, carId = carId, image = "image2.png", weight = 2),
            )

        every { carRepository.findById(carId) } returns car
        every { carImageRepository.findByCarId(carId) } returns images
        every { car.setImages(images) } just runs

        val result = getCarUsecase(carId)

        assertEquals(car, result)
        verify {
            carRepository.findById(carId)
            carImageRepository.findByCarId(carId)
            car.setImages(images)
        }
    }

    @Test
    fun `should throw CarNotFoundException when car does not exist`() {
        val carId = 1
        every { carRepository.findById(carId) } returns null

        assertFailsWith<CarNotFoundException> {
            getCarUsecase(carId)
        }

        verify { carRepository.findById(carId) }
    }
}
