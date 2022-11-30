package kotsprings.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class RootController {

    @GetMapping("/")
    fun index(): String {
        var redirectView = RedirectView()
        return "redirect:/health"
    }
}