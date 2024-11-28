package dev.remodded.regradle.modules

import dev.remodded.regradle.regradle
import org.gradle.api.Project

val Project.moduleType: ModuleType
    get() = regradle()?.module ?: ModuleType.None
