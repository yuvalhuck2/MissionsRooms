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

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}