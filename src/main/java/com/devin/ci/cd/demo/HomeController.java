package com.devin.ci.cd.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${version}")
    private String version;

    @Value("${branch}")
    private String branch;

    @Value("${git.branch}")
    private String gitBranch;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("branch", gitBranch);
        model.addAttribute("version", version);
        return "home";
    }

}
