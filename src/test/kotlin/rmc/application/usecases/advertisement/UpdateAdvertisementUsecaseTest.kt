package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.presentation.dto.address.CreateAddress
import rmc.presentation.dto.advertisement.UpdateAdvertisement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UpdateAdvertisementUsecaseTest {
    private val advertisementRepository = mockk<AdvertisementRepositoryInterface>()
    private val usecase = UpdateAdvertisementUsecase(advertisementRepository)

    @Test
    fun `should update advertisement successfully`() {
        val carId = 1
        val existingAdvertisement =
            AdvertisementEntity(
                carId = carId,
                address = AddressEntity(28, "OldCity", "OldStreet", 1, null, "1234AB"),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                price = 10000.0,
            )

        val updateRequest =
            UpdateAdvertisement(
                carId = carId,
                address =
                    CreateAddress(
                        city = "NewCity",
                        street = "NewStreet",
                        houseNumber = 2,
                        subHouseNumber = "A",
                        postalCode = "5678CD",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                price = 12000.0,
            )

        val updatedAdvertisement =
            AdvertisementEntity(
                carId = carId,
                address =
                    AddressEntity(
                        city = "NewCity",
                        street = "NewStreet",
                        houseNumber = 2,
                        subHouseNumber = "A",
                        postalCode = "5678CD",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                price = 12000.0,
            )

        every { advertisementRepository.findOneByCarId(carId) } returns existingAdvertisement
        every { advertisementRepository.save(any()) } returns updatedAdvertisement

        val result = usecase(updateRequest)

        assertEquals(updatedAdvertisement, result)
        verify { advertisementRepository.findOneByCarId(carId) }
        verify { advertisementRepository.save(any()) }
    }

    @Test
    fun `should throw AdvertisementNotFoundException when advertisement does not exist`() {
        val carId = 1
        val updateRequest =
            UpdateAdvertisement(
                carId = carId,
                address =
                    CreateAddress(
                        city = "NewCity",
                        street = "NewStreet",
                        houseNumber = 2,
                        subHouseNumber = null,
                        postalCode = "5678CD",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                price = 12000.0,
            )

        every { advertisementRepository.findOneByCarId(carId) } returns null

        assertFailsWith<AdvertisementNotFoundException> {
            usecase(updateRequest)
        }
    }
}
