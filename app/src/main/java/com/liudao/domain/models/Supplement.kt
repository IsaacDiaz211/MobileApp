package com.liudao.domain.models

import java.time.LocalDate

data class Supplement(
    val id: Long = 0,
    val name: String,
    val start: LocalDate
)
