package inc.dpointtt.pocketai

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import inc.dpointtt.pocketai.annotation.OnClick
import inc.dpointtt.pocketai.annotation.Tag
import inc.dpointtt.pocketai.chat.ChatAdapter
import inc.dpointtt.pocketai.chat.ChatMessage
import inc.dpointtt.pocketai.initializer.ComponentInitializer
import inc.dpointtt.pocketai.request.ChatGPTRequest
import inc.dpointtt.pocketai.request.ChatGPTResponse
import inc.dpointtt.pocketai.request.OpenAI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {

    @Tag("editText")
    private lateinit var editText: EditText

    @Tag("sendButton")
    @OnClick("sendMessage")
    private lateinit var sendButton: ImageButton

    @Tag("chatView")
    private lateinit var messageRecyclerView: RecyclerView

    private lateinit var messageAdapter: ChatAdapter
    private lateinit var messageLayoutManager: LinearLayoutManager
    private val messageList = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val root : ViewGroup = LayoutInflater.from(this).inflate(R.layout.activity_chat, null) as ViewGroup
        setContentView(root)
        ComponentInitializer(this, root).initialize()

        messageLayoutManager = LinearLayoutManager(this)
        messageRecyclerView.layoutManager = messageLayoutManager

        messageAdapter = ChatAdapter(messageList)
        messageRecyclerView.adapter = messageAdapter

    }

    fun sendMessage(){
        // todo: send request to API and wait for response
        // todo: block button and edittext while waiting for response
        // todo: show response on the screen and unblock components

        editText.isEnabled = false
        sendButton.isClickable = false

        val message = ChatMessage(
            userName = "You",
            messageText = editText.text.toString(),
            avatarId = R.drawable.user_avatar
        )

        editText.text.clear()

        messageList.add(message)
        messageAdapter.notifyItemInserted(messageList.size - 1)

        val tempMessage = ChatMessage(
            messageText = "Writing...",
            avatarId = R.drawable.ai_avatar
        )

        messageList.add(tempMessage)
        messageAdapter.notifyItemInserted(messageList.size - 1)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OpenAI::class.java)

        println("My message: " + message.messageText)

        val call: Call<ChatGPTResponse> = service.postChatGPT(ChatGPTRequest(prompt = message.messageText))

        call.enqueue(object: Callback<ChatGPTResponse>{
            override fun onResponse(
                call: Call<ChatGPTResponse>,
                response: Response<ChatGPTResponse>
            ) {
                if (response.isSuccessful){
                    val chatResponse = ChatMessage(
                        messageText = response.body()?.choices?.get(0)?.text.toString(),
                        avatarId = R.drawable.ai_avatar
                    )
                    val index = messageList.indexOf(tempMessage)
                    messageList.remove(tempMessage)
                    messageList.add(index, chatResponse)
                    messageAdapter.notifyItemChanged(index)

                    messageRecyclerView.scrollToPosition(messageList.lastIndex)
                }
                else{
                    val chatResponse = ChatMessage(
                        messageText = "Error",
                        avatarId = R.drawable.ai_avatar
                    )
                    val index = messageList.indexOf(tempMessage)
                    messageList.remove(tempMessage)
                    messageList.add(index, chatResponse)
                    messageAdapter.notifyItemChanged(index)

                    messageRecyclerView.scrollToPosition(messageList.lastIndex)
                }
            }

            override fun onFailure(call: Call<ChatGPTResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

        editText.isEnabled = true
        sendButton.isClickable = true
    }

}