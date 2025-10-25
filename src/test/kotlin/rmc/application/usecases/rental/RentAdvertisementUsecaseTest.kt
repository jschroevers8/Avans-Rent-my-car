package rmc.application.usecases.rental

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.rental.RentAdvertisement
import rmc.application.exceptions.UserNotFoundException
import rmc.application.exceptions.AdvertisementNotFoundException

class RentAdvertisementUsecaseTest {

    private val rentalRepository = mockk<RentalRepositoryInterface>()
    private val userRepository = mockk<UserRepositoryInterface>()
    private val advertisementRepository = mockk<AdvertisementRepositoryInterface>()
    private val usecase = RentAdvertisementUsecase(rentalRepository, userRepository, advertisementRepository)

    @Test
    fun `should create rental when user and advertisement exist`() {
        val userId = 1
        val advertisementId = 2
        val request = RentAdvertisement(
            advertisementId = advertisementId,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = kotlinx.datetime.LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = kotlinx.datetime.LocalDateTime(2025, 10, 30, 10, 0)
        )
        val savedRental = RentalEntity(
            id = 1,
            userId = userId,
            advertisementId = advertisementId,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = request.pickUpDate,
            returningDate = request.returningDate
        )

        every { userRepository.findById(userId) } returns mockk()
        every { advertisementRepository.findById(advertisementId) } returns mockk()
        every { rentalRepository.save(any()) } returns savedRental

        val result = usecase(request, userId)

        assertEquals(savedRental, result)
        verify { userRepository.findById(userId) }
        verify { advertisementRepository.findById(advertisementId) }
        verify { rentalRepository.save(any()) }
    }

    @Test
    fun `should throw UserNotFoundException when user does not exist`() {
        val userId = 1
        val request = RentAdvertisement(
            advertisementId = 2,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = kotlinx.datetime.LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = kotlinx.datetime.LocalDateTime(2025, 10, 30, 10, 0)
        )

        every { userRepository.findById(userId) } returns null

        assertFailsWith<UserNotFoundException> {
            usecase(request, userId)
        }
    }

    @Test
    fun `should throw AdvertisementNotFoundException when advertisement does not exist`() {
        val userId = 1
        val advertisementId = 2
        val request = RentAdvertisement(
            advertisementId = advertisementId,
            rentalStatus = RentalStatus.PENDING,
            pickUpDate = kotlinx.datetime.LocalDateTime(2025, 10, 25, 10, 0),
            returningDate = kotlinx.datetime.LocalDateTime(2025, 10, 30, 10, 0)
        )

        every { userRepository.findById(userId) } returns mockk()
        every { advertisementRepository.findById(advertisementId) } returns null

        assertFailsWith<AdvertisementNotFoundException> {
            usecase(request, userId)
        }
    }
}
