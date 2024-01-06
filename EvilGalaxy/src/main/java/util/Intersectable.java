package util;

import java.awt.Shape;

@FunctionalInterface
public interface Intersectable {
	void intersects(Shape entity);
}
