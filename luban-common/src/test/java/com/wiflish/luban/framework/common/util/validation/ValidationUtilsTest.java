package com.wiflish.luban.framework.common.util.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author wiflish
 * @since 2024-08-19
 */
class ValidationUtilsTest {

    @Test
    void isMobile() {
        boolean mobile = ValidationUtils.isMobile("18311370077", "CN");
        assertTrue(mobile);

        mobile = ValidationUtils.isMobile("87870977700", "ID");
        assertTrue(mobile);
        mobile = ValidationUtils.isMobile("087870977700", "ID");
        assertTrue(mobile);
        mobile = ValidationUtils.isMobile("62 87870977700", "ID");
        assertTrue(mobile);
        mobile = ValidationUtils.isMobile("+6287870977700", "ID");
        assertTrue(mobile);
        mobile = ValidationUtils.isMobile("187870977700", "ID");
        assertFalse(mobile);
        mobile = ValidationUtils.isMobile("8118250077", "ID");
        assertTrue(mobile);
        mobile = ValidationUtils.isMobile("8311370077", "ID");
        assertTrue(mobile);


        mobile = ValidationUtils.isMobile("8311370077", "CN");
        assertTrue(mobile);
    }
}