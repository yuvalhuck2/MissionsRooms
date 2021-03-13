package missions.room.Domain.Users;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.UserProfileData;
import missions.room.Domain.Message;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    protected String alias;

    protected String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @MapKey
    @JoinColumn(name="dest",referencedColumnName = "alias")
    private Map<String, Message> messages;

    public User(){
        messages=new HashMap<>();
    }

    public User(String alias, String password) {
        this.alias = alias;
        this.password = password;
        messages=new HashMap<>();
    }

    public User(String alias){
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
        User user = (User) o;
        return  Objects.equals(user.alias, alias) &&
                Objects.equals(user.password,password);
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

