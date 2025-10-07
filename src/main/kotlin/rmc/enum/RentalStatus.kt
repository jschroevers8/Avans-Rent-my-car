package rmc.enum

enum class RentalStatus(val status: String) {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED"),
    CANCELLED("CANCELLED")
}