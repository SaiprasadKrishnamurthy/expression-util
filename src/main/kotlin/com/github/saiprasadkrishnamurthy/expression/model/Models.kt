package com.github.saiprasadkrishnamurthy.expression.model

enum class PredicateGroupType {
    OR, AND
}

enum class OperatorType {
    EqualTo,
    NotEqualTo,
    ArrayNotExists,
    ArrayContains,
    StringContains,
    ArrayContainsAll,
    GreaterThan,
    GreaterThanEqualTo,
    LesserThan,
    LesserThanEqualTo,
    Contains;
}

data class Predicate(
        val field: String,
        val operator: OperatorType,
        val value: Any
)

data class PredicateGroup(
        val groupType: PredicateGroupType,
        val predicates: List<Predicate>,
        val nested: PredicateGroup? = null
)

interface OperatorRepository {
    fun find(operator: OperatorType, fieldName: String, value: Any): String
}

interface PredicateGroupTypeRepository {
    fun find(predicateGroupType: PredicateGroupType): String
}

interface ExpressionService {
    fun build(predicateGroup: PredicateGroup): String
}
