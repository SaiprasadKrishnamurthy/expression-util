package com.github.saiprasadkrishnamurthy.expression.repository

import com.github.saiprasadkrishnamurthy.expression.model.OperatorRepository
import com.github.saiprasadkrishnamurthy.expression.model.OperatorType
import java.util.*

class MvelOperatorRepository : OperatorRepository {
    override fun find(operator: OperatorType, fieldName: String, value: Any): String {
        val props = Properties()
        props.load(MvelOperatorRepository::class.java.classLoader.getResourceAsStream("mvel_operators.properties").bufferedReader())
        val clause = props[operator.name]
        if (clause != null) {
            return when (value) {
                is String -> String.format(clause.toString(), fieldName, "'$value'")
                is List<*> -> {
                    val elements = when (value[0]) {
                        is String -> value.joinToString(",") { "\"$it\"" }
                        else -> value.joinToString(",")
                    }
                    String.format(clause.toString(), fieldName, elements)
                }
                else -> String.format(clause.toString(), fieldName, value.toString())
            }
        }
        throw UnsupportedOperationException(" Operator $operator not supported by MVEL expressions.")
    }
}