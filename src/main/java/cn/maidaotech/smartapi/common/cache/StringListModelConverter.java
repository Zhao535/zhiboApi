package cn.maidaotech.smartapi.common.cache;

import cn.maidaotech.smartapi.common.utils.ByteUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.KvCacheException;
import com.sunnysuperman.kvcache.converter.ModelConverter;

import java.util.Collections;
import java.util.List;

public class StringListModelConverter implements ModelConverter<List<String>> {
    private static final StringListModelConverter INSTANCE = new StringListModelConverter();
    private static final byte[] EMPTY_BYTES = new byte[0];

    public static StringListModelConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> deserialize(byte[] bytes) throws KvCacheException {
        if (bytes == null || bytes.length == 0) {
            return Collections.emptyList();
        }
        return StringUtils.split(ByteUtils.toString(bytes), ",");
    }

    @Override
    public byte[] serialize(List<String> list) throws KvCacheException {
        if (list == null || list.isEmpty()) {
            return EMPTY_BYTES;
        }
        return ByteUtils.fromString(StringUtils.join(list, ","));
    }

}
