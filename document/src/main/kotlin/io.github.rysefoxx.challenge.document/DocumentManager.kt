package io.github.rysefoxx.challenge.document

import com.google.common.collect.ImmutableList
import io.github.rysefoxx.challenge.document.impl.SoundDocument
import io.github.rysefoxx.challenge.document.impl.TimerDocument
import io.github.rysefoxx.challenge.document.impl.TimerExtensionDocument
import io.github.rysefoxx.challenge.document.impl.challenge.EnderDragonDocument

object DocumentManager {

    private lateinit var configList: ImmutableList<AbstractDocument>
    private var loaded = false

    fun loadAll() {
        if (loaded) return

        configList = ImmutableList.of(
            TimerExtensionDocument,
            TimerDocument,
            SoundDocument,
            EnderDragonDocument
        )

        loaded = true
    }
}