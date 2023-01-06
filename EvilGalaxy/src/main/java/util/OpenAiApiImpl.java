package util;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class OpenAiApiImpl {
	
    public static String createCompletion(String msg) {
//        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService("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");

        System.out.println("\nCreating completion...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("davinci")
                .prompt(msg)
                .echo(true)
                .user("testing")
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        
        return service.createCompletion(completionRequest).getChoices().get(0).getText();
    }
}
