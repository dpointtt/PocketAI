package inc.dpointtt.pocketai.request

import com.google.gson.annotations.SerializedName

data class Choices(
    @SerializedName("text") var text: String? = null,
    @SerializedName("index") var index: Int? = null,
    @SerializedName("logprobs") var logprobs: String? = null,
    @SerializedName("finish_reason") var finishReason: String? = null
)
