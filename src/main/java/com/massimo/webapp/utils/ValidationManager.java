package com.massimo.webapp.utils;

import com.massimo.webapp.notifications.NotificationType;
import com.massimo.webapp.notifications.Notifications;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public class ValidationManager {



    public static void showNotifications(BindingResult result, ModelMap modelMap){

        List<FieldError> errors = result.getFieldErrors();
        //Notifications
        Notifications notificationsFirst = new Notifications();
        Notifications notificationsLast = new Notifications();
        Notifications notificationsDate = new Notifications();

        int count = 0;

        for(FieldError fieldError: errors){
            if(fieldError.getField().equals("firstname"))
                modelMap.addAttribute("errorNotificationFirst",true);
                modelMap.addAttribute("errorFirst", "has-danger");

                //NOTIFICATIONS
                notificationsFirst.setType(NotificationType.ERROR.toString());
                notificationsFirst.setMessage("Please control the form! errors on Firstname");
                modelMap.addAttribute("notificationsFirst", notificationsFirst);


            if(fieldError.getField().equals("lastname"))
                modelMap.addAttribute("errorNotificationLast",true);
                modelMap.addAttribute("errorLast", "has-danger");

                //NOTIFICATIONS
                notificationsLast.setType(NotificationType.ERROR.toString());
                notificationsLast.setMessage("Please control the form! errors on Lastname");
                modelMap.addAttribute("notificationsLast", notificationsLast);

            if(fieldError.getField().equals("birthDate"))
                modelMap.addAttribute("errorNotificationDate",true);
                modelMap.addAttribute("errorBirthDate", "has-danger");

                //NOTIFICATIONS
                notificationsDate.setType(NotificationType.ERROR.toString());
                notificationsDate.setMessage("Please control the form! errors on Date");
                modelMap.addAttribute("notificationsDate", notificationsDate);


        }
        modelMap.addAttribute("edit", false);
    }

    public static void validateFile(ModelMap modelMap){
        Notifications notifications = new Notifications();
        notifications.setType(NotificationType.ERROR.toString());
        notifications.setMessage("File cannot be empty");
        modelMap.addAttribute("notifications", notifications);
        modelMap.addAttribute("errorFile", true);
        modelMap.addAttribute("error", "has-danger");
    }

    public static void controlOperationAndNotify(String operation, RedirectAttributes redirectAttributes){
        Notifications notifications = new Notifications();
        notifications.setType(NotificationType.SUCCESS.toString());
        if(operation.equalsIgnoreCase("insertUser")){
            notifications.setMessage("User added succesfully!");
        }
        else if(operation.equalsIgnoreCase("editUser")){
            notifications.setMessage("User edited succesfully!");
        }
        else if(operation.equalsIgnoreCase("deleteUser")){
            notifications.setMessage("User deleted succesfully!");
        }
        else if(operation.equalsIgnoreCase("uploadDocument")){
            notifications.setMessage("Document uploaded succesfully!");
        }
        else if(operation.equalsIgnoreCase("deleteDocument")){
            notifications.setMessage("Document deleted succesfully!");
        }
        else if(operation.equalsIgnoreCase("editDocument")){
            notifications.setMessage("Document edited succesfully!");

        }
        redirectAttributes.addFlashAttribute("successMessage", true);
        redirectAttributes.addFlashAttribute("notificationsSuccess", notifications);
    }

}
