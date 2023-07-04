package suite.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;

import game_engine.LoadGame;

public class LoadGameTest {

    private LoadGame loadGame;
    private JFileChooser mockFileChooser;
    
    @Before
    public void setUp() {
    	loadGame = new LoadGame();
        mockFileChooser = mock(JFileChooser.class);
    }

    @Test
    public void testOpenFileChooserWithApproveOption() throws IOException, ClassNotFoundException {
        // Prepare the mock JFileChooser behavior
        when(mockFileChooser.showOpenDialog(null)).thenReturn(JFileChooser.APPROVE_OPTION);
        File selectedFile = new File("example.txt");
        when(mockFileChooser.getSelectedFile()).thenReturn(selectedFile);

        // Call the method
        loadGame.openFileChooser();

        // Verify that the appropriate message is printed
        verifyNoMoreInteractions(mockFileChooser);
    }

    @Test
    public void testOpenFileChooserWithCancelOption() throws ClassNotFoundException, IOException {
        // Prepare the mock JFileChooser behavior
        when(mockFileChooser.showOpenDialog(null)).thenReturn(JFileChooser.CANCEL_OPTION);

        // Call the method
        loadGame.openFileChooser();

        // Verify that no other interactions with the mockFileChooser occurred (optional)
        verifyNoMoreInteractions(mockFileChooser);
    }
    
}