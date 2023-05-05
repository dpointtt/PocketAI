package inc.dpointtt.pocketai.initializer

import androidx.appcompat.app.AppCompatActivity

interface ActivityInitializer : Initializer{
    override fun initialize() {}
    fun initialize(activity: AppCompatActivity)
}