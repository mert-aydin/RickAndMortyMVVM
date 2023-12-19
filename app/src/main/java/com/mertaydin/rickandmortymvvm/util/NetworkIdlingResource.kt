package com.mertaydin.rickandmortymvvm.util

import androidx.test.espresso.IdlingResource

object NetworkIdlingResource : IdlingResource {
    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    @Volatile
    var isIdle = false
        set(idle) {
            field = idle
            if (idle) {
                callback?.onTransitionToIdle()
            }
        }

    override fun getName(): String = javaClass.name

    override fun isIdleNow() = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}
