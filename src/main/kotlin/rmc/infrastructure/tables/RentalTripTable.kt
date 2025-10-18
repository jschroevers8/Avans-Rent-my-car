package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RentalTripTable : Table() {
    val id = integer("id").autoIncrement()
    val rentalId = integer("rental_id").references(RentalTable.id)
    val startMileage = integer("start_mileage")
    val endMileage = integer("end_mileage")
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")

    override val primaryKey = PrimaryKey(id)
}
