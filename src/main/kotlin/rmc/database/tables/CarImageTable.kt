package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object CarImageTable : IntIdTable("CarImage") {
    val carId = reference("carId", CarTable, onDelete = ReferenceOption.CASCADE)
    val imageUrl = text("imageUrl")
    val weight = integer("weight").default(0)
}