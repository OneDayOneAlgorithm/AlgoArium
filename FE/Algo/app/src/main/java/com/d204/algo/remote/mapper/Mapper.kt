package com.d204.algo.remote.mapper

interface Mapper<E, D> {
    fun mapFromModel(model: E): D
    fun mapToModel(type: D): E
}
