package com.challenge.domain.manager

import com.challenge.domain.entity.Circle
import com.challenge.domain.entity.Location
import com.challenge.domain.entity.Member

/**
 * Provides operations for managing [Circle] and [Member] instances, and relations between them.
 */
interface CircleManager {

    /**
     * Creates new [Circle] with an unique ID and adds it managed Circles.
     * @param [circleName] name of new Circle
     * @param [members] set of member to be added to the new Circle
     * @return newly created [Circle]
     */
    fun createCircle(circleName: String, members: Collection<Member>): Circle

    /**
     * Deletes circle form Circle manager.
     * @param [circle] [Circle] that should be deleted form Circle manager
     */
    fun deleteCircle(circle: Circle): Boolean

    /**
     * Instantiates new [Member]. Circle manager does not manage Member unless it is added to [Circle].
     * See [com.challenge.domain.manager.CircleManager.addMemberToCircle].
     * @param [memberName] new Member name
     * @param [lastKnownMemberLocation] optional, Member location
     */
    fun createMember(memberName: String, lastKnownMemberLocation: Location?): Member

    // TODO: Probably need some method to update Member's location

    /**
     * Provides all managed Circles
     * @return list of all [Circle]
     */
    fun getAllCircles(): List<Circle>

    /**
     * Provides all [Circle] that has [count] number of members
     * @return list of [Circle]
     */
    fun findCirclesWithExactMembersCount(count: Int): Set<Circle>

    /**
     * Adds [member] to [Circle] that has provided [circleId].
     * @param [circleId] ID of Circle that [member] has to be added to
     * @param [member] to be added to Circle
     * @throws IllegalStateException in case provided [circleId] is not matched to any of managed Circles
     * @return updated [Circle] with new [Member]
     */
    fun addMemberToCircle(circleId: Int, member: Member): Circle

    /**
     * Removes [member] from [Circle] that has provided [circleId].
     * @param [circleId] ID of Circle that [member] has to be removed from
     * @param [member] to be removed from Circle
     * @throws IllegalStateException in case provided [circleId] is not matched to any of managed Circles
     * @return updated [Circle] w/o removed [Member] or _null_ if the Circle was removed as it has no more Members
     */
    fun removeMemberFromCircle(circleId: Int, member: Member): Circle?

}