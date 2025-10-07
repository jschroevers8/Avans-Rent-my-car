package rmc.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.enum.BodyType
import rmc.enum.FuelType
import rmc.enum.UserType
import rmc.database.tables.*


object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(
                AddressTable,
                UserTable,
                CarTable,
            )

            createMockCarData()
        }
    }

    private fun createMockCarData() {
        val address = AddressTable.insertAndGetId {
            it[city] = "Amsterdam"
            it[street] = "Damrak"
            it[houseNumber] = 10
            it[subHouseNumber] = "A"
            it[postalCode] = "1012LG"
        }

        val user = UserTable.insertAndGetId {
            it[userType] = UserType.STAFF
            it[addressId] = address.value
            it[email] = "testuser@example.com"
            it[password] = "hashed_password"
            it[firstName] = "Jan"
            it[lastName] = "Jansen"
            it[phone] = "0612345678"
            it[userPoints] = 100
        }

        CarTable.insert {
            it[fuelType] = FuelType.BEV
            it[userId] = user.value
            it[bodyType] = BodyType.HATCHBACK
            it[brand] = "Tesla"
            it[model] = "Model 3"
            it[modelYear] = 2024
            it[licensePlate] = "AB-123-CD"
            it[mileage] = 15000
            it[createdStamp] = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
