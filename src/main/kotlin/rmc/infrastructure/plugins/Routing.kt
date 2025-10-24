package rmc.infrastructure.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.routing
import rmc.application.auth.Authenticator
import rmc.application.usecases.advertisement.CreateAdvertisementUsecase
import rmc.application.usecases.advertisement.DeleteAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAvailableAdvertisementsUsecase
import rmc.application.usecases.car.CreateCarUsecase
import rmc.application.usecases.car.DeleteCarUsecase
import rmc.application.usecases.car.GetCarUsecase
import rmc.application.usecases.car.GetPersonalCarsUsecase
import rmc.application.usecases.car.UpdateCarUsecase
import rmc.application.usecases.rental.ApproveRentalUsecase
import rmc.application.usecases.rental.CancelRentalUsecase
import rmc.application.usecases.rental.GetRentalUsecase
import rmc.application.usecases.rental.RentAdvertisementUsecase
import rmc.application.usecases.rentaltrip.RegisterRentalTripUsecase
import rmc.application.usecases.user.LoginUsecase
import rmc.application.usecases.user.SignupUsecase
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.infrastructure.auth.JwtTokenGenerator
import rmc.infrastructure.repositories.AddressRepository
import rmc.infrastructure.repositories.AdvertisementRepository
import rmc.infrastructure.repositories.CarImageRepository
import rmc.infrastructure.repositories.CarRepository
import rmc.infrastructure.repositories.RentalRepository
import rmc.infrastructure.repositories.RentalTripRepository
import rmc.infrastructure.repositories.UserRepository
import rmc.presentation.routes.advertisement.createAdvertisementRoute
import rmc.presentation.routes.advertisement.deleteAdvertisementRoute
import rmc.presentation.routes.advertisement.getAdvertisementRoute
import rmc.presentation.routes.advertisement.getAvailableAdvertisementsRoute
import rmc.presentation.routes.car.createCarRoute
import rmc.presentation.routes.car.deleteCarRoute
import rmc.presentation.routes.car.getCarRoute
import rmc.presentation.routes.car.getPersonalCarsRoute
import rmc.presentation.routes.car.updateCarRoute
import rmc.presentation.routes.rental.approveRentalRoute
import rmc.presentation.routes.rental.cancelRentalRoute
import rmc.presentation.routes.rental.getRentalRoute
import rmc.presentation.routes.rental.rentAdvertisementRoute
import rmc.presentation.routes.rentaltrip.registerRentalTripRoute
import rmc.presentation.routes.user.userLoginRoute
import rmc.presentation.routes.user.userSignupRoute

fun Application.configureRouting() {
    val addressRepository: AddressRepositoryInterface = AddressRepository()
    val userRepository: UserRepositoryInterface = UserRepository(addressRepository)
    val carRepository: CarRepositoryInterface = CarRepository()
    val advertisementRepository: AdvertisementRepositoryInterface = AdvertisementRepository(addressRepository)
    val carImageRepository: CarImageRepositoryInterface = CarImageRepository()
    val rentalRepository: RentalRepositoryInterface = RentalRepository()
    val rentalTripRepository: RentalTripRepositoryInterface = RentalTripRepository()

    val userLoginUsecase = LoginUsecase(userRepository)
    val userSignupUsecase = SignupUsecase(userRepository)
    val createCarUsecase = CreateCarUsecase(carRepository, userRepository, carImageRepository)
    val updateCarUsecase = UpdateCarUsecase(carRepository, userRepository, carImageRepository)
    val getCarUsecase = GetCarUsecase(carRepository, carImageRepository)
    val getPersonalCarsUsecase = GetPersonalCarsUsecase(carRepository, carImageRepository)
    val deleteCarUsecase = DeleteCarUsecase(carRepository, carImageRepository, advertisementRepository, rentalRepository)
    val createAdvertisementUsecase = CreateAdvertisementUsecase(carRepository, advertisementRepository)
    val getAdvertisementUsecase = GetAdvertisementUsecase(advertisementRepository)
    val getAvailableAdvertisementsUsecase = GetAvailableAdvertisementsUsecase(advertisementRepository)
    val rentAdvertisementUsecase = RentAdvertisementUsecase(rentalRepository, userRepository, advertisementRepository)
    val registerRentalTripUsecase = RegisterRentalTripUsecase(rentalTripRepository, rentalRepository)
    val getRentalUsecase = GetRentalUsecase(rentalRepository, rentalTripRepository)
    val cancelRentalUsecase = CancelRentalUsecase(rentalRepository)
    val approveRentalUsecase = ApproveRentalUsecase(rentalRepository)
    val deleteAdvertisementUsecase = DeleteAdvertisementUsecase(advertisementRepository, rentalRepository)

    val jwtSecret = environment.config.property("jwt.secret").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtDomain = environment.config.property("jwt.domain").getString()

    val tokenGenerator = JwtTokenGenerator(jwtSecret, jwtAudience, jwtDomain)
    val authenticator = Authenticator(tokenGenerator)

    routing {
        userLoginRoute(userLoginUsecase, authenticator)
        userSignupRoute(userSignupUsecase)
        createCarRoute(createCarUsecase)
        updateCarRoute(updateCarUsecase)
        getCarRoute(getCarUsecase)
        getPersonalCarsRoute(getPersonalCarsUsecase)
        deleteCarRoute(deleteCarUsecase)
        createAdvertisementRoute(createAdvertisementUsecase)
        getAdvertisementRoute(getAdvertisementUsecase)
        getAvailableAdvertisementsRoute(getAvailableAdvertisementsUsecase)
        rentAdvertisementRoute(rentAdvertisementUsecase)
        registerRentalTripRoute(registerRentalTripUsecase)
        getRentalRoute(getRentalUsecase)
        cancelRentalRoute(cancelRentalUsecase)
        approveRentalRoute(approveRentalUsecase)
        deleteAdvertisementRoute(deleteAdvertisementUsecase)
    }
}
