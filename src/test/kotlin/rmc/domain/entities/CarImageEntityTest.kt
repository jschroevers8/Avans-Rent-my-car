package rmc.domain.entities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CarImageEntityTest {

    @Test
    fun `can create CarImageEntity with all fields`() {
        val carImage = CarImageEntity(
            id = 1,
            carId = 42,
            image = "image.png",
            weight = 1024
        )

        assertEquals(1, carImage.id)
        assertEquals(42, carImage.carId)
        assertEquals("image.png", carImage.image)
        assertEquals(1024, carImage.weight)
    }

    @Test
    fun `can create CarImageEntity without optional id`() {
        val carImage = CarImageEntity(
            carId = 99,
            image = "photo.jpg",
            weight = 2048
        )

        assertNull(carImage.id)
        assertEquals(99, carImage.carId)
        assertEquals("photo.jpg", carImage.image)
        assertEquals(2048, carImage.weight)
    }
}
