package DataAPI;

import Utils.Utils;

public class ClassroomNameData {

    private final String name;
    private final String hebrewName;

    public ClassroomNameData(String name) {
        this.name = name;
        this.hebrewName = Utils.getClassHebrewName(name);
    }

    public String getName() {
        return name;
    }

    public String getHebrewName() {
        return hebrewName;
    }
}
