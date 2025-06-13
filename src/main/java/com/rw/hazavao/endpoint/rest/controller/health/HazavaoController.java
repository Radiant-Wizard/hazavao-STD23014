package com.rw.hazavao.endpoint.rest.controller.health;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HazavaoController {

  public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
  public static final ResponseEntity<String> KO =
      new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);

  @GetMapping("/hazavao")
  public String hazavao(@RequestParam String teny) {
    String prompt = "give me the meaning of this word : " + teny;
    OpenAIClient client = OpenAIOkHttpClient
        .builder()
        .apiKey(System.getenv("OPENAI_API_KEY"))
        .build();

    ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
        .addUserMessage(prompt)
        .model(ChatModel.GPT_3_5_TURBO)
        .build();

    ChatCompletion chatCompletion = client.chat().completions().create(params);
    return chatCompletion.choices().getFirst().message().content().toString();
  }
}
