package rmc.application.usecases.rental

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.datetime.LocalDateTime
import rmc.application.exceptions.RentalNotFoundException
import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalTripEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface

class GetRentalUsecaseTest {

    private lateinit var rentalRepository: RentalRepositoryInterface
    private lateinit var rentalTripRepository: RentalTripRepositoryInterface
    private lateinit var usecase: GetRentalUsecase

    @BeforeTest
    fun setUp() {
        rentalRepository = mockk()
        rentalTripRepository = mockk()
        usecase = GetRentalUsecase(rentalRepository, rentalTripRepository)
    }

    @Test
    fun `should return rental with trips`() {
        val rental = RentalEntity(
            id = 1,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        val trips = listOf(
            RentalTripEntity(
                id = 1,
                rentalId = 1,
                startMileage = 1000,
                endMileage = 1100,
                startDate = LocalDateTime(2025, 10, 25, 10, 0),
                endDate = LocalDateTime(2025, 10, 25, 18, 0)
            )
        )

        every { rentalRepository.findById(1) } returns rental
        every { rentalTripRepository.findByRentalId(1) } returns trips

        val result = usecase(1)

        assertEquals(rental.id, result.id)
        assertEquals(trips, result.rentalTrips)

        verify { rentalRepository.findById(1) }
        verify { rentalTripRepository.findByRentalId(1) }
    }

    @Test
    fun `should throw RentalNotFoundException when rental does not exist`() {
        every { rentalRepository.findById(99) } returns null

        val exception = assertFailsWith<RentalNotFoundException> {
            usecase(99)
        }

        assertEquals("Rental with id 99 not found", exception.message)
        verify { rentalRepository.findById(99) }
    }

    @Test
    fun `should throw IllegalArgumentException when rental id is null`() {
        val rental = RentalEntity(
            id = null,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        every { rentalRepository.findById(1) } returns rental

        val exception = assertFailsWith<IllegalArgumentException> {
            usecase(1)
        }

        assertEquals("Cannot get rental without ID", exception.message)
        verify { rentalRepository.findById(1) }
    }
}
