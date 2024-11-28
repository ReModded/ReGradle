package dev.remodded.retest.common

import dev.remodded.retest.api.ReTestAPI

open class ReTestCommon : ReTestAPI() {
    init {
        println("Init ${Constants.ID}")
    }
}
