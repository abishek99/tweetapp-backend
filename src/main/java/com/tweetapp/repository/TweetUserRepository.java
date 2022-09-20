package com.tweetapp.repository;

import com.tweetapp.model.TweetUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TweetUserRepository extends MongoRepository<TweetUser,String> {

    Optional<TweetUser> findByEmail(String loginId);

    @Query("{'loginId': ?0 ,'password': ?1}")
    TweetUser findByUsernameAndPassword(String userName,String password);

}
