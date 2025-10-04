package rmc.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable


object CarTable : IntIdTable() {
    val brand = varchar("brand", 100)
}