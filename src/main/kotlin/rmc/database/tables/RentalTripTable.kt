package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.date

object RentalTripTable : IntIdTable("RentalTrip") {
    val rentalId = reference("rentalId", RentalTable, onDelete = ReferenceOption.CASCADE)
    val startMileage = integer("startMileage")
    val endMileage = integer("endMileage").nullable()
    val startDate = date("startDate")
    val endDate = date("endDate")
}