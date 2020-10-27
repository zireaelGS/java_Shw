import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Point {

    /*Объявляю переменные*/
    public double x;
    public double y;
    /*Конструктор  класса поинт*/
    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }
    /*Метод,который вычисляет расстояние между точками*/
    public double distance(Point point2) {
        return sqrt(pow((this.x - point2.x), 2) + pow((this.y - point2.y), 2));
    }


}
