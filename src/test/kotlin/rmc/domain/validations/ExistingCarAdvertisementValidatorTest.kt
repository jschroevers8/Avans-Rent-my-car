package rmc.domain.validations

import kotlinx.datetime.LocalDateTime
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.exceptions.AdvertisementAlreadyExistsForCarException
import rmc.domain.repositories.AdvertisementRepositoryInterface
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ExistingCarAdvertisementValidatorTest {

    class FakeAdvertisementRepository(
        private val existingCarId: Int? = null
    ) : AdvertisementRepositoryInterface {
        override fun getAllAvailable(): List<AdvertisementEntity> {
            TODO("Not yet implemented")
        }

        override fun findOneByCarId(carId: Int): AdvertisementEntity? {
            return if (carId == existingCarId) {
                AdvertisementEntity(
                    id = 1,
                    carId = carId,
                    address = AddressEntity(
                        city = "Amsterdam",
                        street = "Damstraat",
                        houseNumber = 10,
                        postalCode = "1012JS"
                    ),
                    availableFrom = LocalDateTime(2025, 10, 25, 10, 0),
                    availableUntil = LocalDateTime(2025,11,5,18,0),
                    price = 100.0
                )
            } else null
        }

        override fun findById(id: Int): AdvertisementEntity? {
            TODO("Not yet implemented")
        }

        override fun save(advertisement: AdvertisementEntity): AdvertisementEntity {
            TODO("Not yet implemented")
        }

        override fun delete(id: Int): Boolean {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun `does not throw when no existing advertisement for car`() {
        val repository = FakeAdvertisementRepository() // geen bestaande advertentie
        val validator = ExistingCarAdvertisementValidator(repository)

        // should not throw
        validator.invoke(42)
        assertTrue(true) // bevestigt dat het is uitgevoerd zonder exception
    }

    @Test
    fun `throws AdvertisementAlreadyExistsForCarException when advertisement exists`() {
        val repository = FakeAdvertisementRepository(existingCarId = 42)
        val validator = ExistingCarAdvertisementValidator(repository)

        assertFailsWith<AdvertisementAlreadyExistsForCarException> {
            validator.invoke(42)
        }
    }
}
