package com.tweetapp.api;

import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetAppService;
import com.tweetapp.vo.ReplyVo;
import com.tweetapp.vo.UpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/tweets")
public class TweetAppApi {

    @Autowired
    private TweetAppService tweetAppService;



    /**
     *
     * @return List<Tweet>
     */
    @GetMapping(value = "/all",name = "GET_ALL_TWEETS")
    public ResponseEntity<List<Tweet>> getAllTweets() {
        return new ResponseEntity<>(tweetAppService.getAllTweets(),HttpStatus.OK);
    }


    /**
     *
     * @param userName
     * @return List<Tweet>
     */
    @GetMapping(value = "/{userName}",name = "GET_TWEET_BY_USERNAME")
    public ResponseEntity<List<Tweet>> getTweetsByUserName(@RequestHeader("Authorization") String token ,@PathVariable("userName") String userName) {
        return new ResponseEntity<>(tweetAppService.getTweetsByUserName(userName),HttpStatus.OK);
    }


    /**
     *
     * @param tweet
     * @return Tweet
     */
    @PostMapping(value="/{userName}/add",name = "ADD_TWEET")
    public ResponseEntity<Tweet> addTweet(@RequestHeader("Authorization") String token ,@PathVariable("userName") String userName,@Valid @RequestBody Tweet tweet) {
        return new ResponseEntity<>(tweetAppService.saveTweet(userName,tweet),HttpStatus.OK);
    }


    /**
     *
     * @param userName
     * @param id
     * @param updateVo
     * @return Tweet
     */
    @PutMapping(value="/{userName}/update/{id}",name = "UPDATE_TWEET")
    public ResponseEntity<Tweet> updateTweet(@RequestHeader("Authorization")String token, @PathVariable("userName") String userName, @PathVariable("id") String id, @RequestBody UpdateVo updateVo) {
        return new ResponseEntity<>(tweetAppService.updateTweet(userName,id,updateVo),HttpStatus.OK);
    }


    /**
     *
     * @param userName
     * @param id
     */
    @DeleteMapping(value="/{userName}/delete/{id}",name = "DELETE_TWEET")
    public void deleteTweet(@RequestHeader("Authorization")String token, @PathVariable("userName") String userName, @PathVariable("id") String id) {
        tweetAppService.deleteTweet(userName,id);
    }

    /**
     *
     * @param userName
     * @param id
     * @return Long
     */
    @PutMapping(value = "/{userName}/like/{id}",name = "LIKE_TWEET")
    public ResponseEntity<Long> likeTweet(@RequestHeader("Authorization")String token, @PathVariable("userName") String userName, @PathVariable("id") String id) {
        return new ResponseEntity<>(tweetAppService.likeTweet(userName,id),HttpStatus.OK);
    }

    /**
     *
     * @param userName
     * @param id
     * @param reply
     * @return Tweet
     */
    @PostMapping(value="/{userName}/reply/{id}",name = "REPLY_TWEET")
    public ResponseEntity<Tweet> replyTweet(@RequestHeader("Authorization")String token, @PathVariable("userName") String userName, @PathVariable("id") String id,@RequestBody ReplyVo reply) {
        return new ResponseEntity<>(tweetAppService.replyTweet(userName,id,reply),HttpStatus.OK);
    }

}
