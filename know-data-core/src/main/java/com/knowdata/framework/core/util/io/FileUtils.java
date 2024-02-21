package com.knowdata.framework.core.util.io;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.URLUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2022/11/10 14:59
 * @description
 * @refrenece-doc
 *  [1] Java实现指定目录下的文件查找 - CSDN - https://blog.csdn.net/ws15168689087/article/details/110233996
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final String CLASS_EXT = ".class";
    public static final String JAR_FILE_EXT = ".jar";
    public static final String JAR_PATH_EXT = ".jar!";
    public static final String PATH_FILE_PRE = "file:";
    public static final String FILE_SEPARATOR;
    public static final String PATH_SEPARATOR;
    private static final Pattern PATTERN_PATH_ABSOLUTE;

    static {
        FILE_SEPARATOR = File.separator;
        PATH_SEPARATOR = File.pathSeparator;
        PATTERN_PATH_ABSOLUTE = Pattern.compile("^[a-zA-Z]:([/\\\\].*)?");
    }

    /**
     * 文件是否被占用
     *
     * @param fileName
     * @return true: 正在使用 ; false: 为被占用
     * @reference-doc
     *  [1] 判断文件是否被占用的两种方法 - 51CTO博客 - https://blog.51cto.com/u_12269676/4920592
     *  [2] java 中如何判断当前的文件是否正在被别人使用中 - 百度知道 - https://zhidao.baidu.com/question/489412083704963172.html
     */
    public static boolean isFileInUse(String fileName) throws FileNotFoundException {
        boolean inUse = true;
        FileInputStream fs = null;

        fs = new FileInputStream(fileName);
        inUse = false;
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logger.debug("#isFileInUse# isFileInUse: {}", inUse);
        return inUse;
    }

    /**
     * 输出到文件
     *
     * @param data
     * @param fileFullPath 含完整路径及文件名称
     *                     eg: "C:\Users\xxxxxx\Desktop\XXXXXXXXXX-1667798146105-20221107011546.bin"
     * @throws IOException
     */
    public static void writeToFile(String data, String fileFullPath) throws IOException {
        // 创建文件对象
        File fileText = new File(fileFullPath);
        // 向文件写入对象写入信息
        try (FileWriter fileWriter = new FileWriter(fileText);) {
            // 写文件
            fileWriter.write(data);
        } catch (Exception exception) {
            logger.error("happens a exception when write data to file. exception :", exception);
        }
    }

    public static void getFileLock(File file) throws FileNotFoundException, IOException {
        // eg: new RandomAccessFile(new File("c:\\test.txt"), "rw");
        RandomAccessFile raf = new RandomAccessFile(new File("c:\\test.txt"), "rw");
        FileChannel fc = raf.getChannel();
        FileLock fl = fc.tryLock();
        if (fl.isValid()) {
            logger.debug("success to get the file lock for file({})!", file.getAbsolutePath());
        }
    }

    /**
     * 输出到文件
     *
     * @param data
     * @param fileFullPath 含完整路径及文件名称
     *                     eg: "C:\Users\xxxxxx\Desktop\XXXXXXXXXX-1667798146105-20221107011546.bin"
     * @throws IOException
     */
    public static void writeToFile(byte[] data, String fileFullPath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileFullPath);) {
            out.write(data);
        } catch (Exception exception) {
            logger.error("happens a exception when write data to file. exception :", exception);
        }
    }

    public static byte[] read(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

    public static String readString(String path, Charset charset) throws IORuntimeException {
        return readString(file(path), charset);
    }

    public static String readString(File file, Charset charset) throws IORuntimeException {
        return create(file, charset).readString();
    }

    public static cn.hutool.core.io.file.FileReader  create(File file, Charset charset) {
        return new cn.hutool.core.io.file.FileReader (file, charset);
    }

    public static File file(String path) {
        return null == path ? null : new File(getAbsolutePath(path));
    }

    public static String getAbsolutePath(String path) {
        return getAbsolutePath(path, (Class) null);
    }

    public static String getAbsolutePath(String path, Class<?> baseClass) {
        String normalPath;
        if (path == null) {
            normalPath = "";
        } else {
            normalPath = normalize(path);
            if (isAbsolutePath(normalPath)) {
                return normalPath;
            }
        }

        URL url = ResourceUtil.getResource(normalPath, baseClass);
        if (null != url) {
            return normalize(URLUtil.getDecodedPath(url));
        } else {
            String classPath = ClassUtil.getClassPath();
            return null == classPath ? path : normalize(classPath.concat((String) Objects.requireNonNull(path)));
        }
    }

    public static boolean isAbsolutePath(String path) {
        if (CharSequenceUtil.isEmpty(path)) {
            return false;
        } else {
            return '/' == path.charAt(0) || ReUtil.isMatch(PATTERN_PATH_ABSOLUTE, path);
        }
    }

    public static String getUserHomePath() {
        return System.getProperty("user.home");
    }

    public static String normalize(String path) {
        if (path == null) {
            return null;
        } else {
            String pathToUse = getPathToUse(path);

            String prefix = "";
            int prefixIndex = pathToUse.indexOf(":");
            if (prefixIndex > -1) {
                prefix = pathToUse.substring(0, prefixIndex + 1);
                if (CharSequenceUtil.startWith(prefix, '/')) {
                    prefix = prefix.substring(1);
                }

                if (!prefix.contains("/")) {
                    pathToUse = pathToUse.substring(prefixIndex + 1);
                } else {
                    prefix = "";
                }
            }

            if (pathToUse.startsWith("/")) {
                prefix = prefix + "/";
                pathToUse = pathToUse.substring(1);
            }

            List<String> pathList = CharSequenceUtil.split(pathToUse, '/');
            List<String> pathElements = getPathElements(prefix, pathList);
            return prefix + CollUtil.join(pathElements, "/");
        }
    }

    private static String getPathToUse(String path) {
        String pathToUse = CharSequenceUtil.removePrefixIgnoreCase(path, "classpath:");
        pathToUse = CharSequenceUtil.removePrefixIgnoreCase(pathToUse, PATH_FILE_PRE);
        if (CharSequenceUtil.startWith(pathToUse, '~')) {
            pathToUse = getUserHomePath() + pathToUse.substring(1);
        }

        pathToUse = pathToUse.replaceAll("[/\\\\]+", "/");
        pathToUse = CharSequenceUtil.trimStart(pathToUse);
        if (path.startsWith("\\\\")) {
            pathToUse = "\\" + pathToUse;
        }
        return pathToUse;
    }

    private static List<String> getPathElements(String prefix, List<String> pathList) {
        List<String> pathElements = new LinkedList();
        int tops = 0;

        for (int i = pathList.size() - 1; i >= 0; --i) {
            String element = (String) pathList.get(i);
            if (!".".equals(element)) {
                if ("..".equals(element)) {
                    ++tops;
                } else if (tops > 0) {
                    --tops;
                } else {
                    pathElements.add(0, element);
                }
            }
        }

        if (tops > 0 && CharSequenceUtil.isEmpty(prefix)) {
            while (tops-- > 0) {
                pathElements.add(0, "..");
            }
        }
        return pathElements;
    }
}
