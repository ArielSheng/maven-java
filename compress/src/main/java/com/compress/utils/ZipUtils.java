package com.compress.utils;

import java.util.regex.Pattern;
import java.util.zip.DeflaterOutputStream;
import com.github.luben.zstd.*;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.XZOutputStream;
import org.xerial.snappy.Snappy;
import java.io.*;

public class ZipUtils {
    private  static final int BUFFER_SIZE = 8 *1024;  //8KB
    public void zlibCompress(String inputFile) {
        String[] f = inputFile.split(Pattern.quote("."));
        String outputFile = f[0] + "_" + f[1] + "." + "zlib";
        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile);
             DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                deflaterOutputStream.write(buffer, 0, len);
            }
            deflaterOutputStream.finish();
            System.out.println("Zlib压缩算法压缩文件成功：" + outputFile);
        } catch (IOException e) {
            System.out.println("压缩出错：" + e.getMessage());
        }
    }

    public void zstCompress(String inputFile) {
        String[] f = inputFile.split("\\.");
        String outputFile = f[0] + "_" + f[1] + "." + "zst";
        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byte[] compressArray = Zstd.compress(buffer);
                outputStream.write(compressArray);
            }
            System.out.println("Zstd压缩算法压缩文件成功：" + outputFile);
        } catch (IOException e) {
            System.out.println("压缩出错：" + e.getMessage());
        }
    }

    public void snapCompress(String inputFile) {
        String[] f = inputFile.split(Pattern.quote("."));
        String outputFile = f[0] + "_" + f[1] + "." + "snap";
        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byte[] compressArray = Snappy.compress(buffer);
                outputStream.write(compressArray);
            }
            System.out.println("Snappy压缩算法压缩文件成功：" + outputFile);
        } catch (IOException e) {
            System.out.println("压缩出错：" + e.getMessage());
        }
    }

    public void xzCompress(String inputFile) {
        String[] f = inputFile.split(Pattern.quote("."));
        String outputFile = f[0] + "_" + f[1] + "." + "xz";
        LZMA2Options options = new LZMA2Options();
        try {
            options.setPreset(7);
        } catch (UnsupportedOptionsException e) {
            throw new RuntimeException(e);
        }
        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile);
             XZOutputStream xzOutputStream = new XZOutputStream(outputStream, options)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                xzOutputStream.write(buffer, 0, len);
            }
            xzOutputStream.finish();
            System.out.println("LZMA压缩算法压缩文件成功：" + outputFile);
        } catch (IOException e) {
          System.out.println("压缩出错：" + e.getMessage());
        }
    }
}