package com.wiflish.luban.framework.common.util.string;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.common.exception.ServiceException;

import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.PHONE_VALIDATE_ERROR;

/**
 * 电话号码工具类.
 *
 * @author wiflish
 * @since 2024-08-19
 */
public class PhoneUtils {
    /**
     * @param phone phone
     * @return 不带前缀的phone
     */
    public static String trimPhone(String phone, String countryCode) {
        if (StrUtil.isEmpty(phone) || StrUtil.isEmpty(countryCode)) {
            return phone;
        }
        if (phone.indexOf("+") == 0) {
            phone = phone.substring(1);
        }
        if (phone.indexOf(countryCode) == 0) {
            return phone.substring(countryCode.length()).trim();
        }
        PhoneEnum externalPhonePrefix = PhoneEnum.getByExternalPhonePrefix(countryCode);
        if (phone.indexOf(externalPhonePrefix.getInternalPhonePrefix()) == 0) {
            return phone.substring(1).trim();
        }
        return phone.trim();
    }

    public static String addExternalPhonePrefix(String phone, String countryCode) {
        if (StrUtil.isEmpty(phone) || StrUtil.isEmpty(countryCode)) {
            return phone;
        }
        if (phone.indexOf("+") == 0) {
            phone = phone.substring(1);
        }
        if (phone.indexOf(countryCode) == 0) {
            return phone.replaceAll(" ", "");
        }
        PhoneEnum externalPhonePrefix = PhoneEnum.getByExternalPhonePrefix(countryCode);
        if (phone.indexOf(externalPhonePrefix.getInternalPhonePrefix()) == 0) {
            phone = phone.substring(externalPhonePrefix.getInternalPhonePrefix().length());
        }
        return externalPhonePrefix.getExternalPhonePrefix() + phone;
    }

    public static String addInternalPhonePrefix(String phone, String countryCode) {
        if (StrUtil.isEmpty(phone) || StrUtil.isEmpty(countryCode)) {
            return phone;
        }
        if (phone.indexOf("+") == 0) {
            phone = phone.substring(1);
        }
        if (phone.indexOf(countryCode) == 0) {
            phone = phone.substring(countryCode.length());
        }
        phone = phone.trim();
        PhoneEnum phonePrefix = PhoneEnum.getByExternalPhonePrefix(countryCode);
        if (phone.indexOf(phonePrefix.getInternalPhonePrefix()) == 0) {
            return phone;
        }
        return phonePrefix.getInternalPhonePrefix() + phone;
    }

    public static void validatePhone(String phone, String statCode) {
        // 如果是印尼手机号，强制0开头.
        PhoneEnum byStateCode = PhoneEnum.getByStateCode(statCode);
        if (byStateCode.equals(PhoneEnum.ID)) {
            boolean validated = phone.indexOf(byStateCode.getInternalPhonePrefix()) == 0;
            if (!validated) {
                throw new ServiceException(PHONE_VALIDATE_ERROR);
            }
        }
    }
}
