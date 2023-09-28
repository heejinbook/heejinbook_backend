package com.book.heejinbook.utils;


import com.book.heejinbook.enums.FilePath;

public class FilePathUtils {
    public static String convertImageUrlToFilePath(String imageUrl){
        return imageUrl.substring(
                imageUrl.indexOf(
                        FilePath.SEPARATE_POINT.getPath()) + 5,
                        imageUrl.length()
        );
    }
}
