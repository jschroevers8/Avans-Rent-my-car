package rmc.domain.validations

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.entities.CarEntity
import rmc.domain.exceptions.CarAlreadyExistsException
import rmc.domain.entities.FuelType
import rmc.domain.entities.BodyType
import kotlinx.datetime.LocalDateTime

class ExistingCarValidatorTest {

    class FakeCarRepository(
        private val existingLicensePlate: String? = null
    ) : CarRepositoryInterface {
        override fun getAllCarsByUserId(userId: Int): List<CarEntity> {
            TODO("Not yet implemented")
        }

        override fun findById(id: Int): CarEntity? {
            TODO("Not yet implemented")
        }

        override fun findByLicensePlate(licensePlate: String): CarEntity? {
            return if (licensePlate == existingLicensePlate) {
                CarEntity(
                    id = 1,
                    fuelType = FuelType.ICE,
                    userId = 1,
                    bodyType = BodyType.SEDAN,
                    brand = "Toyota",
                    model = "Corolla",
                    modelYear = "2020",
                    licensePlate = licensePlate,
                    mileage = "10000",
                    createdStamp = LocalDateTime(2025, 10, 25, 10, 0)
                )
            } else null
        }

        override fun save(car: CarEntity): CarEntity {
            TODO("Not yet implemented")
        }

        override fun delete(id: Int): Boolean {
            TODO("Not yet implemented")
        }

        override fun getAll(): List<CarEntity> {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun `does not throw when license plate does not exist`() {
        val repository = FakeCarRepository() // geen bestaande auto
        val validator = ExistingCarValidator(repository)

        validator.invoke("NEW-123") // should not throw
        assertTrue(true) // bevestigt dat het is uitgevoerd zonder exception
    }

    @Test
    fun `throws CarAlreadyExistsException when license plate exists`() {
        val repository = FakeCarRepository(existingLicensePlate = "EXIST-456")
        val validator = ExistingCarValidator(repository)

        assertFailsWith<CarAlreadyExistsException> {
            validator.invoke("EXIST-456")
        }
    }
}
