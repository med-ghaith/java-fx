package esprit.changemakers.sportshub.entities;

import esprit.changemakers.sportshub.utils.enumerations.MessageType;

import java.sql.Timestamp;

/**
 * @author Jozef
 */
public class PrivateMessage implements Comparable<PrivateMessage> {

    private int id;
    private int idFirstUser;
    private int idSecondUser;
    private String content;
    private Timestamp timestamp;

    private MessageType type;

    public PrivateMessage(int id, int idFirstUser, int idSecondUser, String content, Timestamp timestamp) {
        this.id = id;
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.content = content;
        this.timestamp = timestamp;
    }

    public PrivateMessage(int idFirstUser, int idSecondUser, String content, Timestamp timestamp) {
        this.idFirstUser = idFirstUser;
        this.idSecondUser = idSecondUser;
        this.content = content;
        this.timestamp = timestamp;
    }

    public PrivateMessage() {
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFirstUser() {
        return idFirstUser;
    }

    public void setIdFirstUser(int idFirstUser) {
        this.idFirstUser = idFirstUser;
    }

    public int getIdSecondUser() {
        return idSecondUser;
    }

    public void setIdSecondUser(int idSecondUser) {
        this.idSecondUser = idSecondUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "idFirstUser=" + idFirstUser +
                ", idSecondUser=" + idSecondUser +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivateMessage)) return false;

        PrivateMessage that = (PrivateMessage) o;

        if (getIdFirstUser() != that.getIdFirstUser()) return false;
        if (getIdSecondUser() != that.getIdSecondUser()) return false;
        if (getContent() != null ? !getContent().equals(that.getContent()) : that.getContent() != null) return false;
        return getTimestamp() != null ? getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdFirstUser();
        result = 31 * result + getIdSecondUser();
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(PrivateMessage o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }
}
