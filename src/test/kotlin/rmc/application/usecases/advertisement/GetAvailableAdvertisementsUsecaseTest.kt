package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface

class GetAvailableAdvertisementsUsecaseTest {

    private val advertisementRepository: AdvertisementRepositoryInterface = mockk()
    private val usecase = GetAvailableAdvertisementsUsecase(advertisementRepository)

    @Test
    fun `should return all available advertisements`() {
        val advertisements = listOf(
            AdvertisementEntity(
                id = 1,
                carId = 10,
                address = AddressEntity(28,"Amsterdam", "Street 1", 1, null, "1234AB"),
                availableFrom = LocalDateTime(2023, 10, 25, 0, 0),
                availableUntil = LocalDateTime(2023, 11, 25, 0, 0),
                price = 100.0
            ),
            AdvertisementEntity(
                id = 2,
                carId = 20,
                address = AddressEntity(28,"Rotterdam", "Street 2", 2, null, "5678CD"),
                availableFrom = LocalDateTime(2023, 10, 26, 0, 0),
                availableUntil = LocalDateTime(2023, 11, 26, 0, 0),
                price = 150.0
            )
        )

        every { advertisementRepository.getAllAvailable() } returns advertisements

        val result = usecase()

        assertEquals(advertisements, result)
        verify { advertisementRepository.getAllAvailable() }
    }

    @Test
    fun `should return empty list when no advertisements are available`() {
        every { advertisementRepository.getAllAvailable() } returns emptyList()

        val result = usecase()

        assertEquals(emptyList<AdvertisementEntity>(), result)
        verify { advertisementRepository.getAllAvailable() }
    }
}
