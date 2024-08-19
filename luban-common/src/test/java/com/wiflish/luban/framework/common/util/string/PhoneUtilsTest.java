package com.wiflish.luban.framework.common.util.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wiflish
 * @since 2024-08-19
 */
class PhoneUtilsTest {

    @Test
    void trimPhone() {
        assertEquals("81234567890", PhoneUtils.trimPhone("+62 81234567890", "62"));
        assertEquals("81234567890", PhoneUtils.trimPhone("+081234567890", "62"));
        assertEquals("81234567890", PhoneUtils.trimPhone("081234567890", "62"));
        assertEquals("81234567890", PhoneUtils.trimPhone("81234567890", "62"));
    }

    @Test
    void addExternalPhonePrefix() {
        assertEquals("6281234567890", PhoneUtils.addExternalPhonePrefix("+62 81234567890", "62"));
        assertEquals("6281234567890", PhoneUtils.addExternalPhonePrefix("+081234567890", "62"));
        assertEquals("6281234567890", PhoneUtils.addExternalPhonePrefix("081234567890", "62"));
        assertEquals("6281234567890", PhoneUtils.addExternalPhonePrefix("81234567890", "62"));
    }
}