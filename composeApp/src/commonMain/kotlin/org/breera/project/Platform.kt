package org.breera.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform