package dev.remodded.retest.sponge_api12

import dev.remodded.retest.server.ReTestServer
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.StartedEngineEvent

class ReTestSponge : ReTestServer() {
    @Listener
    fun onEnable(ev: StartedEngineEvent<Server>) {
        println("ReTest Sponge API 1.12 enabled")
        Sponge.server()
    }
}
