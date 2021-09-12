package com.limengxiang.breeze.httpio.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limengxiang.breeze.exception.InvalidParamException;
import com.limengxiang.breeze.validation.Validation;
import com.limengxiang.breeze.validation.annotation.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class AbstractEntity {

    @JsonIgnore
    protected Map<String, Object> errors;

    public AbstractEntity validate() {
        clearErrors();
        internalValidate();
        return this;
    }

    public void throwOnError() {
        if (getErrors() != null) {
            throw new InvalidParamException(getErrors());
        }
    }

    protected void internalValidate() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Annotation[] annos = field.getDeclaredAnnotations();
                for (Annotation anno : annos) {
                    // anno is a proxy, annotationType() is the real annotation class
                    Type identifier = anno.annotationType().getAnnotation(Type.class);
                    if (identifier == null) {
                        continue;
                    }
                    Object value = field.get(this);
                    String err = Validation.applyForMsg(identifier.type(), anno, value);
                    if (err != null) {
                        addError(field.getName(), err);
                    }
                }
            } catch (IllegalAccessException e) {
                //
            }
        }

    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void clearErrors() {
        if (errors != null) {
            errors = null;
        }
    }

    public void addError(String field, String err) {
        if (errors == null) {
            errors = new HashMap<>();
        }
        errors.put(field, err);
    }

}
