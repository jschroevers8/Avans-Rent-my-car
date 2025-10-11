package rmc.domain.repositories

import rmc.domain.entities.UserEntity

interface UserRepositoryInterface {
    suspend fun findById(id: Int): UserEntity?
    suspend fun findByEmail(email: String): UserEntity?
    suspend fun save(user: UserEntity): UserEntity
}