package com.challenge.domain.util

import com.challenge.domain.entity.Location
import java.lang.Math.toRadians
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object LocationUtils {

    // TODO: Requires correctness check as this algorithm was taken form the requirement as is
    fun distanceInMeters(location1: Location, location2: Location): Double {
        val theta = location1.lon - location2.lon
        val radLat1 = toRadians(location1.lat)
        val radLat2 = toRadians(location2.lat)
        val radTheta = toRadians(theta)
        var dist = sin(radLat1) * sin(radLat2) + cos(radLat1) * cos(radLat2) * cos(radTheta)
        dist = acos(dist)
        dist = Math.toDegrees(dist)
        return dist * 60.0 * 1.1515 * 1609.344
    }
}