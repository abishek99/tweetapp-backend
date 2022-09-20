package com.tweetapp.service;


import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetUser;
import com.tweetapp.vo.*;

import java.util.List;

public interface TweetAppService {


     TweetUser saveUser(TweetUser tweetUserVo);

     List<TweetUser> getAllUsers();

     TweetUser getTweetUser(String userName);

     void forgetPassword(ForgetVo forgetVo);



     Tweet saveTweet(String tweetUserId,Tweet tweet);

     Tweet updateTweet(String userName, String id, UpdateVo updateVo);

     void deleteTweet(String userName ,String id);

     Long likeTweet(String userName,String id);

     Tweet replyTweet(String username, String id, ReplyVo reply);

     List<Tweet> getAllTweets();

     List<Tweet> getTweetsByUserName(String userName);


     LoginResponseVo authenticate(LoginVo loginVo);

//     Boolean validate(String token);

}
