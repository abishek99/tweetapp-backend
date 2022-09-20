package com.tweetapp.exception;


public class TweetAppException extends RuntimeException {

    public TweetAppException(Enum message) {
        super(String.valueOf(message));
    }

    public TweetAppException(Enum message, Throwable cause) {
        super(String.valueOf(message), cause);
    }

}
