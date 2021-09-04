package com.challenge.domain.entity

/**
 * Member of [Circle] with name and last known geo-location.
 *
 * Must be instantiated via [com.challenge.domain.manager.CircleManager]
 */
class Member internal constructor(
    val id: Int,
    val name: String,
    val lastKnownLocation: Location?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastKnownLocation != other.lastKnownLocation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (lastKnownLocation?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name', lastKnownLocation=$lastKnownLocation)"
    }
}