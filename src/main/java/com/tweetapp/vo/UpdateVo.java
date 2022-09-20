package com.tweetapp.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateVo {

    @NotBlank(message="tweet is empty")
    @Size(max=144, message = "tweet max limit exceeded")
    private String updatedTweet;

    @Size(max=50,message = "tag max limit exceeded")
    private String tag;

    public String getUpdatedTweet() {
        return updatedTweet;
    }

    public void setUpdatedTweet(String updatedTweet) {
        this.updatedTweet = updatedTweet;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
