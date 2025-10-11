package rmc.infrastructure.tables

import org.jetbrains.exposed.sql.Table

object CarTable : Table() {
    val id = integer("id").autoIncrement()
    val licensePlate = varchar("license_plate", 20).uniqueIndex()
    val brand = varchar("brand", 50)
    override val primaryKey = PrimaryKey(id)
}
