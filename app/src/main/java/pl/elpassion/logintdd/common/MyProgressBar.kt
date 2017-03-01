package pl.elpassion.logintdd.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView

class MyProgressBar : FrameLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

    init {
        if (Animations.areEnabled) {
            addView(ProgressBar(context))
        } else {
            addView(TextView(context).apply {
                text = "loading"
            })
        }
    }
}

object Animations {
    var areEnabled: Boolean = true
}
