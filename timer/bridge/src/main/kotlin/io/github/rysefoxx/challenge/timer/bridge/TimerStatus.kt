package io.github.rysefoxx.challenge.timer.bridge

enum class TimerStatus {

    /**
     * Called when the plugin is not on the server at all.
     */
    PLUGIN_UNLOADED,
    /**
     * Called when the plugin is on the server but the timer is paused.
     */
    TIMER_PAUSED,
    /**
     * Called when the plugin is on the server and the timer is running.
     */
    TIMER_RUNNING

}