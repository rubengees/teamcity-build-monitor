package com.rubengees.teamcitybuildmonitor.controller

import com.rubengees.teamcitybuildmonitor.ConfigProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * @author Ruben Gees
 */
@Controller
class ViewController(val config: ConfigProperties) {

    @RequestMapping("/buildMonitor", method = [GET])
    fun buildMonitor(model: Model): String {
        model.addAttribute("teamcityUrl", config.url)

        return "build-monitor"
    }
}
