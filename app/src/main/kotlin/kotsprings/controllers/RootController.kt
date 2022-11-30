package kotsprings.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class RootController {

    @GetMapping("/")
    fun index(): ModelAndView {
        return ModelAndView("redirect:/health")
    }
}