package seal.VideoService.comment;

public class CommentRequest {
    
    private long userId;
    
    private String comment;

    public CommentRequest() {
    }

    public CommentRequest(long userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
