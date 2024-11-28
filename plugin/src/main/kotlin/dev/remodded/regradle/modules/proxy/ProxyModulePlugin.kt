package dev.remodded.regradle.modules.proxy

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import org.gradle.api.Project

class ProxyModulePlugin : ModulePlugin(ModuleType.PROXY) {
    override fun apply(project: Project) = with(project) {
        super.apply(this)
    }
}
