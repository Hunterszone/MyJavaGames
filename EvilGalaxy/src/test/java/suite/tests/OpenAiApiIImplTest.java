package suite.tests;

import org.junit.Assert;
import org.junit.Test;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import enums.Entity;

public class OpenAiApiIImplTest {

	@Test
	public void testCompletionRequest() {
		OpenAiService service = new OpenAiService("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");
		CompletionRequest completionRequest = CompletionRequest.builder()
                .model("davinci")
                .prompt(Entity.ALIEN.getMsg())
                .echo(true)
                .user("testing")
                .build();
		
		Assert.assertTrue(service.createCompletion(completionRequest).getChoices().get(0).getText().contains(Entity.ALIEN.getMsg()));
	}
}
