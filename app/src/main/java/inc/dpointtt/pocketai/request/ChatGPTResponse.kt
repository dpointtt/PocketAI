package inc.dpointtt.pocketai.request

import com.google.gson.annotations.SerializedName

data class ChatGPTResponse (
    @SerializedName("id") var id: String? = null,
    @SerializedName("object") var oobject: String? = null,
    @SerializedName("created") var created: Int? = null,
    @SerializedName("model") var model: String? = null,
    @SerializedName("choices") var choices: ArrayList<Choices> = arrayListOf(),
    @SerializedName("usage") var usage: Usage? = Usage()
)