package dev.remodded.retest.paper

import dev.remodded.retest.server.ReTestServer
import org.bukkit.Bukkit

class ReTestPaper : ReTestServer() {

    init {
        Bukkit.getServer()
    }
}
