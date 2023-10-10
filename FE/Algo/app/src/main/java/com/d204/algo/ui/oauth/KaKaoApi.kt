package com.d204.algo.ui.oauth

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.d204.algo.ApplicationClass
import com.d204.algo.MainActivity
import com.d204.algo.data.model.User
import com.d204.algo.data.repository.UserRepository
import com.d204.algo.databinding.DialogSolvedacBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "KaKaoApi"

class KaKaoApi(private val act: AppCompatActivity, private val userRepository: UserRepository) {
    private val espHelper = ApplicationClass.preferencesHelper
    private var solvedConnected = espHelper.prefSolvedAcId != ""

    private fun skipLogin() {
        if (AuthApiClient.instance.hasToken() && espHelper.prefUserId != 0L) {
            val tkn = AuthApiClient.instance.tokenManagerProvider.manager.getToken()!!
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    loadUser(tkn)
                }
            } catch (e: Error) {
                Toast.makeText(act, "카카오 토큰 발급 중 오류 발생", Toast.LENGTH_SHORT).show()
            }
        } else {
            realLoign()
        }
    }

    fun setLoginBtn(loginBtn: ImageButton) {
        // 자동 로그인
//        skipLogin()
        loginBtn.setOnClickListener {
            skipLogin()
        }
    }

    private fun realLoign() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(act, "카카오 계정으로 로그인 실패", Toast.LENGTH_SHORT).show()
            }
            if (token != null) {
                // 액티비티 이동
                val intent = Intent(act, MainActivity::class.java)
                act.startActivity(intent)
                act.finish()
            }
        }
        // kakao 실행 가능 여부
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(act)) {
            Log.d(TAG, "realLoign: dddd")
            // 가능하다면 카카오톡으로 로그인하기
            UserApiClient.instance.loginWithKakaoTalk(act) { token, error ->
                if (error != null) {
                    Toast.makeText(act, "카카오톡으로 로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.e("test", "카카오톡으로 로그인 실패 " + error.message)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                } else if (token != null) {
                    // 서버에 카카오 토큰을 넘겨주는 과정
                    CoroutineScope(Dispatchers.IO).launch {
                        loadUser(token)
                    }

                    Toast.makeText(act, "카카오톡으로 로그인 성공", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "카카오톡 로그인: $token")
                    Log.d("test", "카카오톡으로 로그인 성공" + token.accessToken)
                }
            }
        } else {
            Log.d(TAG, "realLoign: 엘스")
            UserApiClient.instance.loginWithKakaoAccount(act, callback = callback)
        }
    }

    private suspend fun loadUser(kakaoToken: OAuthToken) {
        var isServerActivated: Boolean
        userRepository.getUser(kakaoToken.accessToken, kakaoToken.refreshToken).collect {
            saveUserInfo(kakaoToken, it)
            isServerActivated = it.id > 0L
            // solved ac 연동이 됐다면(DB에 ID가 있다면) 다음 화면으로
            solvedConnected = espHelper.prefSolvedAcId != ""
            Log.d(TAG, "loadUser: $solvedConnected ${espHelper.prefSolvedAcId}")
            if (solvedConnected) {
                toMainActivity(kakaoToken)
            } else if (isServerActivated) {
                withContext(Dispatchers.Main) { connectSolvedAcDialog(kakaoToken) }
            } else {
                withContext(Dispatchers.Main) { Toast.makeText(act, "서버가 혼잡합니다.\n잠시 뒤 다시 시도해주세요.", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun saveUserInfo(kakaoToken: OAuthToken, it: User) {
        espHelper.prefAccessToken = kakaoToken.accessToken
        espHelper.prefRefreshToken = kakaoToken.refreshToken
        espHelper.prefUserId = it.id
        espHelper.prefUserNickname = it.kakaoNickname
        espHelper.prefUserEmail = it.kakaoId
        espHelper.prefUserProfile = it.profileImage
        espHelper.prefUserPreTier = it.preTier
        espHelper.prefUserTier = it.tier
        espHelper.prefSolvedAcId = it.solvedAcId
    }

    private fun toMainActivity(token: OAuthToken) {
        val intent = Intent(act, MainActivity::class.java)
        intent.putExtra("kakaoToken", token)
        act.startActivity(intent)
        act.finish()
    }

    private fun connectSolvedAcDialog(kakaoToken: OAuthToken) {
        val dialogBinding = DialogSolvedacBinding.inflate(act.layoutInflater)
        val code = generateRandomSixDigitValue()
        dialogBinding.solvedAcConnectCode.setOnClickListener {
            dialogBinding.solvedAcConnectCode.text = code
            dialogBinding.solvedAcConnectCode.isClickable = false
        }

        val alertDialog = AlertDialog.Builder(act)
            .setView(dialogBinding.root)
            .create()

        alertDialog.show()

        dialogBinding.solvedAcConnectBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isSuccess = checkValueWithServer(
                    espHelper.prefUserId,
                    dialogBinding.solvedAcConnectId.text.toString(),
                    code,
                )
                if (isSuccess) {
                    withContext(Dispatchers.Main) {
                        dialogBinding.solvedAcConnectBtn.text = "연동 완료"
                        dialogBinding.solvedAcConnectBtn.isEnabled = false
                        Toast.makeText(act, "연동이 성공했습니다.", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                        toMainActivity(kakaoToken)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            act,
                            "연동이 실패했습니다.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
    }

    private suspend fun checkValueWithServer(
        userId: Long,
        solvedAcId: String,
        code: String,
    ): Boolean {
        // 서버에 solved ac api를 넘겨서 bio에 해당 번호가 입력돼있는지 확인하고 solvedAc Id를 받아온다
        val isSolved = userRepository.registerSolvedAc(userId, solvedAcId, code)
        Log.d(TAG, "checkValueWithServer: $userId $solvedAcId $code $isSolved")
        return isSolved == "true"
    }

    private fun generateRandomSixDigitValue(): String {
        return (100000..999999).random().toString()
    }
}
