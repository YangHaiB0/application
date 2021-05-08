package com.yanghb.application.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author qinming
 * @date 2020-05-27
 * <p> 文件工具类 </p>
 */
@Slf4j
public class FileUtils {

    /**
     * 读取指定路径下的所有文件
     *
     * @param path 文件路径
     * @return 文件列表
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<>();
        File file = new File(path);
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    files.add(child.toString());
                }
            }
        }
        return files;
    }

    /**
     * 获取指定路径下所有文件名称
     *
     * @param path 路径
     * @return 文件名称
     */
    public static List<String> getFileNames(String path) {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(path);
        String[] children = dir.list();
        if (children != null) {
            fileNames.addAll(Arrays.asList(children));
        }
        return fileNames;
    }


    /**
     * 创建目录
     *
     * @param path 如 /home/ubuntu/.klei/DoNotStarveTogether/MyDediServer/Master
     * @return true 创建成功
     */
    public static boolean mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 读取指定位置文件
     *
     * @param filePath 文件路径
     */
    public static String readFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            //不存在不管它
            return null;
        }
        InputStream inputStream = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = (inputStream.read(buf))) != -1) {
            sb.append(new String(buf, 0, len));
        }
        inputStream.close();
        return sb.toString();
    }


    /**
     * 写入文件到指定位置
     *
     * @param filePath 文件路径
     * @param context  内容
     */
    public static void writeFile(String filePath, String context) throws Exception {
        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(context.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 读取文件最后N行
     * 根据换行符判断当前的行数，
     * 使用统计来判断当前读取第N行
     *
     * @param file    待文件
     * @param numRead 读取的行数
     */
    public static List<String> readLastNLine(File file, long numRead) {
        List<String> result = new ArrayList<>();
        //行数统计
        long count = 0;
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return result;
        }
        // 使用随机读取的读模式
        try (RandomAccessFile fileRead = new RandomAccessFile(file, "r")) {
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L) {
                return result;
            } else {
                //初始化游标
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n') {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        result.add(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    result.add(new String(fileRead.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //反转结果
        Collections.reverse(result);
        return result;
    }


    /**
     * 一行行的写文件
     */
    public static void writeLineFile(String path, List<String> data) throws Exception {
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        StringBuilder content = new StringBuilder();
        data.forEach(e -> content.append(e).append("\n"));
        writeFile(path, content.toString());
    }

    /**
     * 一行行的读取文件
     *
     * @param path 路径
     * @return 文件内容
     */
    public static List<String> readLineFile(String path) {
        List<String> result = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            return result;
        }
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String str;
            while ((str = br.readLine()) != null) {
                result.add(str);
            }
        } catch (Exception e) {
            log.error("一行行的读取文件失败：", e);
        }
        return result;
    }

    /**
     * 读取指定行数
     *
     * @param file      文件路径
     * @param maxNumber 最大行数
     * @return 内容
     */
    public static List<String> readAppointedLineNumber(File file, int maxNumber) {
        List<String> result = new ArrayList<>();
        if (!file.exists()) {
            return result;
        }
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String str;
            int count = 0;
            while ((str = br.readLine()) != null) {
                result.add(str);
                count++;
                if (count >= maxNumber) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
