package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.date
import rmc.enum.RentalStatus

object RentalTable : IntIdTable("Rental") {
    val userId = reference("userId", UserTable, onDelete = ReferenceOption.CASCADE)
    val advertisementId = reference("advertisementId", AdvertisementTable, onDelete = ReferenceOption.CASCADE)
    val rentalStatus = enumerationByName<RentalStatus>("status", 100).default(RentalStatus.PENDING)
    val createdStamp = date("createdStamp")
}