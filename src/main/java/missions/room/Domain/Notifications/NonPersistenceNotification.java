package missions.room.Domain.Notifications;

import DataObjects.FlatDataObjects.OpCode;

public class NonPersistenceNotification<T> extends Notification<T> {

    private final T value;

    private String additionalData;

    public NonPersistenceNotification(OpCode reason, T value) {
        super(reason);
        this.value = value;
    }

    public NonPersistenceNotification(OpCode reason, T value, String additionalData) {
        super(reason);
        this.value = value;
        this.additionalData = additionalData;
    }

    @Override
    public T getValue() {
        return value;
    }

    public String getAdditionalData() {
        return additionalData;
    }


}
