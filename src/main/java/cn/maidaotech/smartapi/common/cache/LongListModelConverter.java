package cn.maidaotech.smartapi.common.cache;

import cn.maidaotech.smartapi.common.utils.ByteUtils;
import cn.maidaotech.smartapi.common.utils.StringUtils;
import com.sunnysuperman.kvcache.KvCacheException;
import com.sunnysuperman.kvcache.converter.ModelConverter;

import java.util.Collections;
import java.util.List;

public class LongListModelConverter implements ModelConverter<List<Long>> {
    private static final LongListModelConverter INSTANCE = new LongListModelConverter();
    private static final byte[] EMPTY_BYTES = new byte[0];

    public static LongListModelConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Long> deserialize(byte[] bytes) throws KvCacheException {
        if (bytes == null || bytes.length == 0) {
            return Collections.emptyList();
        }
        return StringUtils.splitAsLongList(ByteUtils.toString(bytes), ",");
    }

    @Override
    public byte[] serialize(List<Long> list) throws KvCacheException {
        if (list == null || list.isEmpty()) {
            return EMPTY_BYTES;
        }
        return ByteUtils.fromString(StringUtils.join(list, ","));
    }

}
