package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetAdvertisementUsecaseTest {
    private val advertisementRepository = mockk<AdvertisementRepositoryInterface>()
    private val usecase = GetAdvertisementUsecase(advertisementRepository)

    @Test
    fun `should return advertisement when it exists`() {
        val advertisementId = 1
        val advertisement =
            AdvertisementEntity(
                carId = 42,
                address =
                    AddressEntity(
                        city = "Amsterdam",
                        street = "Main Street",
                        houseNumber = 10,
                        subHouseNumber = null,
                        postalCode = "1234AB",
                    ),
                availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                price = 15000.0,
            )

        every { advertisementRepository.findById(advertisementId) } returns advertisement

        val result = usecase(advertisementId)

        assertEquals(advertisement, result)
        verify { advertisementRepository.findById(advertisementId) }
    }

    @Test
    fun `should throw AdvertisementNotFoundException when advertisement does not exist`() {
        val advertisementId = 1

        every { advertisementRepository.findById(advertisementId) } returns null

        assertFailsWith<AdvertisementNotFoundException> {
            usecase(advertisementId)
        }
        verify { advertisementRepository.findById(advertisementId) }
    }
}
