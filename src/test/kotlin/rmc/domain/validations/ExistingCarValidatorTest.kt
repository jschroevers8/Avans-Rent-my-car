package rmc.domain.validations

import io.mockk.every
import io.mockk.mockk
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

    @Test
    fun `does not throw when license plate does not exist`() {
        val repository = mockk<CarRepositoryInterface>()
        every { repository.findByLicensePlate("NEW-123") } returns null

        val validator = ExistingCarValidator(repository)

        validator.invoke("NEW-123") // should not throw
        assertTrue(true)
    }

    @Test
    fun `throws CarAlreadyExistsException when license plate exists`() {
        val repository = mockk<CarRepositoryInterface>()
        val existingCar = CarEntity(
            id = 1,
            fuelType = FuelType.ICE,
            userId = 1,
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "EXIST-456",
            mileage = "10000",
            createdStamp = LocalDateTime(2025, 10, 25, 10, 0)
        )

        every { repository.findByLicensePlate("EXIST-456") } returns existingCar

        val validator = ExistingCarValidator(repository)

        assertFailsWith<CarAlreadyExistsException> {
            validator.invoke("EXIST-456")
        }
    }
}
