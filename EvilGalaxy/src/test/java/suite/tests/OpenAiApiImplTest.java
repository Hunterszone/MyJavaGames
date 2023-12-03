package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;

import retrofit2.HttpException;
import retrofit2.Response;
import util.OpenAiApiImpl;

public class OpenAiApiImplTest {

    @Test
    public void testCreateCompletionSuccess() throws Exception {
        String prompt = "Test prompt";
        String expectedResponse = "Test";

        OpenAiService serviceMock = mock(OpenAiService.class);
        CompletionResult completionCallMock = mock(CompletionResult.class);
        Response<?> responseMock = Response.success(expectedResponse);

        Mockito.doReturn(completionCallMock).when(serviceMock).createCompletion(Mockito.any(CompletionRequest.class));
        Mockito.doReturn(responseMock.toString()).when(completionCallMock).toString();

        String result = OpenAiApiImpl.createCompletion(prompt).substring(0, 4);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testCreateCompletionFailure() throws IOException {
        String prompt = "Test prompt";
        String expectedFallbackResponse = "Test prompt";

        OpenAiService serviceMock = mock(OpenAiService.class);
        CompletionResult completionCallMock = mock(CompletionResult.class);
        HttpException httpExceptionMock = mock(HttpException.class);

        when(serviceMock.createCompletion(Mockito.any(CompletionRequest.class))).thenReturn(completionCallMock);
        when(completionCallMock.toString()).thenThrow(httpExceptionMock);

        String result = OpenAiApiImpl.createCompletion(prompt).substring(0, 11);

        assertEquals(expectedFallbackResponse, result);
    }
}
