package com.challenge.domain.entity

import com.challenge.domain.util.LocationUtils

/**
 * Named group of [Member].
 *
 * Must be instantiated with [com.challenge.domain.manager.CircleManager]
 */
class Circle internal constructor(
    val id: Int,
    val name: String,
    val members: Set<Member>
) {

    fun getCircleMember(memberId: Int): Member? = members.firstOrNull { it.id == memberId }

    fun findMembersWithinArea(searchCenter: Location, searchRadius: Double) =
        members.filter { member ->
            member.lastKnownLocation?.let { memberLocation ->
                LocationUtils.distanceInMeters(searchCenter, memberLocation) <= searchRadius
            } ?: false
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Circle

        if (id != other.id) return false
        if (name != other.name) return false
        if (members != other.members) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + members.hashCode()
        return result
    }

    override fun toString(): String {
        return "Circle(id=$id, name='$name', members=$members)"
    }
}