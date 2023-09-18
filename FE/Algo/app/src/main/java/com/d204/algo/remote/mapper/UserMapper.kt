package com.d204.algo.remote.mapper

import com.d204.algo.data.model.User
import com.d204.algo.remote.model.UserModel
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserModel, User> {
    override fun mapFromModel(model: UserModel): User {
        return User(
            id = model.id,
            kakaoId = model.kakaoId,
            kakaoNickname = model.kakaoNickname,
            profileImage = model.profileImage,
            preTier = model.preTier,
        )
    }

    override fun mapToModel(type: User): UserModel {
        return UserModel(
            id = type.id,
            kakaoId = type.kakaoId,
            kakaoNickname = type.kakaoNickname,
            profileImage = type.profileImage,
            preTier = type.preTier,
        )
    }
}
