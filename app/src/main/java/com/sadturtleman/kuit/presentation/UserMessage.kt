package com.sadturtleman.kuit.presentation

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUserMessage(): String = when (this) {
    is IOException -> "인터넷 연결을 확인하세요"
    is HttpException -> "서버 오류 (${code()})"
    else -> "알 수 없는 오류"
}