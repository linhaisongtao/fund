package com.example.daisongsong.chartapp.book.data;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by daisongsong on 2016/11/30.
 */

public class FileUtils {
    private static File sRoot;

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            sRoot = new File(path, "fund");
            sRoot.mkdirs();
        }
    }

    public static void write(String fileName, String content) {
        File file = open(fileName);
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String read(String fileName) {
        String content = null;
        File file = open(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int size = 0;
        try {
            InputStream in = new FileInputStream(file);
            size = in.read(buffer);
            while (size > 0) {
                baos.write(buffer, 0, size);
                size = in.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static File open(String name) {
        File file = new File(sRoot, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
