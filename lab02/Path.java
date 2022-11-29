/** A class that represents a path via pursuit curves. */
public class Path {

    // TODO
    public Point curr;
    public Point next;
    public Path(double x, double y) {
        curr = new Point();
        next = new Point(x, y);
    }

    public double getCurrX() {
        return curr.getX();
    }
    public double getCurrY() {
        return curr.getY();
    }
    public double getNextX() {
        return next.getX();
    }
    public double getNextY(){
        return next.getY();
    }
    public Point getCurrentPoint(){
        return new Point(getCurrX(), getCurrY());
    }

    public void setCurrentPoint(Point point) {
        Point curr = new Point(point.getX(), point.getY());
    }

    public void iterate(double dx, double dy) {
        curr = next;
        next = new Point(getCurrX() + dx, getCurrY() + dy);
    }
}
