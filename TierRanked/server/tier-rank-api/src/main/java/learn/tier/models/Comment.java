package learn.tier.models;

import java.time.LocalDateTime;

public class Comment {

    private int commentId;
    private String comment;
    private LocalDateTime timestamp;
    private int tierListId;
    private int appUserId;

    public Comment() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getTierListId() {
        return tierListId;
    }

    public void setTierListId(int tierListId) {
        this.tierListId = tierListId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

}
