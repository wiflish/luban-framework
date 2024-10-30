package com.wiflish.luban.framework.common.api.i18n;

import com.wiflish.luban.framework.common.api.i18n.dto.I18nMessageDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wiflish
 * @since 2024-04-18
 */
public interface I18nApi {
    List<I18nMessageDTO> getList(LocalDateTime latestUpdateTime);
}
