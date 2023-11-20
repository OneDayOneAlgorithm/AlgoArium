package com.d204.algo.remote.mapper

import com.d204.algo.data.model.Ranking
import com.d204.algo.remote.model.RankingModel
import javax.inject.Inject

class RankingMapper @Inject constructor() : Mapper<RankingModel, Ranking> {
    override fun mapFromModel(model: RankingModel): Ranking {
        return Ranking(
            id = model.id,
            userId = model.userId,
            score = model.score,
            ranking = model.ranking,
            tier = model.tier,
            kakaoNickname = model.kakaoNickname,
            profileImage = model.profileImage,
        )
    }

    override fun mapToModel(type: Ranking): RankingModel {
        return RankingModel(
            id = type.id,
            userId = type.userId,
            score = type.score,
            ranking = type.ranking,
            tier = type.tier,
            kakaoNickname = type.kakaoNickname,
            profileImage = type.profileImage,
        )
    }
}
