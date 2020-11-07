
import org.junit.Test;
import org.testng.Assert;

public class PointTests {
    @Test
    public void testDistance() {
        Point point1 = new Point(10, 34);
        Point point2 = new Point(3, 10);
        Assert.assertEquals(point1.distance(point2), 25.0);
    }

    @Test
    public void testTheCoincidenceOfPoints() {
        Point point1 = new Point(10, 34);
        Point point2 = new Point(10, 34);
        Assert.assertEquals(point1.x, point2.x);
        Assert.assertEquals(point1.y, point2.y);
    }

    @Test
    public void testDistanceIsPositiveValue() {
        Point point1 = new Point(10, 34);
        Point point2 = new Point(3, 10);
        Assert.assertTrue(point1.distance(point2) > 0.0);
    }

    @Test
    public void testPoint1IsHighterThanPoint2() {
        Point point1 = new Point(10, 34);
        Point point2 = new Point(3, 10);
        Assert.assertTrue(point1.x > point2.x);
    }
}
