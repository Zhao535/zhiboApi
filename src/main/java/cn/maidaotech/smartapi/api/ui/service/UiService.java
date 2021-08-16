package cn.maidaotech.smartapi.api.ui.service;

import cn.maidaotech.smartapi.api.ui.entity.UIQo;
import cn.maidaotech.smartapi.api.ui.model.UI;
import org.springframework.data.domain.Page;

public interface UiService {

    void save(UI ui) throws Exception;

    UI item(Integer id) throws Exception;

    UI itemForUsr(Byte type) throws Exception;

    Page<UI> items (UIQo qo) throws Exception;

    void uiDefault(Integer id) throws Exception;

}
