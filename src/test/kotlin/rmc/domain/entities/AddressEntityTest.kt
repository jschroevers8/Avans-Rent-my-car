package rmc.domain.entities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AddressEntityTest {
    @Test
    fun `can create AddressEntity with all fields`() {
        val address =
            AddressEntity(
                id = 1,
                city = "Amsterdam",
                street = "Damstraat",
                houseNumber = 10,
                subHouseNumber = "A",
                postalCode = "1012JS",
            )

        assertEquals(1, address.id)
        assertEquals("Amsterdam", address.city)
        assertEquals("Damstraat", address.street)
        assertEquals(10, address.houseNumber)
        assertEquals("A", address.subHouseNumber)
        assertEquals("1012JS", address.postalCode)
    }

    @Test
    fun `can create AddressEntity without optional fields`() {
        val address =
            AddressEntity(
                city = "Rotterdam",
                street = "Coolsingel",
                houseNumber = 5,
                postalCode = "3012AD",
            )

        assertNull(address.id)
        assertNull(address.subHouseNumber)
        assertEquals("Rotterdam", address.city)
        assertEquals("Coolsingel", address.street)
        assertEquals(5, address.houseNumber)
        assertEquals("3012AD", address.postalCode)
    }
}
