package com.devin.ci.cd.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class HomeController {

    @Value("${version}")
    private String version;

    @GetMapping("/")
    public String home(Model model) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(new ClassPathResource("git.properties").getURI()));
        String gitInfo = new String(bytes);
        model.addAttribute("version", version);
        System.out.println(gitInfo);
        model.addAttribute("gitInfo", gitInfo);
        return "home";
    }

}
