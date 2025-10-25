package rmc.domain.validations

import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.AddressEntity
import rmc.domain.exceptions.AdvertisementAlreadyExistsForCarException
import kotlinx.datetime.LocalDateTime

class ExistingCarAdvertisementValidatorTest {

    @Test
    fun `does not throw when no existing advertisement for car`() {
        val repository = mockk<AdvertisementRepositoryInterface>()
        every { repository.findOneByCarId(42) } returns null

        val validator = ExistingCarAdvertisementValidator(repository)

        validator.invoke(42) // should not throw
        assertTrue(true) // bevestigt dat het is uitgevoerd zonder exception
    }

    @Test
    fun `throws AdvertisementAlreadyExistsForCarException when advertisement exists`() {
        val repository = mockk<AdvertisementRepositoryInterface>()
        val existingAd = AdvertisementEntity(
            id = 1,
            carId = 42,
            address = AddressEntity(
                city = "Amsterdam",
                street = "Damstraat",
                houseNumber = 10,
                postalCode = "1012JS"
            ),
            availableFrom = LocalDateTime(2025,10,25,10,0),
            availableUntil = LocalDateTime(2025,11,5,18,0),
            price = 100.0
        )

        every { repository.findOneByCarId(42) } returns existingAd

        val validator = ExistingCarAdvertisementValidator(repository)

        assertFailsWith<AdvertisementAlreadyExistsForCarException> {
            validator.invoke(42)
        }
    }
}
