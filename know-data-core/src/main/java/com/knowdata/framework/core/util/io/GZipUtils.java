package com.knowdata.framework.core.util.io;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  01:26:13
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP工具
 * @description
 *  GZIP是用于UNIX系统的文件压缩，在 Linux 中经常会使用到 *.gz 的文件，就是 GZIP 格式，GZIP 压缩的支持类保存在 java.util.zip 包中
 *  常用的类有 GZIPOutputStream(GZIP压缩输出流)、GZIPInputStream(GZIP压缩输入流)
 * @reference-doc
 *  [1] java gzipoutputstream_Java GZIPOutputStream流压缩文件的操作 - CSDN - https://blog.csdn.net/weixin_36162235/article/details/114775060
 */
public class GZipUtils {
    private static final Logger logger = LoggerFactory.getLogger(GZipUtils.class);

    private static final int BYTE_LEN = 512;
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    private static String ZIP_STREAM_HEADER = "__zip_";
    private static byte[] zipStreamHeader = ZIP_STREAM_HEADER.getBytes();


    /**
     * 解压
     *
     * @param bytes 待解压byte数组
     * @return
     * @throws IOException
     */
    public static byte[] uncompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        @Cleanup
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        @Cleanup
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        @Cleanup
        GZIPInputStream gzip = new GZIPInputStream(in, BYTE_LEN);
        try {
            byte[] buffer = new byte[BYTE_LEN];
            int n;
            while ((n = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            logger.error(String.format("GzipUtil uncompress error"));
            return null;
        }
        return out.toByteArray();
    }


    /**
     * 判断一个字节数组是否是压缩字节数组
     *
     * @param input
     * @return
     */
    public static boolean hasZip(byte[] input) {
        if(input.length < zipStreamHeader.length) {
            return false;
        }
        String header = null;
        try {
            header = new String(input, 0, zipStreamHeader.length, GZIP_ENCODE_UTF_8);
            return header.equals(ZIP_STREAM_HEADER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解压返回字符串
     *
     * @param bytes    待解压byte数组
     * @param encoding 编码
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if(bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[BYTE_LEN];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩
     *
     * @param str      待压缩字符串
     * @param encoding 编码
     * @return
     * @throws IOException
     */
    public static byte[] compress(String str, String encoding) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes(encoding));
        gzip.close();
        return out.toByteArray();
    }
}
