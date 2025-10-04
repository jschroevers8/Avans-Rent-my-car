package rmc.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.database.tables.CarTable


object DatabaseFactory {
    fun init() {
        TODO()
//        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
//        transaction {
//            SchemaUtils.create(CarTable)
//
//            CarTable.insert {
//                it[brand] = "Tesla"
//            }
//
//            CarTable.insert {
//                it[brand] = "BMW"
//            }
//        }
    }
}
