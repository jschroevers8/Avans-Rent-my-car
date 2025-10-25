package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import rmc.domain.entities.RentalStatus

object RentalTable : Table("rental") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val advertisementId = integer("advertisement_id").references(AdvertisementTable.id, onDelete = ReferenceOption.CASCADE)
    val rentalStatus = enumerationByName("rental_status", 20, RentalStatus::class)
    val pickUpDate = datetime("pick_up_date")
    val returningDate = datetime("returning_date")

    override val primaryKey = PrimaryKey(id)
}
