package suite.tests;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import enums.Entity;
import retrofit2.HttpException;

public class OpenAiApiIImplTest {

	@Ignore("TBD")
	@Test
	public void testCompletionRequest() {
		OpenAiService service = new OpenAiService("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"); // put your API key
		CompletionRequest completionRequest = CompletionRequest.builder()
                .model("davinci")
                .prompt(Entity.ALIEN.getMsg())
                .echo(true)
                .user("testing")
                .build();
		
		try {
			Assert.assertTrue(service.createCompletion(completionRequest).getChoices().get(0).getText().contains(Entity.ALIEN.getMsg()));	
        } catch (HttpException e) {
			e.printStackTrace();
		}
	}
}
