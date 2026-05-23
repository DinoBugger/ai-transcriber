package com.audio.transcriber.util;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.util.StringUtils;
import  org.springframework.web.multipart.MultipartFile;

public class FileValidator {

  // with OpenAI whisper model, the max file size is 25MB, but we set it to 20MB to be safe and allow for some overhead
  private static final long MAX_AUDIO_SIZE = 20 * 1024 * 1024;

  private static final List<String> ALLOWED_AUDIO_TYPES = Arrays.asList(
      "audio/mpeg",  // .mp3
      "audio/ogg",   // .ogg
      "audio/wav",   // .wav
      "audio/x-wav", // .wav
      "audio/aac",   // .aac
      "audio/m4a"    // .m4a
  );
  public static String validateFile(MultipartFile file) {
    if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
      return "No file uploaded.";
    }

    if(file.getSize() > MAX_AUDIO_SIZE) {
      return "File size exceeds the maximum allowed limit of 10MB.";
    }

    String contentType = file.getContentType();
    if( !ALLOWED_AUDIO_TYPES.contains(contentType)) {
     return "Unsupported file type. Allowed types are: " + String.join(", ", ALLOWED_AUDIO_TYPES);
    }

    String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    if (originalFilename.contains("..")) {
      return "Invalid file name with moving directory.";
    }

    return null;

  }

}
