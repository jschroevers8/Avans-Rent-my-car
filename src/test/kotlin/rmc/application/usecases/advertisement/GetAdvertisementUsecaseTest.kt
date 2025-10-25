package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface

class GetAdvertisementUsecaseTest {

    private val advertisementRepository: AdvertisementRepositoryInterface = mockk()
    private val usecase = GetAdvertisementUsecase(advertisementRepository)

    @Test
    fun `should return advertisement when it exists`() {
        val adId = 100
        val advertisement = AdvertisementEntity(
            id = adId,
            carId = 10,
            address = AddressEntity(
                city = "Amsterdam",
                street = "Street",
                houseNumber = 1,
                subHouseNumber = null,
                postalCode = "1234AB"
            ),
            availableFrom = LocalDateTime(2023, 10, 25, 0, 0),
            availableUntil = LocalDateTime(2023, 11, 25, 0, 0),
            price = 100.0
        )

        every { advertisementRepository.findById(adId) } returns advertisement

        val result = usecase(adId)

        assertEquals(advertisement, result)
        verify { advertisementRepository.findById(adId) }
    }

    @Test
    fun `should throw AdvertisementNotFoundException when advertisement does not exist`() {
        val adId = 100
        every { advertisementRepository.findById(adId) } returns null

        assertThrows<AdvertisementNotFoundException> {
            usecase(adId)
        }

        verify { advertisementRepository.findById(adId) }
    }
}
