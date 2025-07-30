package com.liudao.domain.models

import java.time.LocalDate

class Period(
    val id: Long = 0,
    val suppId: Long,
    val start: LocalDate,
    val end: LocalDate? = null
)