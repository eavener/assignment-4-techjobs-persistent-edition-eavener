package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "All Skills");
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillsForm(Model model) {
        model.addAttribute("title", "Create Skill");
        model.addAttribute("skill", new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Skill");
            model.addAttribute(new Skill());
            return "skills/add";
        }
        skillRepository.save(newSkill);
        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        Optional<Skill> optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("title", skill.getName() + " Details");
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            model.addAttribute("title", "Invalid Skill ID: " + skillId);
            return "redirect:../";
        }
    }
}
