package com.challenge.domain

import com.challenge.domain.entity.Circle
import com.challenge.domain.entity.Member
import com.challenge.domain.manager.countDuplicates
import com.challenge.domain.manager.removeDuplicates
import org.junit.Assert.assertEquals
import org.junit.Test

internal class CircleCollectionExtensionsKtTest {

    private val circlesWithDuplicates = listOf(
        Circle(0, "original 0", setOf(Member(0, "", null))),
        Circle(
            0, "duplicate 0", setOf(
                Member(1, "", null),
                Member(2, "", null)
            )
        ),
        Circle(1, "original 1", setOf(Member(3, "", null))),
        Circle(1, "duplicate 1", emptySet()),
        Circle(2, "original 2", emptySet()),
    )

    private val circlesWithNoDuplicates = listOf(
        Circle(0, "original 0", emptySet()),
        Circle(1, "original 1", emptySet()),
        Circle(2, "original 2", emptySet()),
        Circle(3, "original 3", emptySet())
    )

    private val circlesRemovedDuplicates = listOf(
        Circle(
            0, "duplicate 0", setOf(
                Member(1, "", null),
                Member(2, "", null)
            )
        ),
        Circle(1, "original 1", setOf(Member(3, "", null))),
        Circle(2, "original 2", emptySet()),
    )

    @Test
    fun `countDuplicates finds duplicates`() {
        val duplicatesCount = circlesWithDuplicates.countDuplicates()
        assertEquals(2, duplicatesCount)
    }

    @Test
    fun `countDuplicates finds no duplicates`() {
        val duplicatesCount = circlesWithNoDuplicates.countDuplicates()
        assertEquals(0, duplicatesCount)
    }

    @Test
    fun `countDuplicates finds no duplicates in empty collection`() {
        val duplicatesCount = emptyList<Circle>().countDuplicates()
        assertEquals(0, duplicatesCount)
    }

    @Test
    fun `removeDuplicates returns circlesRemovedDuplicates`() {
        val listWithNoDuplicates = circlesWithDuplicates.removeDuplicates()
        assertEquals(circlesRemovedDuplicates, listWithNoDuplicates)
    }
}