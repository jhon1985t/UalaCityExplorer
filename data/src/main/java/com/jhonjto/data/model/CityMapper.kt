package com.jhonjto.data.model

import com.jhonjto.domain.City

fun CityDto.toDomain(): City = City(
    id = _id,
    name = name,
    country = country,
    lat = coord.lat,
    lon = coord.lon
)
