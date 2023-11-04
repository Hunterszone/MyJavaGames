package util;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import retrofit2.HttpException;

public class OpenAiApiImpl {
	
    public static String createCompletion(String msg) {
//        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService("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");

        System.out.println("\nCreating completion...");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("davinci")
                .prompt(msg)
                .echo(true)
                .temperature(0.1)
                .user("testing")
                .build();
//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
        	return service.createCompletion(completionRequest).getChoices().get(0).getText();	
        } catch (HttpException e) {
        	System.err.println("createCompletion exception: " + e.getMessage());
			return msg;
		}
    }
}
