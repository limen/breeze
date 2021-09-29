package com.limengxiang.breeze.validation;

import com.limengxiang.breeze.http.request.RequestEntity;
import com.limengxiang.breeze.validation.annotation.Length;
import com.limengxiang.breeze.validation.annotation.Range;
import com.limengxiang.breeze.validation.annotation.Temporal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class ValidationTest {

    static class TestEntity extends RequestEntity {

        @Length(min = 10, max = 20, msg = "string length out of range")
        String string;
        @Range(min = 10, max = 20, msg = "integer value out of range")
        Integer integer;
        @Range(min = 10.0, max = 20.0, msg = "float value out of range")
        Float aFloat;
        @Temporal(msg = "should be temporal")
        String date;

    }

    @Test
    public void testValidation() {
        TestEntity testEntity = new TestEntity();
        testEntity.string = "1234567890";
        testEntity.integer = 100;
        testEntity.aFloat = 11.1F;
        testEntity.date = "2020-11-11 10:00:00";
        Map<String, Object> errors = testEntity.validate().getErrors();
        Assertions.assertTrue(errors.containsKey("integer"));
        Assertions.assertEquals(errors.size(), 1);

        testEntity.date = "20201101 10:00:00";
        errors = testEntity.validate().getErrors();
        Assertions.assertTrue(errors.containsKey("integer"));
        Assertions.assertTrue(errors.containsKey("date"));
        Assertions.assertEquals(errors.size(), 2);

        testEntity.aFloat = 21.0F;
        testEntity.integer = 11;
        testEntity.date = "2021-11-10 00:00:00";
        errors = testEntity.validate().getErrors();
        Assertions.assertTrue(errors.containsKey("aFloat"));
        Assertions.assertEquals(errors.size(), 1);

        testEntity.date = "2000-02-29 23:59:59";
        errors = testEntity.validate().getErrors();
        Assertions.assertTrue(errors.containsKey("aFloat"));
        Assertions.assertEquals(errors.size(), 1);

        testEntity.date = "2000-03-31 23:59:59";
        errors = testEntity.validate().getErrors();
        Assertions.assertTrue(errors.containsKey("aFloat"));
        Assertions.assertEquals(errors.size(), 1);

        testEntity.aFloat = 11.0F;
        errors = testEntity.validate().getErrors();
        Assertions.assertNull(errors);
    }

}
