package extensions.javafx.scene.canvas.GraphicsContext;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * @author Enaium
 */
@Extension
public class GraphicsContextExtensions {
    public static void drawText(@This GraphicsContext self, String text, double x, double y, Color color) {
        self.setFill(color);
        self.fillText(text, x, y + new Text(text).getFont().getSize());
    }

    public static void drawText(@This GraphicsContext self, String text, double x, double y, double width, double height, Color color) {
        self.setFill(color);
        self.fillText(text, x + width / 2 - new Text(text).getLayoutBounds().getWidth() / 2, y + height / 2 + new Text(text).getFont().getSize() / 2);
    }

    public static void drawText(@This GraphicsContext self, String text, Rectangle2D rectangle, Color color) {
        drawText(self, text, rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

    public static void drawOval(@This GraphicsContext self, Rectangle2D rectangle, Color color) {
        self.setFill(color);
        self.fillOval(rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
    }
}
