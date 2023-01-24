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
                .user("testing")
                .build();
//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
        	return service.createCompletion(completionRequest).getChoices().get(0).getText();	
        } catch (HttpException e) {
			return msg;
		}
    }
}
