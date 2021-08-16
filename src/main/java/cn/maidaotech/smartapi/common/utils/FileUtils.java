package cn.maidaotech.smartapi.common.utils;

import com.sunnysuperman.commons.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils extends FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteQuietly(File file) {
        if (file == null) {
            return;
        }
        try {
            FileUtil.delete(file);
        } catch (Exception e) {
            LOG.error(null, e);
        }
    }

    public static long getRemoteFileSize(String url) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLengthLong();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
