package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import rmc.domain.exceptions.RentalCannotBeCancelledException
import rmc.domain.exceptions.RentalNotPendingException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class RentalEntityTest {

    @Test
    fun `can create RentalEntity with all fields`() {
        val pickUpDate = LocalDateTime(2025, 10, 25, 10, 0)
        val returningDate = LocalDateTime(2025, 10, 30, 18, 0)

        val rental = RentalEntity(
            id = 1,
            userId = 42,
            advertisementId = 99,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = pickUpDate,
            returningDate = returningDate
        )

        assertEquals(1, rental.id)
        assertEquals(42, rental.userId)
        assertEquals(99, rental.advertisementId)
        assertEquals(RentalStatus.PENDING, rental.rentalStatus)
        assertEquals(pickUpDate, rental.pickUpDate)
        assertEquals(returningDate, rental.returningDate)
        assertEquals(emptyList<RentalTripEntity>(), rental.rentalTrips)
    }

    @Test
    fun `can create RentalEntity without optional id`() {
        val pickUpDate = LocalDateTime(2025, 10, 25, 10, 0)
        val returningDate = LocalDateTime(2025, 10, 30, 18, 0)

        val rental = RentalEntity(
            userId = 50,
            advertisementId = 100,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = pickUpDate,
            returningDate = returningDate
        )

        assertNull(rental.id)
        assertEquals(50, rental.userId)
        assertEquals(100, rental.advertisementId)
        assertEquals(RentalStatus.PENDING, rental.rentalStatus)
    }

    @Test
    fun `can set rentalTrips`() {
        val rental = RentalEntity(
            userId = 1,
            advertisementId = 2,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 30, 18, 0)
        )

        val trips = listOf(
            RentalTripEntity(rentalId = 1, startMileage = 100, endMileage = 200,
                startDate = LocalDateTime(2025, 10, 25, 10, 0),
                endDate = LocalDateTime(2025, 10, 26, 10, 0))
        )

        rental.setTrips(trips)
        assertEquals(trips, rental.rentalTrips)
    }

    @Test
    fun `ensureStatusPending throws when status is not PENDING`() {
        val rental = RentalEntity(
            userId = 1,
            advertisementId = 2,
            rentalStatus = RentalStatus.ACTIVE,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 30, 18, 0)
        )

        assertFailsWith<RentalNotPendingException> {
            rental.ensureStatusPending()
        }
    }

    @Test
    fun `ensureStatusNotActiveOrCompleted throws when status is ACTIVE or COMPLETED`() {
        val activeRental = RentalEntity(
            userId = 1,
            advertisementId = 2,
            rentalStatus = RentalStatus.ACTIVE,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 30, 18, 0)
        )

        assertFailsWith<RentalCannotBeCancelledException> {
            activeRental.ensureStatusNotActiveOrCompleted()
        }

        val completedRental = RentalEntity(
            userId = 1,
            advertisementId = 2,
            rentalStatus = RentalStatus.COMPLETED,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 30, 18, 0)
        )

        assertFailsWith<RentalCannotBeCancelledException> {
            completedRental.ensureStatusNotActiveOrCompleted()
        }
    }
}
