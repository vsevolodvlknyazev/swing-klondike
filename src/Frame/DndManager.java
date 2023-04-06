package Frame;

import javax.swing.*;
import java.awt.*;

public class DndManager {
    private final JLayeredPane panel;
    private boolean isDragging = false;

    public DndManager(JLayeredPane panel) {
        this.panel = panel;
    }

    public void drag(Component component, Point relativePos) {
        // do this before loop to avoid flickering bug
        setRelativeComponentPos(
                component, relativePos, panel.getMousePosition());

        isDragging = true;
        new Thread(() -> {
            while (isDragging) {
                try {
                    setRelativeComponentPos(
                            component, relativePos, panel.getMousePosition());
                }
                catch (NullPointerException e) {
                    // wait until the cursor will return to the panel
                }
            }
        }).start();
    }

    public void stop() {
        isDragging = false;
    }

    /**
     * preserve the position of the component
     * relative to the mouse cursor
     */
    public static void setRelativeComponentPos(
            Component component, Point relativePos, Point panelPos) {
        final int objectX = (int)(panelPos.getX() - relativePos.getX());
        final int objectY = (int)(panelPos.getY() - relativePos.getY());
        component.setLocation(objectX,objectY);
    }
}