package com.audio.transcriber.util;
import java.util.Objects;
import org.springframework.util.StringUtils;
import  org.springframework.web.multipart.MultipartFile;

public class FileValidator {

  private static final long MAX_AUDIO_SIZE = 10 * 1024 * 1024;

  private static final String ALLOWED_AUDIO_TYPE = "audio/mpeg";
  public static String validateFile(MultipartFile file) {
    if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
      return "No file uploaded.";
    }

    if(file.getSize() > MAX_AUDIO_SIZE) {
      return "File size exceeds the maximum allowed limit of 10MB.";
    }

    String contentType = file.getContentType();
    if( !ALLOWED_AUDIO_TYPE.equals(contentType)) {
      return "Invalid file type. Only MP3 audio files are allowed.";
    }

    String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    if (originalFilename.contains("..")) {
      return "Invalid file name with moving directory.";
    }

    return null;

  }

}
