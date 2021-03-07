package missions.room.Domain.Notifications;

import DataAPI.OpCode;

import javax.persistence.*;
import java.io.Serializable;

public abstract class Notification<T> implements Serializable {

    private OpCode reason;

    public Notification(OpCode reason) {
        this.reason = reason;
    }

    public Notification() {
    }

    public abstract T getValue();

    public OpCode getReason() {
        return reason;
    }
}