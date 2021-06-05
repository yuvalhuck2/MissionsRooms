package DataObjects.APIObjects;

public class UserChatData {
    private String _id;
    private String name;

    public UserChatData(String id, String name) {
        this._id = id;
        this.name = name;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

}
