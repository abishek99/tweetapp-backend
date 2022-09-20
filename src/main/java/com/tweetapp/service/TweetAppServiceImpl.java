package com.tweetapp.service;


import com.tweetapp.enumerator.TweetEnum;
import com.tweetapp.enumerator.TweetUserEnum;
import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetUser;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.TweetUserRepository;
import com.tweetapp.security.JwtUtils;
import com.tweetapp.security.SecurityConfig;
import com.tweetapp.security.TweetAppUserDetails;
import com.tweetapp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class TweetAppServiceImpl implements TweetAppService {

    @Autowired
    private TweetUserRepository tweetUserRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TweetAppUserDetails tweetAppUserDetails;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private JwtUtils jwtUtils;

    //@Autowired
    //private KafkaTemplate<String,String> kafkaTemplate;


    public static final Logger LOG = LoggerFactory.getLogger(TweetAppServiceImpl.class);

    @Override
    public TweetUser saveUser(TweetUser tweetUser) {
        if (StringUtils.isEmpty(tweetUser.getLoginId())) {
            LOG.info("LOGIN_ID IS EMPTY");
            throw new TweetAppException(TweetUserEnum.LOGIN_ID_NOT_FOUND);
        }
        if (StringUtils.isNotEmpty(tweetUser.getLoginId())) {
            Optional<TweetUser> loginId = tweetUserRepository.findById(tweetUser.getLoginId());
            if (loginId.isPresent()) {
                LOG.info("LOGIN_ID already present in DB");
                throw new TweetAppException(TweetUserEnum.LOGIN_ID_PRESENT);
            }
        }
        if (!tweetUser.getConfirmPassword().equals(tweetUser.getPassword())) {
            LOG.info("password and confirmPassword mismatch");
            throw new TweetAppException(TweetUserEnum.PASWWORD_MISMATCH);
        }
        tweetUser.setPassword(securityConfig.passwordEncoder().encode(tweetUser.getPassword()));
        //kafkaTemplate.send("tweetapp",tweetUser.getLoginId(),"tweetUser Registered");
        return tweetUserRepository.save(tweetUser);
    }

    @Override
    public List<TweetUser> getAllUsers() {
       return tweetUserRepository.findAll();
    }

    @Override
    public TweetUser getTweetUser(String userName) {
        TweetUser tweetUser = tweetUserRepository.findById(userName).orElse(null);
        if(tweetUser==null){
            LOG.info("user not found in system");
            throw new TweetAppException(TweetUserEnum.USER_NOT_FOUND);
        }
        return tweetUser;
    }

    @Override
    public void forgetPassword(ForgetVo forgetVo) {
        if(StringUtils.isEmpty(forgetVo.getSecret()) || StringUtils.isEmpty(forgetVo.getUserName())){
            throw new TweetAppException(TweetUserEnum.INVALID_CREDENTIALS);
        }
        TweetUser tweetUser = tweetUserRepository.findById(forgetVo.getUserName()).orElse(null);
        if(tweetUser!=null){
            tweetUser.setPassword(securityConfig.passwordEncoder().encode(forgetVo.getChangePassword()));
            tweetUserRepository.save(tweetUser);
        }
        else{
            throw new TweetAppException(TweetEnum.USER_NOT_FOUND);
        }
    }


    @Override
    public Tweet saveTweet(String tweetUserId,Tweet tweet) {
        tweet.setUserTweetId(tweetUserId);
        tweet.setDateOfPost(LocalDateTime.now());
        tweet.setReplyVo(new ArrayList<>());
        tweet.setLikedBy(new HashSet<>());
        LOG.info("---------------Saving Tweet---------------");
        //kafkaTemplate.send("tweetapp",tweet.getId(),"tweet posted");
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet updateTweet(String userName, String id, UpdateVo updateVo)  {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            LOG.info("Tweet present for update Tweet with id:{}",id);
            Tweet updatedTweet = tweet.get();
            updatedTweet.setTweet(updateVo.getUpdatedTweet());
            updatedTweet.setTag(updateVo.getTag());
            updatedTweet.setDateOfPost(LocalDateTime.now());
            tweetRepository.save(updatedTweet);
            return updatedTweet;
        }
        LOG.info("tweet not found in system for update");
        throw new TweetAppException(TweetEnum.TWEET_NOT_FOUND);
    }

    @Override
    public void deleteTweet(String userName, String id) {
        tweetRepository.deleteById(id);
    }

    @Override
    public Long likeTweet(String userName, String id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            LOG.info("Tweet present to Like for id:{}",id);
            if (tweet.get().getLikedBy().contains(userName)) {
                tweet.get().setLike(tweet.get().getLike() - 1);
                tweet.get().getLikedBy().remove(userName);
            } else {
                tweet.get().setLike(tweet.get().getLike() + 1);
                tweet.get().getLikedBy().add(userName);
            }
        } else {
            LOG.info("tweet not found in system for like");
            throw new TweetAppException(TweetEnum.TWEET_NOT_FOUND);
        }
        //kafkaTemplate.send("tweetapp",tweet.get().getUserTweetId(),"tweetUser replied to tweet");
        tweetRepository.save(tweet.get());
        return tweet.get().getLike();
    }

    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    @Override
    public List<Tweet> getTweetsByUserName(String userName) {
        return tweetRepository.findByUserTweetId(userName);
    }


    @Override
    public Tweet replyTweet(String userName, String id, ReplyVo reply) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isPresent()) {
            LOG.info("Tweet present to reply for:{}",id);
            reply.setUserReplyId(userName);
            reply.setRepliedDate(LocalDateTime.now());
            List<ReplyVo> replyList = tweet.get().getReplyVo();
            if(replyList==null) {
                replyList = new ArrayList<>();
                replyList.add(reply);
            }else {
                replyList.add(reply);
            }
            tweet.get().setReplyVo(replyList);
            tweetRepository.save(tweet.get());
            return tweet.get();
        } else {
            LOG.info("tweet not found for reply");
            throw new TweetAppException(TweetEnum.TWEET_NOT_FOUND);
        }
    }

    @Override
    public LoginResponseVo authenticate(LoginVo loginVo) {
        LoginResponseVo loginResponseVo = new LoginResponseVo();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVo.getUserName(), loginVo.getPassword()));
        }catch(Exception e){
            LOG.info("Throwing BadCredentials exception");
            throw new BadCredentialsException("Invalid Credential");
        }
        UserDetails userDetails = tweetAppUserDetails.loadUserByUsername(loginVo.getUserName());
        String jwt = jwtUtils.generateToken(userDetails);
        loginResponseVo.setToken(jwt);
        LOG.info("---------------Authentication Successful---------------");
        return loginResponseVo;
    }

//    public Boolean validate(String token){
//        String username = jwtUtils.extractUsername(token);
//        return jwtUtils.validateToken(token,tweetAppUserDetails.loadUserByUsername(username));
//    }


}
