package DataObjects.FlatDataObjects;

import java.util.Objects;

public class PointsData {

    private String name;

    private int points;

    private boolean canReduce;

    public PointsData(String name, int points) {
        this.name=name;
        this.points=points;
        this.canReduce=false;
    }

    public PointsData(String className, GroupType groupType, int points) {
        this.name=className+" "+groupType.toString();
        this.points=points;
        this.canReduce=false;
    }

    public PointsData(String name, int points, boolean canReduce) {
        this.name=name;
        this.points=points;
        this.canReduce=canReduce;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public boolean isCanReduce() {
        return canReduce;
    }

    public void SetCanReduce(boolean canReduce) {
        this.canReduce=canReduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointsData that = (PointsData) o;
        return points == that.points &&
                canReduce == that.canReduce &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, points, canReduce);
    }
}
