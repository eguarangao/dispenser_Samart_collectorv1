package com.example.proyect01.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "user_container",
    primaryKeys = ["userId", "dispenserId"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = DispenserEntity::class, parentColumns = ["id"], childColumns = ["dispenserId"])
    ]
)
data class UserDispenser (val userId: Long,
                          val dispenserId: Long)

data class UserWithDispenser(
    @Embedded val person: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(UserDispenser::class)
    )
        val dispenseres: List<DispenserEntity>
)

data class DispenserWithUser(
    @Embedded val dispenser: DispenserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(UserDispenser::class)
    )
    val useres: List<UserDispenser>
)
