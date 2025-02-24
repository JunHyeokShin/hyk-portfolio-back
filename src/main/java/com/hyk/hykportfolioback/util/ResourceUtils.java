package com.hyk.hykportfolioback.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceUtils {

  public static boolean isThumbnailFile(File file) {
    if (!file.isFile()) return false;

    String resourceFilenameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
    return resourceFilenameWithoutExtension.equals("thumbnail");
  }

  public static String createProjectThumbnailSavePath(String id, MultipartFile file) {
    String fileExtension = getFileExtension(file);
    return System.getProperty("user.dir") + "/resources/project/" + id + "/thumbnail" + fileExtension;
  }

  public static String createPostThumbnailSavePath(Integer id, MultipartFile file) {
    String fileExtension = getFileExtension(file);
    return System.getProperty("user.dir") + "/resources/post/" + id + "/thumbnail" + fileExtension;
  }

  public static String createProjectResourceSavePath(String id, MultipartFile file) {
    return System.getProperty("user.dir") + "/resources/project/" + id + "/" + file.getOriginalFilename();
  }

  public static String createPostResourceSavePath(Integer id, MultipartFile file) {
    return System.getProperty("user.dir") + "/resources/post/" + id + "/" + file.getOriginalFilename();
  }

  public static String getFileExtension(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    return originalFilename.substring(originalFilename.lastIndexOf("."));
  }

  public static void saveFile(MultipartFile file, Path savePath) throws IOException {
    Files.createDirectories(savePath.getParent());
    file.transferTo(savePath.toFile());
  }

  public static void deleteProjectDirectory(String id) {
    try {
      Path path = Paths.get(System.getProperty("user.dir") + "/resources/project/" + id);
      FileUtils.deleteDirectory(path.toFile());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void deletePostDirectory(String id) {
    try {
      Path path = Paths.get(System.getProperty("user.dir") + "/resources/post/" + id);
      FileUtils.deleteDirectory(path.toFile());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

}
