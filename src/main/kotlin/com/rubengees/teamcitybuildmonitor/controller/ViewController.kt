package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.ConfigProperties
import org.springframework.core.env.Environment
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Ruben Gees
 */
@Controller
class ViewController(val environment: Environment, val config: ConfigProperties) {

    private val port by lazy { environment.getProperty("local.server.port") }

    @RequestMapping("/buildMonitor")
    fun getBuildMonitor(model: Model): String {
        model.addAttribute("teamcityUrl", config.url)
        model.addAttribute("port", port)

        return "build_monitor"
    }
}
