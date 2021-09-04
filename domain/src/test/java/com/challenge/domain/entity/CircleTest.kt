package com.challenge.domain.entity

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CircleTest {

    private lateinit var circle: Circle

    @Before
    fun setUp() {
        circle = Circle(
            0, "Circle", setOf(
                Member(0, "In Area member", Location(0.1, 0.1)),
                Member(1, "In Area member", Location(0.1001, 0.1001)),
                Member(1, "Out of Area member", Location(3.0, 0.1)),
                Member(2, "Wanted", null)
            )
        )
    }

    @Test
    fun `getCircleMember returns correct member`() {
        val member = circle.getCircleMember(memberId = 2)
        assertEquals("Wanted", member!!.name)
    }

    @Test
    fun `findMembersWithinArea returns members in area`() {
        val membersInArea = circle.findMembersWithinArea(
            searchCenter = Location(0.1, 0.1),
            searchRadius = 200.0
        )
        assertTrue(membersInArea.any { it.id == 0 } && membersInArea.any { it.id == 1 })
    }

}