package com.massimo.webapp.controller;

import com.massimo.webapp.model.MaritalStatus;
import com.massimo.webapp.model.Skill;
import com.massimo.webapp.model.User;
import com.massimo.webapp.service.MaritalStatusService;
import com.massimo.webapp.service.SkillService;
import com.massimo.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    MaritalStatusService maritalStatusService;

    @Autowired
    SkillService skillService;

    /*
    *
    *
    * LIST ALL USERS
    *
    * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String usersList(ModelMap modelMap){

        List<User> users = userService.getAllUsers();
        modelMap.addAttribute("users", users);

        return "list";
    }

   /*
   *
   *
   * GO TO ADD USER
   *
   * */
   @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String addUser(ModelMap modelMap){
       User user = new User();
       modelMap.addAttribute("user", user);
       modelMap.addAttribute("edit", false);
       return "registrationForm";
   }

   /*
   *
   *
   * ADD USER
   *
   * */
   @RequestMapping(value = "/newUser", method = RequestMethod.POST)
   public String saveUser(@Valid User user, BindingResult result,
                          ModelMap modelMap){
       if(result.hasErrors()){
           List<FieldError> errors = result.getFieldErrors();
           for(FieldError fieldError: errors){
               if(fieldError.getField().equals("firstname"))
                   modelMap.addAttribute("errorFirst", "has-danger");
               if(fieldError.getField().equals("lastname"))
                   modelMap.addAttribute("errorLast", "has-danger");
               if(fieldError.getField().equals("birthDate"))
                   modelMap.addAttribute("errorBirthDate", "has-danger");
           }
           modelMap.addAttribute("edit", false);
           return "registrationForm";
       }

       userService.saveUser(user);
       return "redirect:/";
   }

    /*
     *
     *
     * GO TO EDIT FORM
     *
     * */
    @RequestMapping(value = "/edit-user-{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable int id, ModelMap modelMap){
        User user = userService.findUserById(id);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("edit", true);
        return "registrationForm";
    }


    /*
    *
    *
    * EDIT USER
    *
    * */
    @RequestMapping(value = "/edit-user-{id}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap modelMap, @PathVariable int id){
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError: errors){
                if(fieldError.getField().equals("firstname"))
                    modelMap.addAttribute("errorFirst", "has-danger");
                if(fieldError.getField().equals("lastname"))
                    modelMap.addAttribute("errorLast", "has-danger");
                if(fieldError.getField().equals("birthDate"))
                    modelMap.addAttribute("errorBirthDate", "has-danger");
            }
            return "registrationForm";
        }
        userService.updateUser(user);

        return "redirect:/";
    }

   /*
   *
   *
   * DELETE A USER
   *
   * */

   @RequestMapping(value = "/delete-user-{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id){
       userService.deleteUser(id);
       return "redirect:/";
   }

   /*
   *
   *
   * SEARCH USER
   *
   * */
   @RequestMapping(value = "/users", method = RequestMethod.GET)
   public String searchUser(@RequestParam("term") String search, ModelMap modelMap){

       List<User> users = userService.searchUsers(search);
       modelMap.addAttribute("users", users);
       modelMap.addAttribute("reset", true);
       return "list";
   }


   /*
   *
   *
   * PASSO LISTA DI MARITAL STATUS E SKILLS ALLA FORM
   *
   * */
    @ModelAttribute("maritalStatus")
    public List<MaritalStatus> initializeMaritalStatus() {
       List<MaritalStatus> maritalStatusList = maritalStatusService.findAllMaritalStatus();
        return maritalStatusList;
    }

    @ModelAttribute("skills")
    public List<Skill> initializeSkills() {
        List<Skill> skillsList = skillService.findAllSkills();
        return skillsList;
    }


}
