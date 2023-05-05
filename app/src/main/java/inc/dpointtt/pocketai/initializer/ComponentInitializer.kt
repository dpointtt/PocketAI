package inc.dpointtt.pocketai.initializer

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import inc.dpointtt.pocketai.annotation.Tag
import java.lang.reflect.Field

open class ComponentInitializer(
    private var activity: AppCompatActivity,
    private var initView: ViewGroup
) : ButtonInitializer() {

    override fun initialize() {
        for(field: Field in activity::class.java.declaredFields){
            if(field.isAnnotationPresent(Tag::class.java)){
                field.isAccessible = true
                val tag: String = field.getAnnotation(Tag::class.java)?.tag ?: error("Not found")
                attachComponents(field, tag)
            }
        }
        super.initialize(activity)
    }

    private fun attachComponents(field: Field, tag: String){
        val handler = Handler(Looper.getMainLooper())
        if(View::class.java.isAssignableFrom(field.type)){
            val component = initView.findViewWithTag<View>(tag)
                ?: throw RuntimeException("Component not found! TAG=${tag}")
            if(field.type.isInstance(component)){
                field.set(activity, component)
                handler.post {
                    (component).let { component ->
                        (component.parent as? ViewGroup)?.removeView(component)
                    }
                    initView.addView(component)
                }
            }
        }
    }

}