package cn.maidaotech.smartapi.common.reposiotry.support;

import com.sunnysuperman.commons.util.FormatUtil;

public class DataQueryObjectPage extends DataQueryObjectSort {

    public static int PAGESIZE_MIN = 10;
    public static int PAGESIZE_MAX = 50;

    protected Integer pageNumber = 1;
    protected Integer pageSize = PAGESIZE_MIN;

    public Integer getPageNumber() {
        int asInt = FormatUtil.parseIntValue(pageNumber, 0);
        return asInt <= 0 ? 0 : asInt - 1;
    }

    public void setPageNumber(Integer page) {
        this.pageNumber = page;
    }

    public Integer getPageSize() {
        int defaultValue = PAGESIZE_MIN;
        int maxValue = PAGESIZE_MAX;
        if (pageSize == null || pageSize <= 0) {
            return defaultValue;
        }
        if (pageSize > maxValue) {
            return Math.min(maxValue, pageSize);
        }
        return pageSize;
    }

    public void setPageSize(Integer size) {
        this.pageSize = size;
    }
}
