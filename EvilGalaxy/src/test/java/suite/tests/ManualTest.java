package suite.tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import extras.Manual;

public class ManualTest {

    private FrameFixture window;

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeLater(() -> {
            Manual readme = new Manual();
            window = new FrameFixture(readme);
            window.show();
        });
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void testManualContent() throws InterruptedException {
        // Wait for the frame to be ready
        Thread.sleep(2000);

        JTextPane readme = (JTextPane) window.textBox("readme").target();

        assertEquals(new Font("Arial", Font.BOLD | Font.PLAIN, 18), readme.getFont());
        assertEquals(Color.DARK_GRAY, readme.getBackground());
        assertEquals(false, readme.isEditable());
    }
}
