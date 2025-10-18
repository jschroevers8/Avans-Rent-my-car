package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import rmc.domain.entities.RentalStatus

object RentalTable : Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val advertisementId = integer("advertisement_id").references(AdvertisementTable.id)
    val rentalStatus = enumerationByName("rental_status", 20, RentalStatus::class)
    val createdStamp = datetime("created_stamp")

    override val primaryKey = PrimaryKey(id)
}
