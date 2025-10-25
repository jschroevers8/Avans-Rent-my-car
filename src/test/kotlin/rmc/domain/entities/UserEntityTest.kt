package rmc.domain.entities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserEntityTest {
    private val sampleAddress =
        AddressEntity(
            id = 1,
            city = "Amsterdam",
            street = "Damstraat",
            houseNumber = 10,
            subHouseNumber = "A",
            postalCode = "1012JS",
        )

    @Test
    fun `can create UserEntity`() {
        val user =
            UserEntity(
                id = 1,
                userType = UserType.CUSTOMER,
                address = sampleAddress,
                email = "test@example.com",
                password = "password123",
                firstName = "John",
                lastName = "Doe",
                phone = "0612345678",
                userPoints = 100,
            )

        assertEquals(1, user.id)
        assertEquals(UserType.CUSTOMER, user.userType)
        assertEquals(sampleAddress, user.address)
        assertEquals("test@example.com", user.email)
        assertEquals("password123", user.password)
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("0612345678", user.phone)
        assertEquals(100, user.userPoints)
    }

    @Test
    fun `ensureCustomer does not throw for CUSTOMER`() {
        val user =
            UserEntity(
                userType = UserType.CUSTOMER,
                address = sampleAddress,
                email = "customer@example.com",
                password = "pass",
                firstName = "Alice",
                lastName = "Smith",
                phone = "0611111111",
                userPoints = 0,
            )

        // Should not throw
        user.ensureCustomer()
    }

    @Test
    fun `ensureCustomer throws for non-CUSTOMER`() {
        val user =
            UserEntity(
                userType = UserType.ADMIN,
                address = sampleAddress,
                email = "admin@example.com",
                password = "pass",
                firstName = "Bob",
                lastName = "Admin",
                phone = "0622222222",
                userPoints = 0,
            )

        val exception =
            assertFailsWith<IllegalAccessException> {
                user.ensureCustomer()
            }

        assertEquals("Only customers can add a car", exception.message)
    }
}
