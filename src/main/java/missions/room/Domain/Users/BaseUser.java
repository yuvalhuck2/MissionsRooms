package missions.room.Domain.Users;

import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import missions.room.Domain.Message;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseUser {

    @Id
    protected String alias;

    protected String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @MapKey
    @JoinColumn(name="dest",referencedColumnName = "alias")
    private Map<String, Message> messages;

    public BaseUser(){
        messages=new HashMap<>();
    }

    public BaseUser(String alias, String password) {
        this.alias = alias;
        this.password = password;
        messages=new HashMap<>();
    }

    public BaseUser(String alias){
        this.alias=alias;
        messages=new HashMap<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public abstract OpCode getOpcode();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser baseUser = (BaseUser) o;
        return  Objects.equals(baseUser.alias, alias) &&
                Objects.equals(baseUser.password,password);
    }

    public void addMessage(Message message) {
        messages.put(message.getId(),message);
    }

    public Response<Boolean> deleteMessage(String messageId) {
        if(messages.remove(messageId)==null){
            return new Response<>(false,OpCode.Message_Not_Exist);
        }
        return new Response<>(true,OpCode.Success);
    }

    public Collection<Message> getMessages() {
        return messages.values();
    }

    public abstract UserProfileData getProfileData();
}