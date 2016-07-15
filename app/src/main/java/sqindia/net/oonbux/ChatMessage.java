package sqindia.net.oonbux;

public class ChatMessage {

    private String content;
    private int isMine;
    private String message;
    private String messegeBy;
    private String name;
    private String userImage;

    public ChatMessage()
    {

    }

    public ChatMessage(String content, int isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
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
