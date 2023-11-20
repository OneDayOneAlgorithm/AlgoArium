package com.d204.algo.remote.mapper

import com.d204.algo.data.model.Problem
import com.d204.algo.remote.model.ProblemModel
import javax.inject.Inject

class ProblemMapper @Inject constructor() : Mapper<ProblemModel, Problem> {
    override fun mapFromModel(model: ProblemModel): Problem {
        return Problem(
            id = model.id,
            problemNumber = model.problemNumber,
            title = model.title,
            problemTag = model.problemTag,
            problemLevel = model.problemLevel,
            solvedUserCount = model.solvedUserCount,
            problemLike = model.problemLike,
            problemMemo = model.problemMemo,
            userId = model.userId,
        )
    }

    override fun mapToModel(type: Problem): ProblemModel {
        return ProblemModel(
            id = type.id,
            problemNumber = type.problemNumber,
            title = type.title,
            problemTag = type.problemTag,
            problemLevel = type.problemLevel,
            solvedUserCount = type.solvedUserCount,
            problemLike = type.problemLike,
            problemMemo = type.problemMemo,
            userId = type.userId,
        )
    }
}
