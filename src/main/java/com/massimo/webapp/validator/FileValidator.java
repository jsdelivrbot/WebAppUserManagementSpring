package com.massimo.webapp.validator;

import com.massimo.webapp.model.FileBucket;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return FileBucket.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FileBucket fileBucket = (FileBucket) o;

        if(fileBucket.getFile()!=null ){
            if(fileBucket.getFile().getSize()==0){
                errors.rejectValue("file", "missing.file");
            }
        }



    }
}
