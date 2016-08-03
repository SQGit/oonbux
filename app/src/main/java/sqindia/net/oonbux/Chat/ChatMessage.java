package sqindia.net.oonbux.Chat;

public class ChatMessage {

    private String content;
    private int isMine;
    private String message;
    private String messegeBy;
    private String name;
    private String userImage;
    private String message_id;

    public ChatMessage()
    {

    }

    public ChatMessage(String content, int isMine,String id) {
        this.content = content;
        this.isMine = isMine;
        this.message_id = id;
    }

    public String getContent() {
        return content;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {

        this.message_id = message_id;
    }

    public int isMine() {
        return isMine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessegeBy() {
        return messegeBy;
    }

    public void setMessegeBy(String messegeBy) {
        this.messegeBy = messegeBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
