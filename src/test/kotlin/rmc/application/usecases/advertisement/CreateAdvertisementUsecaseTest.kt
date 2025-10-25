package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.BodyType
import rmc.domain.entities.CarEntity
import rmc.domain.entities.FuelType
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.validations.ExistingCarAdvertisementValidator
import rmc.presentation.dto.address.CreateAddress
import rmc.presentation.dto.advertisement.CreateAdvertisement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateAdvertisementUsecaseTest {
    // Mock repositories
    private val carRepository = mockk<CarRepositoryInterface>()
    private val advertisementRepository = mockk<AdvertisementRepositoryInterface>()

    // Use real validator with mocked repository
    private val existingCarAdvertisementValidator =
        ExistingCarAdvertisementValidator(advertisementRepository)

    private val usecase =
        CreateAdvertisementUsecase(
            carRepository,
            advertisementRepository,
            existingCarAdvertisementValidator,
        )

    @Test
    fun `should create advertisement successfully`() {
        val carId = 1
        val userId = 42
        val car =
            CarEntity(
                id = carId,
                userId = userId,
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

        val createRequest =
            CreateAdvertisement(
                carId = carId,
                address =
                    CreateAddress(
                        city = "TestCity",
                        street = "TestStreet",
                        houseNumber = 10,
                        subHouseNumber = "B",
                        postalCode = "1234AB",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 26, 12, 0),
                price = 15000.0,
            )

        val savedAdvertisement =
            AdvertisementEntity(
                carId = carId,
                address =
                    AddressEntity(
                        city = "TestCity",
                        street = "TestStreet",
                        houseNumber = 10,
                        subHouseNumber = "B",
                        postalCode = "1234AB",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 26, 12, 0),
                price = 15000.0,
            )

        // Stub repository calls
        every { carRepository.findById(carId) } returns car
        every { advertisementRepository.findOneByCarId(carId) } returns null
        every { advertisementRepository.save(any()) } returns savedAdvertisement

        val result = usecase(createRequest, userId)

        assertEquals(savedAdvertisement, result)

        // Verify interactions
        verify { carRepository.findById(carId) }
        verify { advertisementRepository.findOneByCarId(carId) }
        verify { advertisementRepository.save(any()) }
    }

    @Test
    fun `should throw CarNotFoundException when car does not exist`() {
        val carId = 1
        val userId = 42
        val createRequest =
            CreateAdvertisement(
                carId = carId,
                address =
                    CreateAddress(
                        city = "TestCity",
                        street = "TestStreet",
                        houseNumber = 10,
                        subHouseNumber = null,
                        postalCode = "1234AB",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 26, 12, 0),
                price = 15000.0,
            )

        every { carRepository.findById(carId) } returns null

        assertFailsWith<CarNotFoundException> {
            usecase(createRequest, userId)
        }
    }
}
