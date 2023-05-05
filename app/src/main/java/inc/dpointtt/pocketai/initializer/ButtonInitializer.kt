package inc.dpointtt.pocketai.initializer

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import inc.dpointtt.pocketai.annotation.OnClick

open class ButtonInitializer : ActivityInitializer {
    override fun initialize(activity: AppCompatActivity) {
        activity::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            field.getAnnotation(OnClick::class.java)?.let { clickableAnnotation ->
                val view = field.get(activity) as? View ?: return@let
                view.setOnClickListener {
                    val clickFunctionName = clickableAnnotation.function
                    val clickFunction = activity::class.java.methods.find {
                        it.name == clickFunctionName
                    } ?: return@setOnClickListener
                    clickFunction.invoke(activity)
                }
            }
        }
    }

}