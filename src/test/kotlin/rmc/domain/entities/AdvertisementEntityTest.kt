package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class AdvertisementEntityTest {

    private val address = AddressEntity(
        city = "Amsterdam",
        street = "Damstraat",
        houseNumber = 10,
        postalCode = "1012JS"
    )

    @Test
    fun `can create AdvertisementEntity with all fields`() {
        val availableFrom = LocalDateTime(2025, 10, 25, 10, 0)
        val availableUntil = LocalDateTime(2025, 11, 5, 18, 0)

        val ad = AdvertisementEntity(
            id = 1,
            carId = 42,
            address = address,
            availableFrom = availableFrom,
            availableUntil = availableUntil,
            price = 99.99
        )

        assertEquals(1, ad.id)
        assertEquals(42, ad.carId)
        assertEquals(address, ad.address)
        assertEquals(availableFrom, ad.availableFrom)
        assertEquals(availableUntil, ad.availableUntil)
        assertEquals(99.99, ad.price)
    }

    @Test
    fun `can create AdvertisementEntity without optional id`() {
        val availableFrom = LocalDateTime(2025, 10, 25, 10, 0)
        val availableUntil = LocalDateTime(2025, 11, 5, 18, 0)

        val ad = AdvertisementEntity(
            carId = 55,
            address = address,
            availableFrom = availableFrom,
            availableUntil = availableUntil,
            price = 149.99
        )

        assertNull(ad.id)
        assertEquals(55, ad.carId)
        assertEquals(address, ad.address)
        assertEquals(availableFrom, ad.availableFrom)
        assertEquals(availableUntil, ad.availableUntil)
        assertEquals(149.99, ad.price)
    }

    @Test
    fun `canBeDeleted returns true when no ACTIVE or PENDING rentals`() {
        val ad = AdvertisementEntity(
            carId = 1,
            address = address,
            availableFrom = LocalDateTime(2025, 10, 25, 10, 0),
            availableUntil = LocalDateTime(2025, 11, 5, 18, 0),
            price = 50.0
        )

        val rentals = listOf(
            RentalEntity(
                userId = 1,
                advertisementId = 1,
                rentalStatus = RentalStatus.COMPLETED,
                pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
                returningDate = LocalDateTime(2025, 10, 30, 18, 0)
            )
        )

        assertTrue(ad.canBeDeleted(rentals))
    }

    @Test
    fun `canBeDeleted returns false when there is an ACTIVE or PENDING rental`() {
        val ad = AdvertisementEntity(
            carId = 1,
            address = address,
            availableFrom = LocalDateTime(2025, 10, 25, 10, 0),
            availableUntil = LocalDateTime(2025, 11, 5, 18, 0),
            price = 50.0
        )

        val rentals = listOf(
            RentalEntity(
                userId = 1,
                advertisementId = 1,
                rentalStatus = RentalStatus.ACTIVE,
                pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
                returningDate = LocalDateTime(2025, 10, 30, 18, 0)
            ),
            RentalEntity(
                userId = 2,
                advertisementId = 1,
                rentalStatus = RentalStatus.PENDING,
                pickUpDate = LocalDateTime(2025, 10, 26, 10, 0),
                returningDate = LocalDateTime(2025, 10, 31, 18, 0)
            )
        )

        assertFalse(ad.canBeDeleted(rentals))
    }
}
