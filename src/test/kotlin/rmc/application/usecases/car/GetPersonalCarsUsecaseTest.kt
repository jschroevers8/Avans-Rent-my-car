package rmc.application.usecases.car

import io.mockk.*
import kotlinx.datetime.LocalDateTime
import rmc.domain.entities.BodyType
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.entities.FuelType
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPersonalCarsUsecaseTest {
    private lateinit var carRepository: CarRepositoryInterface
    private lateinit var carImageRepository: CarImageRepositoryInterface
    private lateinit var getPersonalCarsUsecase: GetPersonalCarsUsecase

    @BeforeTest
    fun setUp() {
        carRepository = mockk()
        carImageRepository = mockk()
        getPersonalCarsUsecase = GetPersonalCarsUsecase(carRepository, carImageRepository)
    }

    @Test
    fun `should return list of cars with images`() {
        val userId = 1
        val car1 =
            spyk(
                CarEntity(
                    id = 1,
                    fuelType = FuelType.FCEV,
                    userId = userId,
                    bodyType = BodyType.SEDAN,
                    brand = "Brand1",
                    model = "Model1",
                    modelYear = "2020",
                    licensePlate = "ABC123",
                    mileage = "1000",
                    createdStamp = LocalDateTime(2025, 10, 25, 10, 0),
                ),
            )
        val car2 =
            spyk(
                CarEntity(
                    id = 2,
                    fuelType = FuelType.ICE,
                    userId = userId,
                    bodyType = BodyType.HATCHBACK,
                    brand = "Brand2",
                    model = "Model2",
                    modelYear = "2021",
                    licensePlate = "XYZ789",
                    mileage = "2000",
                    createdStamp = LocalDateTime(2025, 10, 25, 10, 0),
                ),
            )

        val cars = listOf(car1, car2)

        val imagesCar1 = listOf(CarImageEntity(id = 1, carId = 1, image = "img1.png", weight = 1))
        val imagesCar2 = listOf(CarImageEntity(id = 2, carId = 2, image = "img2.png", weight = 1))

        every { carRepository.getAllCarsByUserId(userId) } returns cars
        every { carImageRepository.findByCarId(1) } returns imagesCar1
        every { carImageRepository.findByCarId(2) } returns imagesCar2
        every { car1.setImages(imagesCar1) } just runs
        every { car2.setImages(imagesCar2) } just runs

        val result = getPersonalCarsUsecase(userId)

        assertEquals(cars, result)
        verify {
            carRepository.getAllCarsByUserId(userId)
            carImageRepository.findByCarId(1)
            carImageRepository.findByCarId(2)
            car1.setImages(imagesCar1)
            car2.setImages(imagesCar2)
        }
    }
}
