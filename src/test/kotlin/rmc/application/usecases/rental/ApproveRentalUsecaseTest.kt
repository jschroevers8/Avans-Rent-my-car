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
import rmc.domain.exceptions.RentalNotPendingException
import rmc.domain.repositories.RentalRepositoryInterface

class ApproveRentalUsecaseTest {

    private lateinit var rentalRepository: RentalRepositoryInterface
    private lateinit var usecase: ApproveRentalUsecase

    @BeforeTest
    fun setUp() {
        rentalRepository = mockk()
        usecase = ApproveRentalUsecase(rentalRepository)
    }

    @Test
    fun `should approve rental successfully`() {
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

        assertEquals(RentalStatus.ACTIVE, result.rentalStatus)
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
    fun `should throw RentalNotPendingException when rental is not pending`() {
        val rental = RentalEntity(
            id = 2,
            userId = 1,
            advertisementId = 1,
            rentalStatus = RentalStatus.ACTIVE,
            pickUpDate = LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = LocalDateTime(2025, 10, 26, 10, 0),
        )

        every { rentalRepository.findById(2) } returns rental

        val exception = assertFailsWith<    RentalNotPendingException> {
            usecase(2)
        }

        assertEquals("Rental with id 2 is not pending and cannot be approved", exception.message)

        verify { rentalRepository.findById(2) }
    }
}
