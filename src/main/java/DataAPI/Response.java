package DataAPI;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private T value;
    private OpCode reason;
    private  String msg;

    public Response(T value, OpCode reason) {
        this.value = value;
        this.reason = reason;
    }

    public Response(T value, OpCode reason, String msg) {
        this.value = value;
        this.reason = reason;
        this.msg = msg;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public OpCode getReason() {
        return reason;
    }

    public void setReason(OpCode reason) {
        this.reason = reason;
    }
}
