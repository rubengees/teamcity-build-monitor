package com.rubengees.teamcitybuildmonitor.controller

import org.springframework.core.env.Environment
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Ruben Gees
 */
@Controller
class ViewController(val environment: Environment) {

    private val port by lazy { environment.getProperty("local.server.port") }

    @RequestMapping("/buildMonitor")
    fun getBuildMonitor(model: Model): String {
        model.addAttribute("port", port)

        return "build_monitor"
    }
}
