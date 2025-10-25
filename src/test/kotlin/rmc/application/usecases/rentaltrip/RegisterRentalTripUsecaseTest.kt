package rmc.application.usecases.rentaltrip

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.RentalNotFoundException
import rmc.domain.entities.RentalTripEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.presentation.dto.rentaltrip.RegisterRentalTrip

class RegisterRentalTripUsecaseTest {

    private lateinit var rentalTripRepository: RentalTripRepositoryInterface
    private lateinit var rentalRepository: RentalRepositoryInterface
    private lateinit var usecase: RegisterRentalTripUsecase

    @BeforeTest
    fun setUp() {
        rentalTripRepository = mockk()
        rentalRepository = mockk()
        usecase = RegisterRentalTripUsecase(rentalTripRepository, rentalRepository)
    }

    @Test
    fun `should register rental trip successfully`() {
        val request = RegisterRentalTrip(
            rentalId = 1,
            startMileage = 100,
            endMileage = 150,
            startDate = LocalDateTime(2025, 10, 25, 10, 0),
            endDate = LocalDateTime(2025, 10, 25, 12, 0)
        )

        // Mock: rentalRepository vindt de rental
        every { rentalRepository.findById(1) } returns mockk()

        // Mock: rentalTripRepository.save retourneert de entity
        every { rentalTripRepository.save(any()) } answers { firstArg() as RentalTripEntity }

        val result = usecase(request)

        assertEquals(1, result.rentalId)
        assertEquals(100, result.startMileage)
        assertEquals(150, result.endMileage)
        assertEquals(LocalDateTime(2025, 10, 25, 10, 0), result.startDate)
        assertEquals(LocalDateTime(2025, 10, 25, 12, 0), result.endDate)

        verify { rentalRepository.findById(1) }
        verify { rentalTripRepository.save(any()) }
    }

    @Test
    fun `should throw RentalNotFoundException when rental does not exist`() {
        val request = RegisterRentalTrip(
            rentalId = 99,
            startMileage = 100,
            endMileage = 150,
            startDate = LocalDateTime(2025, 10, 25, 10, 0),
            endDate = LocalDateTime(2025, 10, 25, 12, 0)
        )

        // Mock: rentalRepository vindt de rental niet
        every { rentalRepository.findById(99) } returns null

        val exception = assertFailsWith<RentalNotFoundException> {
            usecase(request)
        }

        assertEquals("Rental with id 99 not found", exception.message)

        verify { rentalRepository.findById(99) }
    }
}
