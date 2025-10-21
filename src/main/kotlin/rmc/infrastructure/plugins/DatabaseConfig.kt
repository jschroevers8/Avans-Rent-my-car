package rmc.infrastructure.plugins

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import rmc.infrastructure.tables.*

fun initDatabase() {
    val dotenv = dotenv()

    Database.connect(
        url = dotenv["DB_URL"],
        driver = "com.mysql.cj.jdbc.Driver",
        user = dotenv["DB_USER"],
        password = dotenv["DB_PASSWORD"],
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            CarTable,
            AddressTable,
            UserTable,
            AdvertisementTable,
            CarImageTable,
            RentalTable,
            RentalTripTable,
        )
    }
}
