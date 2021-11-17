package com.example.demo.controllers;

import com.example.demo.models.Assignment;
import com.example.demo.models.User;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AssignmentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/register")
    public String form(){
        return "assignment/form";
    }

    @PostMapping("/register")
    public String form(@Valid Assignment assignment, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("message", "Verifique os campos!");
            return "redirect:/register";
        }
        assignment.setUser(currentUser());
        assignmentRepository.save(assignment);
        attributes.addFlashAttribute("message", "Tarefa cadastrada com sucesso!");
        return "redirect:/register";
    }

    //the correct would be to return a paged list, but this is just a simple study mvc
    @RequestMapping("/assignments")
    public ModelAndView assignmentList(){
        ModelAndView mv = new ModelAndView("assigmentList");
        Iterable<Assignment> assignments = assignmentRepository.findByUser(currentUser());
        mv.addObject("assignments", assignments);
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView AssignmentDetails(@PathVariable("id") long id){
        //in the flow this error wouldn't happen, but anyway if
        // it wasn't just an example, you'd have to make the error explicit
        Assignment assignment = assignmentRepository.findById(id).orElseThrow();

        if (assignment.getUser().getId() != currentUser().getId()) {
            //here an error pops, but it will only happen if someone tries to circumvent the site
            return null;
        } else {
            ModelAndView mv = new ModelAndView("assignment/AssignmentDetails");
            mv.addObject("assignments", assignment);
            return mv;
        }
    }

    @PutMapping("/{id}")
    public String update(@Valid Assignment assignment, RedirectAttributes attributes){
        assignment.setUser(currentUser());
        assignmentRepository.save(assignment);
        attributes.addFlashAttribute("message", "Tarefa cadastrada com sucesso!");
        return "redirect:/assignments";

    }

    @RequestMapping("/delete")
    public String deleteAssignments(long id){
        Assignment assignment = assignmentRepository.findById(id).orElseThrow();
        if (assignment.getUser().getId() != currentUser().getId()) {
            //here an error pops, but it will only happen if someone tries to circumvent the site
            return null;
        } else {
            assignmentRepository.delete(assignment);
            return "redirect:/assignments";
        }
    }

    //the logics shouldn't be here. They are here just because it is a simple study
    private User currentUser(){
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findByLogin(login.orElseThrow()).orElseThrow();
        return user;
    }
}
