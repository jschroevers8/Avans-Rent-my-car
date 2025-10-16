package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object CarImageTable : Table("car_image") {
    val id = integer("id").autoIncrement()
    val carId = integer("car_id").references(CarTable.id)
    val image = largeText("image")
    val weight = integer("weight")
    override val primaryKey = PrimaryKey(id)
}
