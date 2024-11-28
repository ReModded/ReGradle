package dev.remodded.regradle.modules.server

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import org.gradle.api.Project

class ServerModulePlugin : ModulePlugin(ModuleType.SERVER) {
    override fun apply(project: Project) = with(project) {
        super.apply(this)
    }
}
