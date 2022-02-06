package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Defines the schema of the database, aka the fields of every entity in the DB
 * Each instance of Asteroid represents a row in a asteroid table in the app's database.*/

@Entity (tableName = "asteroids_db")
data class Asteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)
