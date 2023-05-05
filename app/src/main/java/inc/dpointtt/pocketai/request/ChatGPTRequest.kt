package inc.dpointtt.pocketai.request

import com.google.gson.annotations.SerializedName

data class ChatGPTRequest(
    @SerializedName("model") var model: String? = "text-davinci-003",
    @SerializedName("prompt") var prompt: String? = null,
    @SerializedName("max_tokens") var maxTokens: Int? = 100,
    @SerializedName("temperature") var temperature: Int? = 0,
    @SerializedName("n") var n: Int? = 1
)