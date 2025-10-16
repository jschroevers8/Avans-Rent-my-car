package rmc

import io.ktor.server.application.*
import io.ktor.server.routing.routing
import rmc.application.usecases.advertisement.CreateAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAdvertisementUsecase
import rmc.application.usecases.advertisement.GetAvailableAdvertisementsUsecase
import rmc.application.usecases.car.CreateCarUsecase
import rmc.application.usecases.car.GetCarUsecase
import rmc.application.usecases.user.LoginUsecase
import rmc.application.usecases.user.SignupUsecase
import rmc.domain.repositories.AddressRepositoryInterface
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.infrastructure.repositories.AddressRepository
import rmc.infrastructure.repositories.AdvertisementRepository
import rmc.infrastructure.repositories.CarRepository
import rmc.infrastructure.repositories.UserRepository
import rmc.presentation.routes.advertisement.createAdvertisementRoute
import rmc.presentation.routes.advertisement.getAdvertisementRoute
import rmc.presentation.routes.advertisement.getAvailableAdvertisementsRoute
import rmc.presentation.routes.car.createCarRoute
import rmc.presentation.routes.car.getCarRoute
import rmc.presentation.routes.user.userLoginRoute
import rmc.presentation.routes.user.userSignupRoute

fun Application.configureRouting() {
    val addressRepository: AddressRepositoryInterface = AddressRepository()
    val userRepository: UserRepositoryInterface = UserRepository(addressRepository)
    val carRepository: CarRepositoryInterface = CarRepository(userRepository)
    val advertisementRepository: AdvertisementRepositoryInterface = AdvertisementRepository(addressRepository)

    val userLoginUsecase = LoginUsecase(userRepository)
    val userSignupUsecase = SignupUsecase(userRepository)
    val createCarUsecase = CreateCarUsecase(carRepository, userRepository)
    val getCarUsecase = GetCarUsecase(carRepository)
    val createAdvertisementUsecase = CreateAdvertisementUsecase(carRepository, advertisementRepository)
    val getAdvertisementUsecase = GetAdvertisementUsecase(advertisementRepository)
    val getAvailableAdvertisementsUsecase = GetAvailableAdvertisementsUsecase(advertisementRepository)

    routing {
        userLoginRoute(userLoginUsecase)
        userSignupRoute(userSignupUsecase)
        createCarRoute(createCarUsecase)
        getCarRoute(getCarUsecase)
        createAdvertisementRoute(createAdvertisementUsecase)
        getAdvertisementRoute(getAdvertisementUsecase)
        getAvailableAdvertisementsRoute(getAvailableAdvertisementsUsecase)
    }
}
