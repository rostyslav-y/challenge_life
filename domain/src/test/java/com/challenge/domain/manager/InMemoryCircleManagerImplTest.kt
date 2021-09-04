package com.challenge.domain.manager

import com.challenge.domain.entity.Circle
import com.challenge.domain.entity.Location
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class InMemoryCircleManagerImplTest {

    private lateinit var circleManager: CircleManager

    @Before
    fun setUp() {
        circleManager = InMemoryCircleManagerImpl()
    }

    @Test
    fun `createMember creates member with provided params`() {
        val member = circleManager.createMember("newMember", Location(0.0, 0.0))
        assertEquals("newMember", member.name)
        assertEquals(Location(0.0, 0.0), member.lastKnownLocation)
    }

    @Test
    fun `createCircle creates circle with provided params`() {
        val member = circleManager.createMember("newMember", Location(0.0, 0.0))
        val circle = circleManager.createCircle("newCircle", listOf(member))
        assertEquals("newCircle", circle.name)
        assertEquals(member, circle.members.firstOrNull())
    }

    @Test
    fun `findCirclesWithExactMembersCount returns correct lists of circles`() {
        val member1 = circleManager.createMember("newMember1", Location(0.0, 0.0))
        val circle = circleManager.createCircle("newCircle", listOf(member1))

        val circlesWithOneMember = circleManager.findCirclesWithExactMembersCount(1)
        assertEquals(1, circlesWithOneMember.size)

        val circlesWithZeroMembers = circleManager.findCirclesWithExactMembersCount(0)
        assertEquals(0, circlesWithZeroMembers.size)

        val circlesWithTwoMembers = circleManager.findCirclesWithExactMembersCount(2)
        assertEquals(0, circlesWithTwoMembers.size)

        val member2 = circleManager.createMember("newMember2", Location(0.0, 0.0))
        circleManager.addMemberToCircle(circleId = circle.id, member2)
        val circlesWithTwoMembersAfterAddMember = circleManager.findCirclesWithExactMembersCount(2)
        assertEquals(1, circlesWithTwoMembersAfterAddMember.size)

        circleManager.removeMemberFromCircle(circleId = circle.id, member1)
        circleManager.removeMemberFromCircle(circleId = circle.id, member2)
        val circlesWithOneMemberAfterRemoval = circleManager.findCirclesWithExactMembersCount(1)
        assertEquals(0, circlesWithOneMemberAfterRemoval.size)
    }

    @Test(expected = IllegalStateException::class)
    fun `findCirclesWithExactMembersCount throws exception if no such circle found`() {
        val member = circleManager.createMember("member", null)
        circleManager.removeMemberFromCircle(circleId = 1, member)
    }

    @Test
    fun `findCirclesWithExactMembersCount returns circle with on changes if member is not added to the circle`() {
        val addedMember = circleManager.createMember("addedMember", null)
        val circle = circleManager.createCircle("circle", listOf(addedMember))
        val notAddedMember = circleManager.createMember("notAddedMember", null)
        val updatedCircle = circleManager.removeMemberFromCircle(circleId = circle.id, notAddedMember)
        assertEquals(circle, updatedCircle)
    }

    @Test
    fun `getAllCircles provides right number of stored circles`() {
        assertEquals(0, circleManager.getAllCircles().size)

        circleManager.createCircle("newCircle1", emptyList())
        assertEquals(1, circleManager.getAllCircles().size)

        val circle2 = circleManager.createCircle("newCircle2", emptyList())
        assertEquals(2, circleManager.getAllCircles().size)

        circleManager.deleteCircle(circle2)
        assertEquals(1, circleManager.getAllCircles().size)
    }

    @Test(expected = IllegalStateException::class)
    fun `deletion of non-added circle throws exception`() {
        circleManager.deleteCircle(Circle(0, "Non-added circle", emptySet()))
    }
}