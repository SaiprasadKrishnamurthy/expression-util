package com.github.saiprasadkrishnamurthy.expression

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.saiprasadkrishnamurthy.expression.model.PredicateGroup
import com.github.saiprasadkrishnamurthy.expression.repository.MvelOperatorRepository
import com.github.saiprasadkrishnamurthy.expression.service.MvelExpressionService
import java.io.FileInputStream


object Foo {
    @JvmStatic
    fun main(args: Array<String>) {
        val mapper = jacksonObjectMapper()
        val p = mapper.readValue(FileInputStream("req1.json"), PredicateGroup::class.java)
        println(p)
        val o = MvelExpressionService(MvelOperatorRepository()).build(p)
        println(o)
    }

}