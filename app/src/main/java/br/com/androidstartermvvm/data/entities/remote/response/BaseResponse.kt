package br.com.androidstartermvvm.data.entities.remote.response

data class BaseResponse<T>(
    val response: T?,
    val status: StatusResponse
)

data class StatusResponse (
    val success:Boolean,
    val code:Int
)
