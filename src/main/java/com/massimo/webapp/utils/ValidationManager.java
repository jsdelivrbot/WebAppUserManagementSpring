package com.massimo.webapp.utils;

import com.massimo.webapp.notifications.NotificationType;
import com.massimo.webapp.notifications.Notifications;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public class ValidationManager {



    public static void showNotifications(BindingResult result, ModelMap modelMap){

        List<FieldError> errors = result.getFieldErrors();
        //Notifications
        Notifications notificationsFirst = new Notifications();
        Notifications notificationsLast = new Notifications();
        Notifications notificationsDate = new Notifications();
        List<Notifications> notificationsList = new ArrayList<>();

        boolean addedFirst = false;
        boolean addedLast = false;
        boolean addedDate = false;

        for(FieldError fieldError: errors){
            if(fieldError.getField().equals("firstname"))
                if(addedFirst==false) {

                    //modelMap.addAttribute("errorNotification", true);
                    modelMap.addAttribute("errorFirst", "has-danger");

                    //NOTIFICATIONS
                    notificationsFirst.setType(NotificationType.DANGER.getStatus().toString());
                    notificationsFirst.setMessage("Please control the form! errors on Firstname");


                    //AGGIUNGO A LISTA
                    notificationsList.add(notificationsFirst);
                    modelMap.addAttribute("notificationsList",notificationsList);
                    addedFirst=true;
                }

            if(fieldError.getField().equals("lastname"))
                if(addedLast==false) {

                    modelMap.addAttribute("errorLast", "has-danger");

                    //NOTIFICATIONS
                    notificationsLast.setType(NotificationType.DANGER.getStatus().toString());
                    notificationsLast.setMessage("Please control the form! errors on Lastname");

                    //AGGIUNGO A LISTA
                    notificationsList.add(notificationsLast);
                    modelMap.addAttribute("notificationsList",notificationsList);
                    addedLast=true;
                }

            if(fieldError.getField().equals("birthDate"))
                if(addedDate==false) {

                    modelMap.addAttribute("errorBirthDate", "has-danger");

                    //NOTIFICATIONS
                    notificationsDate.setType(NotificationType.DANGER.getStatus().toString());
                    notificationsDate.setMessage("Please control the form! errors on Date");

                    //AGGIUNGO A LISTA
                    notificationsList.add(notificationsDate);
                    modelMap.addAttribute("notificationsList",notificationsList);
                    addedDate=true;
                }
            modelMap.addAttribute("errorNotification", true);
        }
        modelMap.addAttribute("edit", false);
    }

    public static void validateFile(ModelMap modelMap){
        Notifications notifications = new Notifications();
        notifications.setType(NotificationType.DANGER.getStatus().toString());
        notifications.setMessage("File cannot be empty");
        modelMap.addAttribute("notifications", notifications);
        modelMap.addAttribute("errorFile", true);
        modelMap.addAttribute("error", "has-danger");
    }

    public static void controlOperationAndNotify(String operation, RedirectAttributes redirectAttributes){
        Notifications notifications = new Notifications();
        notifications.setType(NotificationType.SUCCESS.getStatus().toString());

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

    public static void validateSearch(ModelMap modelMap, int resultSize) {
        Notifications notifications = new Notifications();
        if(resultSize>0){
            notifications.setMessage("Search returned "+ resultSize + " results");
        }else{
            notifications.setType(NotificationType.WARNING.getStatus().toString());
            notifications.setMessage("We are sorry, no results found!");
        }

        modelMap.addAttribute("notifications", notifications);
    }
}
