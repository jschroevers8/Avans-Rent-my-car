package rmc.application.usecases.advertisement

import io.mockk.*
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.*
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.validations.ExistingCarAdvertisementValidator
import rmc.presentation.dto.advertisement.CreateAdvertisement

class CreateAdvertisementUsecaseTest {

    private lateinit var carRepository: CarRepositoryInterface
    private lateinit var advertisementRepository: AdvertisementRepositoryInterface
    private lateinit var existingCarAdvertisementValidator: ExistingCarAdvertisementValidator
    private lateinit var usecase: CreateAdvertisementUsecase

    @BeforeEach
    fun setup() {
        carRepository = mockk()
        advertisementRepository = mockk()
        existingCarAdvertisementValidator = mockk()
        usecase = CreateAdvertisementUsecase(
            carRepository,
            advertisementRepository,
            existingCarAdvertisementValidator
        )
    }

    @Test
    fun `should create advertisement successfully`() {
        val userId = 1
        val carId = 10
        val car = CarEntity(
            id = carId,
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

        val request = CreateAdvertisement(
            carId = carId,
            address = rmc.presentation.dto.address.CreateAddress(
                city = "Amsterdam",
                street = "Main Street",
                houseNumber = 10,
                subHouseNumber = null,
                postalCode = "1234AB"
            ),
            availableFrom = LocalDateTime(2023, 10, 25, 0, 0),
            availableUntil = LocalDateTime(2023, 11, 25, 0, 0),
            price = 100.0
        )

        val savedAdvertisement = AdvertisementEntity(
            id = 1,
            carId = carId,
            address = AddressEntity(
                city = "Amsterdam",
                street = "Main Street",
                houseNumber = 10,
                subHouseNumber = null,
                postalCode = "1234AB"
            ),
            availableFrom = request.availableFrom,
            availableUntil = request.availableUntil,
            price = 100.0
        )

        every { carRepository.findById(carId) } returns car
        every { existingCarAdvertisementValidator(carId) } just Runs
        every { advertisementRepository.save(any()) } returns savedAdvertisement

        val result = usecase(request, userId)

        assertEquals(savedAdvertisement, result)
        verify { carRepository.findById(carId) }
        verify { existingCarAdvertisementValidator(carId) }
        verify { advertisementRepository.save(any()) }
    }

    @Test
    fun `should throw CarNotFoundException when car does not exist`() {
        val userId = 1
        val carId = 10
        val request = CreateAdvertisement(
            carId = carId,
            address = rmc.presentation.dto.address.CreateAddress(
                city = "Amsterdam",
                street = "Main Street",
                houseNumber = 10,
                subHouseNumber = null,
                postalCode = "1234AB"
            ),
            availableFrom = LocalDateTime(2023, 10, 25, 0, 0),
            availableUntil = LocalDateTime(2023, 11, 25, 0, 0),
            price = 100.0
        )

        every { carRepository.findById(carId) } returns null

        assertThrows(CarNotFoundException::class.java) {
            usecase(request, userId)
        }
        verify { carRepository.findById(carId) }
    }

    @Test
    fun `should throw UnauthorizedCarAccessException when user does not own car`() {
        val carId = 10
        val car = CarEntity(
            id = carId,
            fuelType = FuelType.ICE,
            userId = 2, // different owner
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "ABC123",
            mileage = "10000",
            createdStamp = LocalDateTime(2023, 1, 1, 0, 0)
        )
        val request = CreateAdvertisement(
            carId = carId,
            address = rmc.presentation.dto.address.CreateAddress(
                city = "Amsterdam",
                street = "Main Street",
                houseNumber = 10,
                subHouseNumber = null,
                postalCode = "1234AB"
            ),
            availableFrom = LocalDateTime(2023, 10, 25, 0, 0),
            availableUntil = LocalDateTime(2023, 11, 25, 0, 0),
            price = 100.0
        )

        every { carRepository.findById(carId) } returns car

        assertThrows(rmc.domain.exceptions.UnauthorizedCarAccessException::class.java) {
            usecase(request, userId = 1)
        }
        verify { carRepository.findById(carId) }
    }
}
