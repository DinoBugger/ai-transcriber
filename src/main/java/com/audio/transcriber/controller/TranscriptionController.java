package com.audio.transcriber.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;

import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/transcribe")
public class TranscriptionController {
  private final OpenAiAudioTranscriptionOptions options;
  private final OpenAiAudioTranscriptionModel model;

  MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
  ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();


  @Autowired
  public TranscriptionController( @Value("${spring.ai.openai.api-key}") String apiKey, @Value("${spring.ai.openai.audio.transcription.options.model}") String modelName) {
    ApiKey apiKeyObj = () -> apiKey;
    OpenAiAudioApi audioApi = new OpenAiAudioApi(
        "https://api.openai.com",
        apiKeyObj,
        headers,
        RestClient.builder(),
        WebClient.builder(),
        errorHandler
    );

    model= new OpenAiAudioTranscriptionModel(audioApi);
    options = OpenAiAudioTranscriptionOptions.builder()
        .responseFormat(TranscriptResponseFormat.TEXT)
        .temperature(0f)
        .model(modelName)
        .build();
  }


  @GetMapping("/local-test")
  public  String localTest() {
    Resource resource =  new FileSystemResource("resources/audio-shortest.mp3");
    AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(resource, options);
    AudioTranscriptionResponse response = model.call(prompt);
    return response.getResult().getOutput();
  }
}
