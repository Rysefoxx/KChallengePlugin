package io.github.rysefoxx.challenge.core.module.impl

import io.github.rysefoxx.challenge.core.module.Module

abstract class ChallengeModule : Module() {

    override val bundleName: String
        get() = "challenges"
}