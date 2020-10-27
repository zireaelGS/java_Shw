import static java.lang.Math.*;

public class MyFirstNoHelloProgram {

    public static void main(String[] args) {
        System.out.println("Поиграемся с точками, поехали!\n");
        /*Создаем два объекта,т.е. наши две точки с указанием их координат в качестве параметров*/
        Point point1 = new Point(10, 34);
        Point point2 = new Point(3, 10);
        /*Выводим на экран результат вычисления расстояния между этими точками с помощью функции */
        System.out.println("Есть первая точка с координатами (" + point1.x + ", " + point1.y + "),\nа так " +
                "же вторая точка с координатами (" + point2.x + ", " + point2.y + ").\nРасстоняние между ними равно "
                + distance(point1, point2) + "\nЭто кстати выполненно с помощью функции.\n");
        /*Выводим на экран результат вычисления расстояния между этими точками с помощью метода */
        System.out.println("А теперь вызовем именно метод, а не функцию!\nО,чудо, результат то все тот же!" +
                "\nОн равен " + point1.distance(point2));

    }
    /*Функция вычисляющая растояние между точками*/
    public static double distance(Point p1, Point p2) {
        return sqrt(pow((p1.x - p2.x), 2) + pow((p1.y - p2.y), 2));
    }
}