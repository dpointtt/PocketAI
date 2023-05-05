package inc.dpointtt.pocketai.request

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// https://api.openai.com/
// v1/completions

interface OpenAI {
    @Headers("Content-Type: application/json", "Authorization: Bearer APIKEY")
    @POST("v1/completions")
    fun postChatGPT(@Body body: ChatGPTRequest) : Call<ChatGPTResponse>
}