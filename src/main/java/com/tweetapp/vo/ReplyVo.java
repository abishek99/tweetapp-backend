package com.tweetapp.vo;



import java.time.LocalDateTime;
import java.util.List;

public class ReplyVo {

    private String userReplyId;

    private String replied;

    private List<String> replyTag;

    private LocalDateTime repliedDate;

    public ReplyVo(String userReplyId , String replied, LocalDateTime repliedDate) {
        this.userReplyId = userReplyId;
        this.replied = replied;
        this.repliedDate = repliedDate;
    }

    public String getUserReplyId() {
        return userReplyId;
    }

    public void setUserReplyId(String userReplyId) {
        this.userReplyId = userReplyId;
    }

    public String getReplied() {
        return replied;
    }

    public void setReplied(String replied) {
        this.replied = replied;
    }


    public List<String> getReplyTag() {
        return replyTag;
    }

    public void setReplyTag(List<String> replyTag) {
        this.replyTag = replyTag;
    }

    public LocalDateTime getRepliedDate() {
        return repliedDate;
    }

    public void setRepliedDate(LocalDateTime repliedDate) {
        this.repliedDate = LocalDateTime.now();
    }

    public ReplyVo() {
    }
}
