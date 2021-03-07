package missions.room.Domain.Notifications;

import DataAPI.OpCode;

public class NonPersistenceNotification<T> extends Notification<T> {

    private final T value;

    public NonPersistenceNotification(OpCode reason, T value) {
        super(reason);
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
