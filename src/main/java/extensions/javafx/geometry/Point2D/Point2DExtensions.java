package extensions.javafx.geometry.Point2D;

import javafx.geometry.Point2D;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * @author Enaium
 */
@Extension
public class Point2DExtensions {
    public static Point2D plus(@This Point2D self, Point2D point) {
        return self.add(point);
    }

    public static Point2D minus(@This Point2D self, Point2D point) {
        return self.subtract(point);
    }

    public static Point2D times(@This Point2D self, Point2D point) {
        return new Point2D(self.getX() * point.getX(), self.getY() * point.getY());
    }

    public static Point2D times(@This Point2D self, double value) {
        return self.multiply(value);
    }

    public static Point2D div(@This Point2D self, Point2D point) {
        return new Point2D(self.getX() / point.getX(), self.getY() / point.getY());
    }

    public static Point2D div(@This Point2D self, double value) {
        return self.multiply(1 / value);
    }

    public static Point2D copy(@This Point2D self) {
        return new Point2D(self.getX(), self.getY());
    }
}
