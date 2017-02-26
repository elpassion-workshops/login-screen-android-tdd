package pl.elpassion.logintdd.loader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView

class LoaderView : FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        addView(getLoader(context))
    }

    companion object {
        var isTest: Boolean = false
        private fun getLoader(context: Context): View {
            return if (!isTest) {
                ProgressBar(context)
            } else {
                TextView(context).apply { text = "Loader" }
            }
        }
    }
}
