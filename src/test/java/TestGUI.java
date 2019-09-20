import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGUI {
    private FrameFixture window;
    private Robot robot = BasicRobot.robotWithNewAwtHierarchy();

    @BeforeEach
    void setUp() {
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        GUI frame = GuiActionRunner.execute(GUI::new);
        window = new FrameFixture(robot, frame);
        window.show();
    }

    @Test
    void testFunFileChoose() {
        window.button("newGame").requireVisible();
        window.button("newGame").click();
        window.button("up").requireVisible();
        window.button("up").click();
        window.button("autoPlay").requireVisible();
        window.button("autoPlay").click();
    }

    @AfterEach
    void tearDown() {
        window.cleanUp();
    }
}
