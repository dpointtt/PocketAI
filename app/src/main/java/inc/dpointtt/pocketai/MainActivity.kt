package inc.dpointtt.pocketai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import inc.dpointtt.pocketai.annotation.OnClick
import inc.dpointtt.pocketai.annotation.Tag
import inc.dpointtt.pocketai.initializer.ComponentInitializer

class MainActivity : AppCompatActivity() {

    @Tag("startButton")
    @OnClick("startButtonFun")
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        val root : ViewGroup = LayoutInflater.from(this).inflate(R.layout.activity_main, null) as ViewGroup
        setContentView(root)
        ComponentInitializer(this, root).initialize()
    }

    fun startButtonFun(){
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
        finish()
    }

}