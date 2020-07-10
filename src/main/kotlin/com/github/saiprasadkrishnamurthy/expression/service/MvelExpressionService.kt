package com.github.saiprasadkrishnamurthy.expression.service

import com.github.saiprasadkrishnamurthy.expression.model.ExpressionService
import com.github.saiprasadkrishnamurthy.expression.model.Predicate
import com.github.saiprasadkrishnamurthy.expression.model.PredicateGroup
import com.github.saiprasadkrishnamurthy.expression.repository.MvelOperatorRepository

class MvelExpressionService constructor(private val mvelOperatorRepository: MvelOperatorRepository) : ExpressionService {
    override fun build(predicateGroup: PredicateGroup): String {
        val out = StringBuilder()
        buildConditions(out, predicateGroup, null, true)
        return out.toString()
    }

    private fun buildConditions(out: StringBuilder, blueprintPredicateGroup: PredicateGroup?, previous: PredicateGroup?, first: Boolean) {
        if (blueprintPredicateGroup != null) {
            if (!first)
                out.append(" ").append(previous?.groupType?.name)
            val clauses = blueprintPredicateGroup.predicates.joinToString(separator = " ${blueprintPredicateGroup.groupType.name} ") {
                clauses(it)
            }
            out.append(" (").append(clauses)
            buildConditions(out, blueprintPredicateGroup.nested, blueprintPredicateGroup, false)
            out.append(")")
        }
    }

    private fun clauses(bp: Predicate): String = mvelOperatorRepository.find(bp.operator, bp.field, bp.value)

}