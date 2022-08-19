package com.chaunmi.fastwebview.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class StorageLoader {

    public static InputStream getStorageFileStream(File dir, String url) {
        File file = getResByUrl(dir, url);
        if (file != null) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return inputStream;
        }
        return null;
    }


    public static File getResByUrl(File file, String url) {
        String uPath = getUrlPath(url);
        return getResByUrlPath(file, uPath);
    }


    private static File getResByUrlPath(File file, String urlPath) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if(files == null) {
                return null;
            }
            String pathName = "";
            for (File child : files) {
                if (child.isDirectory()) {
                    pathName = child.getName();
                    if(!urlPath.contains(pathName)) {
                        continue;
                    }
                    File targetFile = getResByUrlPath(child, urlPath);
                    if (targetFile != null) {
                        return targetFile;
                    }
                } else {
                    String fileName = child.getName();
                    if (urlPath.endsWith(fileName)) {
                        return child;
                    }
                }
            }
        } else {
            String fileName = file.getName();
            if (urlPath.endsWith(fileName)) {
                return file;
            }
        }
        return null;
    }
    /**
     * 获取url的path信息
     *
     * @param url
     * @return
     */

    public static String getUrlPath(String url) {
        String uPath = "";
        try {
            URL u = new URL(url);
            uPath = u.getPath();
            if (uPath.startsWith("/")) {
                if (uPath.length() == 1) {
                    return uPath;
                }
                uPath = uPath.substring(1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return uPath;
    }
}
