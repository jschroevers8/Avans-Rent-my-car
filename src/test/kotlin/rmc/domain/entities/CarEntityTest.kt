package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import rmc.domain.exceptions.UnauthorizedCarAccessException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class CarEntityTest {

    @Test
    fun `can create CarEntity with all fields`() {
        val createdStamp = LocalDateTime(2025, 10, 25, 10, 0)

        val car = CarEntity(
            id = 1,
            fuelType = FuelType.FCEV,
            userId = 42,
            bodyType = BodyType.HATCHBACK,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "AB-123-CD",
            mileage = "15000",
            createdStamp = createdStamp
        )

        assertEquals(1, car.id)
        assertEquals(FuelType.FCEV, car.fuelType)
        assertEquals(42, car.userId)
        assertEquals(BodyType.HATCHBACK, car.bodyType)
        assertEquals("Toyota", car.brand)
        assertEquals("Corolla", car.model)
        assertEquals("2020", car.modelYear)
        assertEquals("AB-123-CD", car.licensePlate)
        assertEquals("15000", car.mileage)
        assertEquals(createdStamp, car.createdStamp)
        assertEquals(emptyList<CarImageEntity>(), car.carImages)
    }

    @Test
    fun `can create CarEntity without optional id`() {
        val createdStamp = LocalDateTime(2025, 10, 25, 10, 0)

        val car = CarEntity(
            fuelType = FuelType.ICE,
            userId = 50,
            bodyType = BodyType.HATCHBACK,
            brand = "Volkswagen",
            model = "Golf",
            modelYear = "2019",
            licensePlate = "XY-987-ZT",
            mileage = "30000",
            createdStamp = createdStamp
        )

        assertNull(car.id)
        assertEquals(FuelType.ICE, car.fuelType)
        assertEquals(50, car.userId)
        assertEquals(BodyType.HATCHBACK, car.bodyType)
        assertEquals("Volkswagen", car.brand)
        assertEquals("Golf", car.model)
        assertEquals("2019", car.modelYear)
        assertEquals("XY-987-ZT", car.licensePlate)
        assertEquals("30000", car.mileage)
        assertEquals(createdStamp, car.createdStamp)
    }

    @Test
    fun `can set carImages`() {
        val car = CarEntity(
            fuelType = FuelType.ICE,
            userId = 1,
            bodyType = BodyType.SUV,
            brand = "Tesla",
            model = "Model X",
            modelYear = "2022",
            licensePlate = "TES-1234",
            mileage = "5000",
            createdStamp = LocalDateTime(2025, 10, 25, 10, 0)
        )

        val images = listOf(
            CarImageEntity(carId = 1, image = "img1.png", weight = 1024)
        )

        car.setImages(images)
        assertEquals(images, car.carImages)
    }

    @Test
    fun `ensureOwnedBy succeeds when user is owner`() {
        val car = CarEntity(
            id = 1,
            fuelType = FuelType.BEV,
            userId = 42,
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "AB-123-CD",
            mileage = "15000",
            createdStamp = LocalDateTime(2025, 10, 25, 10, 0)
        )

        car.ensureOwnedBy(42) // should not throw
    }

    @Test
    fun `ensureOwnedBy throws when user is not owner`() {
        val car = CarEntity(
            id = 1,
            fuelType = FuelType.ICE,
            userId = 42,
            bodyType = BodyType.SEDAN,
            brand = "Toyota",
            model = "Corolla",
            modelYear = "2020",
            licensePlate = "AB-123-CD",
            mileage = "15000",
            createdStamp = LocalDateTime(2025, 10, 25, 10, 0)
        )

        assertFailsWith<UnauthorizedCarAccessException> {
            car.ensureOwnedBy(99)
        }
    }
}
