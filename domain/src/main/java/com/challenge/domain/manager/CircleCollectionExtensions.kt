package com.challenge.domain.manager

import com.challenge.domain.entity.Circle

/**
 * Counts total number of [Circle] with same IDs
 */
fun Iterable<Circle>.countDuplicates(): Int =
    this.groupingBy { it.id }.eachCount().values
        // -1 stands for removing non-duplicate item
        .fold(0) { acc, i -> acc + i - 1 }

/**
 * Returns list of [Circle] with unique IDs.
 * In case several circles have the same IDs it returns the one with highest number of [Circle.members].
 * Behaviour in case, when two circles have the same number of members is undefined, any of them can be returned.
 */
fun Iterable<Circle>.removeDuplicates(): List<Circle> {
    val circleItemsNumberComparator = compareBy { circle: Circle -> circle.members.size }
    val resultMap = hashMapOf<Int, Circle>()
    forEach { a ->
        resultMap[a.id] = resultMap[a.id]?.let { b -> maxOf(a, b, circleItemsNumberComparator) } ?: a
    }
    return resultMap.values.toList()
}

