package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RentalTripEntityTest {
    @Test
    fun `can create RentalTripEntity with all fields`() {
        val startDate = LocalDateTime(2025, 10, 25, 10, 0)
        val endDate = LocalDateTime(2025, 10, 30, 18, 0)

        val trip =
            RentalTripEntity(
                id = 1,
                rentalId = 123,
                startMileage = 1000,
                endMileage = 1500,
                startDate = startDate,
                endDate = endDate,
            )

        assertEquals(1, trip.id)
        assertEquals(123, trip.rentalId)
        assertEquals(1000, trip.startMileage)
        assertEquals(1500, trip.endMileage)
        assertEquals(startDate, trip.startDate)
        assertEquals(endDate, trip.endDate)
    }

    @Test
    fun `can create RentalTripEntity without optional id`() {
        val startDate = LocalDateTime(2025, 10, 25, 10, 0)
        val endDate = LocalDateTime(2025, 10, 30, 18, 0)

        val trip =
            RentalTripEntity(
                rentalId = 456,
                startMileage = 2000,
                endMileage = 2500,
                startDate = startDate,
                endDate = endDate,
            )

        assertNull(trip.id)
        assertEquals(456, trip.rentalId)
        assertEquals(2000, trip.startMileage)
        assertEquals(2500, trip.endMileage)
        assertEquals(startDate, trip.startDate)
        assertEquals(endDate, trip.endDate)
    }
}
