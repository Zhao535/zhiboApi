package cn.maidaotech.smartapi.common.service;

import cn.maidaotech.smartapi.common.utils.FileUtils;
import cn.maidaotech.smartapi.common.utils.SimpleHttpClient;
import cn.maidaotech.smartapi.common.utils.UUIDGeneratorFactory;
import com.sunnysuperman.commons.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Service
public class LocalFileHelper {
    private final UUIDGeneratorFactory.UUIDGenerator TMP_FILE_ID_CREATOR = UUIDGeneratorFactory.create();
    private final String TMP_DIR = "/tmp/" + new File(System.getProperty("user.dir")).getName();

    private File makeTmpFile(boolean isDir, String extension) throws IOException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        StringBuilder buf = new StringBuilder(TMP_DIR).append('/').append(year).append('/').append(month).append('/')
                .append(day).append('/').append(TMP_FILE_ID_CREATOR.generate());
        if (extension != null) {
            buf.append('.').append(extension);
        }
        File file = new File(buf.toString());
        boolean ok;
        if (isDir) {
            ok = file.mkdirs();
        } else {
            ok = FileUtil.ensureFile(file);
        }
        if (!ok) {
            throw new IOException("Failed to create file/dir");
        }
        return file;
    }

    public File createTmpFile(String extension) throws IOException {
        return makeTmpFile(false, extension);
    }

    public File createTmpDir() throws IOException {
        return makeTmpFile(true, null);
    }

    public File download(String url, long maxSize) throws IOException {
        String extension;
        int paramIndex = url.indexOf('?');
        if (paramIndex > 0) {
            extension = FileUtil.getFileExt(url.substring(0, paramIndex));
        } else {
            extension = FileUtil.getFileExt(url);
        }
        File file = createTmpFile(extension);
        boolean ok = false;
        try {
            SimpleHttpClient client = new SimpleHttpClient().setConnectTimeout(15).setReadTimeout(30);
            SimpleHttpClient.DownloadOptions options = new SimpleHttpClient.DownloadOptions().setMaxSize(maxSize);
            boolean downloaded = client.download(url, new FileOutputStream(file), options);
            if (!downloaded) {
                return null;
            }
            ok = true;
            return file;
        } finally {
            if (!ok) {
                FileUtils.deleteQuietly(file);
            }
        }
    }

    public File download(String url) throws IOException {
        return download(url, -1);
    }

    public File copyFromStream(InputStream in, String extension) throws IOException {
        File file = createTmpFile(extension);
        boolean ok = false;
        try {
            FileUtil.copy(in, new FileOutputStream(file));
            ok = true;
            return file;
        } finally {
            if (!ok) {
                FileUtils.deleteQuietly(file);
            }
        }
    }
}
