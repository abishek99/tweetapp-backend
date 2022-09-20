package com.tweetapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tweetapp.vo.ReplyVo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document
public class Tweet {


    @Id
    private String tweetId;

    @NotBlank(message="tweet is empty")
    @Size(max=144, message = "tweet max limit exceeded")
    private String tweet;

    private String userTweetId;

    private LocalDateTime dateOfPost;

    @Size(max=50,message = "tag max limit exceeded")
    private String tag;

    private List<ReplyVo> replyVo;

    private Long like;

    @JsonIgnore
    private Set<String> likedBy;

    //@PersistenceCreator
    public Tweet(String tweetId, String tweet, String userTweetId, LocalDateTime dateOfPost, String tag, List<ReplyVo> replyVo, Long like , Set<String> likedBy) {
        this.tweetId = tweetId;
        this.tweet = tweet;
        this.userTweetId = userTweetId;
        this.dateOfPost = dateOfPost;
        this.tag = tag;
        this.replyVo = replyVo;
        this.like = like;
        this.likedBy = likedBy;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUserTweetId() {
        return userTweetId;
    }

    public void setUserTweetId(String userTweetId) {
        this.userTweetId = userTweetId;
    }

    public LocalDateTime getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(LocalDateTime dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<ReplyVo> getReplyVo() {
        return replyVo;
    }

    public void setReplyVo(List<ReplyVo> replyVo) {
        this.replyVo = replyVo;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public Set<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<String> likedBy) {
        this.likedBy = likedBy;
    }

    public Tweet() {

    }


}
