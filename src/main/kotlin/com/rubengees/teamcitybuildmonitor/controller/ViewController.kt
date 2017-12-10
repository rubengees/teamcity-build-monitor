package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.ConfigProperties
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Ruben Gees
 */
@Controller
class ViewController(private val config: ConfigProperties) {

//    @LocalServerPort
//    private lateinit var port: String

    @RequestMapping("/buildMonitor")
    fun getBuildMonitor(model: Model): String {
        model.addAttribute("port", 8080)
        model.addAttribute("interval", config.interval)

        return "build_monitor"
    }
}
