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
import rmc.domain.entities.RentalStatus
import rmc.domain.exceptions.RentalCannotBeCancelledException
import rmc.domain.repositories.RentalRepositoryInterface

class CancelRentalUsecaseTest {

    private lateinit var rentalRepository: RentalRepositoryInterface
    private lateinit var usecase: CancelRentalUsecase

    @BeforeTest
    fun setUp() {
        rentalRepository = mockk()
        usecase = CancelRentalUsecase(rentalRepository)
    }

    @Test
    fun `should cancel rental successfully`() {
        val rental = RentalEntity(
            id = 1,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        every { rentalRepository.findById(1) } returns rental
        every { rentalRepository.save(any()) } answers { firstArg() as RentalEntity }

        val result = usecase(1)

        assertEquals(RentalStatus.CANCELLED, result.rentalStatus)
        assertEquals(1, result.id)
        assertEquals(rental.userId, result.userId)
        assertEquals(rental.advertisementId, result.advertisementId)

        verify { rentalRepository.findById(1) }
        verify { rentalRepository.save(any()) }
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
    fun `should throw RentalCannotBeCancelledException when rental is ACTIVE`() {
        val rental = RentalEntity(
            id = 2,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.ACTIVE,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        every { rentalRepository.findById(2) } returns rental

        val exception = assertFailsWith<RentalCannotBeCancelledException> {
            usecase(2)
        }

        assertEquals("Rental with id  2 cannot be cancelled because it is ACTIVE", exception.message)
        verify { rentalRepository.findById(2) }
    }

    @Test
    fun `should throw RentalCannotBeCancelledException when rental is COMPLETED`() {
        val rental = RentalEntity(
            id = 3,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.COMPLETED,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        every { rentalRepository.findById(3) } returns rental

        val exception = assertFailsWith<RentalCannotBeCancelledException> {
            usecase(3)
        }

        assertEquals("Rental with id  3 cannot be cancelled because it is COMPLETED", exception.message)
        verify { rentalRepository.findById(3) }
    }
}
