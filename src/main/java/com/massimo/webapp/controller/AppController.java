package com.massimo.webapp.controller;

import com.massimo.webapp.model.*;
import com.massimo.webapp.service.MaritalStatusService;
import com.massimo.webapp.service.SkillService;
import com.massimo.webapp.service.UserDocumentService;
import com.massimo.webapp.service.UserService;
import com.massimo.webapp.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.ArrayList;
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

    @Autowired
    UserDocumentService userDocumentService;

    @Autowired
    FileValidator fileValidator;



    @InitBinder("fileBucket")
    protected void initBinder(WebDataBinder webDataBinder){

        webDataBinder.setValidator(fileValidator);
    }

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
   * GO TO ADD DOCUMENT
   *
   * */
   @RequestMapping(value = "add-document-{userId}", method = RequestMethod.GET)
   public String addDocument(@PathVariable int userId, ModelMap modelMap){

       User user = userService.findUserById(userId);

       FileBucket file = new FileBucket();

       modelMap.addAttribute("user", user);
       modelMap.addAttribute("fileBucket", file);
       modelMap.addAttribute("editDoc", false);
       return "manageDocuments";

   }

   /*
   *
   *
   * UPLOAD A DOCUMENT
   *
   * */

   @RequestMapping(value = "/add-document-{userId}", method = RequestMethod.POST)
   public String uploadDocument(@Valid FileBucket fileBucket, BindingResult result, @PathVariable int userId, ModelMap model) throws IOException {
       if (result.hasErrors()) {
           System.out.println("validation errors");
           model.addAttribute("error", "has-danger");

           return "manageDocuments";
       } else {

           System.out.println("Fetching file");

           User user = userService.findUserById(userId);
           model.addAttribute("user", user);

           saveDocument(fileBucket, user);

           return "redirect:/";
       }
   }

   /*
   *
   *
   * DOWNLOAD DOCUMENT
   *
   * */
   @RequestMapping(value = "download-document-{docId}", method = RequestMethod.GET)
   public String downloadDocument(@PathVariable int docId, HttpServletResponse response) throws IOException {
       UserDocument userDocument = userDocumentService.findById(docId);
       response.setContentType(userDocument.getType());
       response.setContentLength(userDocument.getContent().length);
       response.setHeader("Content-Disposition","attachment; filename=\"" + userDocument.getName() +"\"");

       FileCopyUtils.copy(userDocument.getContent(), response.getOutputStream());

       return "redirect:/";

   }


    private void saveDocument(FileBucket fileBucket, User user) throws IOException {
       UserDocument userDocument = new UserDocument();

        MultipartFile file = fileBucket.getFile();

        userDocument.setContent(file.getBytes());
        userDocument.setDescription(fileBucket.getDescription());
        userDocument.setName(file.getOriginalFilename());
        userDocument.setType(file.getContentType());
        userDocument.setUser(user);

        userDocumentService.saveDocument(userDocument);

    }

    /*
    *
    * DELETE DOCUMENT
    *
    * */
    @RequestMapping(value = "/delete-document-{docId}", method = RequestMethod.GET)
    public String deleteDocument(@PathVariable int docId){

        userDocumentService.deleteById(docId);
        return "redirect:/";
    }

    /*
    *
    *
    * GO TO EDIT FILE
    *
    * */
    @RequestMapping(value = "/edit-document-{docId}", method = RequestMethod.GET)
    public String editFile(@PathVariable int docId, ModelMap modelMap){

        UserDocument userDocument = userDocumentService.findById(docId);
        FileBucket fileBucket = new FileBucket();

        fileBucket.setDescription(userDocument.getDescription());

        MockMultipartFile mockMultipartFile = new MockMultipartFile(userDocument.getName(), userDocument.getName()
        ,userDocument.getType(), userDocument.getContent());

        fileBucket.setFile(mockMultipartFile);
        modelMap.addAttribute("editDoc", true);
        modelMap.addAttribute("fileBucket", fileBucket);

        return "manageDocuments";



    }

    /*
    *
    *
    * UPDATE DOCUMENT
    *
    * */
    @RequestMapping(value = "/edit-document-{docId}", method = RequestMethod.POST)
    public String updateDocument(@Valid FileBucket fileBucket, BindingResult result,
                             ModelMap modelMap, @PathVariable int docId) throws IOException {

        if (result.hasErrors()) {
            System.out.println("validation errors");
            modelMap.addAttribute("error", "has-danger");

            return "manageDocuments";
        }else {
            UserDocument userDocument = userDocumentService.findById(docId);

            MultipartFile file = fileBucket.getFile();

            userDocumentService.updateDocument(userDocument, fileBucket, file);

            return "redirect:/";
        }

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
