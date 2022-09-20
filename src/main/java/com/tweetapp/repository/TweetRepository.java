package com.tweetapp.repository;


import com.tweetapp.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends MongoRepository<Tweet,String> {

        public List<Tweet> findByUserTweetId(String username);

        @Query("{'userTweetId': ?0 ,'id': ?1}")
        Optional<Tweet> findByUserNameAndTweetId(String userTweetId, String id);
}
