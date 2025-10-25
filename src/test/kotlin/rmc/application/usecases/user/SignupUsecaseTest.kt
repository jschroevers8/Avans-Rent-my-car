package rmc.application.usecases.user

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.mindrot.jbcrypt.BCrypt
import rmc.application.exceptions.UserAlreadyExistsException
import rmc.domain.entities.UserEntity
import rmc.domain.entities.UserType
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.address.CreateAddress
import rmc.presentation.dto.user.CreateUser
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SignupUsecaseTest {
    private lateinit var userRepository: UserRepositoryInterface
    private lateinit var signupUsecase: SignupUsecase

    @BeforeTest
    fun setUp() {
        userRepository = mockk()
        signupUsecase = SignupUsecase(userRepository)
    }

    @Test
    fun `should create user successfully`() {
        val request =
            CreateUser(
                email = "test@example.com",
                password = "password123",
                userType = UserType.CUSTOMER,
                firstName = "Test",
                lastName = "User",
                phone = "1234567890",
                userPoints = 0,
                address =
                    CreateAddress(
                        street = "Main Street",
                        city = "Amsterdam",
                        houseNumber = 1,
                        subHouseNumber = "A",
                        postalCode = "1011AA",
                    ),
            )

        // Mock: userRepository.findByEmail retourneert null (geen bestaand user)
        every { userRepository.findByEmail("test@example.com") } returns null

        // Mock: userRepository.save retourneert een UserEntity (simuleer opslag)
        every { userRepository.save(any()) } answers { firstArg() as UserEntity }

        val result = signupUsecase(request)

        assertEquals("test@example.com", result.email)
        assertEquals("Test", result.firstName)
        assertEquals("User", result.lastName)
        assertEquals(UserType.CUSTOMER, result.userType)
        assertEquals("1234567890", result.phone)
        assertEquals(0, result.userPoints)

        // Adrescheck
        assertEquals("Main Street", result.address.street)
        assertEquals("Amsterdam", result.address.city)
        assertEquals(1, result.address.houseNumber)
        assertEquals("A", result.address.subHouseNumber)
        assertEquals("1011AA", result.address.postalCode)

        // Password check via BCrypt
        assert(BCrypt.checkpw("password123", result.password))

        verify { userRepository.findByEmail("test@example.com") }
        verify { userRepository.save(any()) }
    }

    @Test
    fun `should throw UserAlreadyExistsException when email already exists`() {
        val request =
            CreateUser(
                email = "existing@example.com",
                password = "password123",
                userType = UserType.CUSTOMER,
                firstName = "Test",
                lastName = "User",
                phone = "1234567890",
                userPoints = 0,
                address =
                    CreateAddress(
                        street = "Main Street",
                        city = "Amsterdam",
                        houseNumber = 1,
                        subHouseNumber = "A",
                        postalCode = "1011AA",
                    ),
            )

        // Mock: userRepository.findByEmail retourneert een bestaand UserEntity
        every { userRepository.findByEmail("existing@example.com") } returns mockk()

        val exception =
            assertFailsWith<UserAlreadyExistsException> {
                signupUsecase(request)
            }

        assertEquals("User with email existing@example.com already exists", exception.message)

        verify { userRepository.findByEmail("existing@example.com") }
    }
}
