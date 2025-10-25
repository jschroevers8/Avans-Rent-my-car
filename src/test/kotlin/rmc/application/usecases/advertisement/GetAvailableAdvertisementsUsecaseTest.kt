package rmc.application.usecases.advertisement

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDateTime
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAvailableAdvertisementsUsecaseTest {
    private val advertisementRepository = mockk<AdvertisementRepositoryInterface>()
    private val usecase = GetAvailableAdvertisementsUsecase(advertisementRepository)

    @Test
    fun `should return list of available advertisements`() {
        val availableAds =
            listOf(
                AdvertisementEntity(
                    carId = 1,
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
                ),
                AdvertisementEntity(
                    carId = 2,
                    address =
                        AddressEntity(
                            city = "Rotterdam",
                            street = "Second Street",
                            houseNumber = 5,
                            subHouseNumber = "A",
                            postalCode = "5678CD",
                        ),
                    availableFrom = LocalDateTime(2025, 10, 25, 12, 0),
                    availableUntil = LocalDateTime(2025, 10, 25, 12, 0),
                    price = 18000.0,
                ),
            )

        every { advertisementRepository.getAllAvailable() } returns availableAds

        val result = usecase()

        assertEquals(availableAds, result)
        verify { advertisementRepository.getAllAvailable() }
    }

    @Test
    fun `should return empty list when no available advertisements`() {
        every { advertisementRepository.getAllAvailable() } returns emptyList()

        val result = usecase()

        assertEquals(emptyList<AdvertisementEntity>(), result)
        verify { advertisementRepository.getAllAvailable() }
    }
}
