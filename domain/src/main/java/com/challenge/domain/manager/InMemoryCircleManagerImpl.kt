package com.challenge.domain.manager

import com.challenge.domain.entity.Circle
import com.challenge.domain.entity.Location
import com.challenge.domain.entity.Member
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * In-memory thread-safe [CircleManager] implementation. Optimized to provide fast findCirclesWithExactMembersCount call
 * by using additional thread sync blocks, that can cause multithreading performance reduction.
 */
class InMemoryCircleManagerImpl : CircleManager {

    private val incrementalMemberId = AtomicInteger()
    private val incrementalCircleId = AtomicInteger()
    private val circles = ConcurrentHashMap<Int, Circle>() // circleID - circle

    // TODO: Discuss blocking implementation. If there is no better solution
    //  to provide O(1) findCirclesWithExactMembersCount complexity, we could change the requirements
    private val membersCountToCircles = HashMap<Int, MutableSet<Circle>>()

    override fun createCircle(circleName: String, members: Collection<Member>): Circle {
        val id = incrementalCircleId.incrementAndGet()
        val newCircle = Circle(id, circleName, members.toSet())
        circles[id] = newCircle
        updateMembersNumberMap(null, newCircle)
        return newCircle
    }

    override fun deleteCircle(circle: Circle): Boolean {
        synchronized(circle) {
            updateMembersNumberMap(circle, null)
            return circles.remove(circle.id) != null
        }
    }

    override fun createMember(memberName: String, lastKnownMemberLocation: Location?): Member =
        Member(incrementalMemberId.getAndIncrement(), memberName, lastKnownMemberLocation)

    override fun getAllCircles(): List<Circle> {
        return circles.values.toList()
    }

    override fun findCirclesWithExactMembersCount(count: Int): Set<Circle> {
        synchronized(membersCountToCircles) {
            return membersCountToCircles[count] ?: emptySet()
        }
    }

    override fun addMemberToCircle(circleId: Int, member: Member): Circle {
        val circle = circles[circleId] ?: throw IllegalStateException(
            "Unable to add member to the circle, " +
                "this circle has been already deleted, circleID = $circleId"
        )
        synchronized(circle) {
            if (circles[circleId] == null) {
                throw IllegalStateException(
                    "Unable to add member to the circle, " +
                        "this circle has been already deleted, circleID = $circleId"
                )
            }
            val updatedCircle = Circle(circle.id, circle.name, members = circle.members + member)
            circles[circleId] = updatedCircle
            updateMembersNumberMap(circle, updatedCircle)
        }
        return circle
    }

    override fun removeMemberFromCircle(circleId: Int, member: Member): Circle? {
        val circle = circles[circleId] ?: throw IllegalStateException(
            "Unable to remove member from the circle, " +
                "this circle has been already deleted, circleID = $circleId"
        )
        synchronized(circle) {
            if (circles[circleId] == null) {
                throw IllegalStateException(
                    "Unable to remove member from the circle, " +
                        "this circle has been already deleted, circleID = $circleId"
                )
            }
            val members = circle.members - member
            return if (members.isEmpty()) {
                // remove circle if there is no more members
                deleteCircle(circle)
                null
            } else {
                val updatedCircle = Circle(circle.id, circle.name, members = circle.members - member)
                circles[circleId] = updatedCircle
                updateMembersNumberMap(circle, updatedCircle)
                circle
            }
        }
    }

    private fun updateMembersNumberMap(
        circleBeforeEdit: Circle?,
        circleAfterEdit: Circle?
    ) {
        synchronized(membersCountToCircles) {
            // remove circle from pair where key == number of members before edit
            // ignore circleBeforeEdit for deleteCircle
            circleBeforeEdit?.let { circle ->
                val numberOfItemsBeforeEdit = circle.members.size
                membersCountToCircles[numberOfItemsBeforeEdit]?.remove(circleBeforeEdit)
                    ?: throw IllegalStateException(
                        "Number item must be already created before removing item op," +
                            "check logic correctness"
                    )
            }
            // add circle to pair where key == number of members after edit
            // ignore circleBeforeEdit for addCircle
            circleAfterEdit?.let { circle ->
                val numberOfItemsAfterEdit = circleAfterEdit.members.size
                var set = membersCountToCircles[numberOfItemsAfterEdit]
                if (set == null) {
                    set = mutableSetOf()
                    membersCountToCircles[numberOfItemsAfterEdit] = set
                }
                set.add(circle)
            }
        }
    }
}