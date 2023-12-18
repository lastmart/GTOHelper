package com.gtohelper.common

interface Mapper<TIn, TOut> {
    fun transform(data: TIn): TOut
}