package rmc.domain.entities

data class UserEntity(
    val id: Int? = null,
    val userType: UserType,
    val address: AddressEntity,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val userPoints: Int,
) {
    fun ensureCustomer() {
        if (this.userType != UserType.CUSTOMER) {
            throw IllegalAccessException("Only customers can add a car")
        }
    }
}
