package com.massimo.webapp.controller;

import com.massimo.webapp.notifications.NotificationType;
import com.massimo.webapp.notifications.Notifications;
import com.massimo.webapp.model.*;
import com.massimo.webapp.service.MaritalStatusService;
import com.massimo.webapp.service.SkillService;
import com.massimo.webapp.service.UserDocumentService;
import com.massimo.webapp.service.UserService;
import com.massimo.webapp.utils.ValidationManager;
import com.massimo.webapp.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
                          ModelMap modelMap, RedirectAttributes redirectAttributes){
       if(result.hasErrors()){

           //Validate fields for notification
           ValidationManager.showNotifications(result,modelMap);
           return "registrationForm";
       }


       userService.saveUser(user);

       //Control parameter
       String operation = "insertUser";
       //Validate for insert and notifications
       ValidationManager.controlOperationAndNotify(operation, redirectAttributes);

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
                             ModelMap modelMap, @PathVariable int id, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){

            //Validate for notifications
            ValidationManager.showNotifications(result,modelMap);
            return "registrationForm";
        }
        userService.updateUser(user);

        //Control parameter
        String operation = "editUser";
        //Validate for edit user and notifications
        ValidationManager.controlOperationAndNotify(operation, redirectAttributes);
        return "redirect:/";
    }

   /*
   *
   *
   * DELETE A USER
   *
   * */

   @RequestMapping(value = "/delete-user-{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id, RedirectAttributes redirectAttributes){
       userService.deleteUser(id);
       //Control parameter
       String operation = "deleteUser";
       //Validate for notification
       ValidationManager.controlOperationAndNotify(operation, redirectAttributes);
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

       ValidationManager.validateSearch(modelMap,users.size());
       modelMap.addAttribute("users", users);
       modelMap.addAttribute("reset", true);
       modelMap.addAttribute("resultSize", users.size());
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
   public String uploadDocument(@Valid FileBucket fileBucket, BindingResult result,
                                @PathVariable int userId, ModelMap model, RedirectAttributes redirectAttributes) throws IOException {
       if (result.hasErrors()) {

           ValidationManager.validateFile(model);

           return "manageDocuments";
       } else {

           System.out.println("Fetching file");

           User user = userService.findUserById(userId);
           model.addAttribute("user", user);

           saveDocument(fileBucket, user);
           //Control parameter
           String operation = "uploadDocument";
           //Validation for notify in document upload
           ValidationManager.controlOperationAndNotify(operation, redirectAttributes);

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
    public String deleteDocument(@PathVariable int docId, RedirectAttributes redirectAttributes){

        userDocumentService.deleteById(docId);

        //Control parameter
        String operation = "deleteDocument";
        //Validation for notify deleted document
        ValidationManager.controlOperationAndNotify(operation, redirectAttributes);
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
                             ModelMap modelMap, @PathVariable int docId,
                                 RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()) {
            System.out.println("validation errors");
            ValidationManager.validateFile(modelMap);
            modelMap.addAttribute("error", "has-danger");

            return "manageDocuments";
        }else {
            UserDocument userDocument = userDocumentService.findById(docId);

            MultipartFile file = fileBucket.getFile();

            userDocumentService.updateDocument(userDocument, fileBucket, file);

            //Control parameter
            String operation = "editDocument";
            //Validate for notify updated document
            ValidationManager.controlOperationAndNotify(operation, redirectAttributes);
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
