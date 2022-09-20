package com.tweetapp.service;


import com.tweetapp.exception.TweetAppException;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetUser;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.TweetUserRepository;
import com.tweetapp.security.JwtUtils;
import com.tweetapp.security.SecurityConfig;
import com.tweetapp.security.TweetAppUserDetails;
import com.tweetapp.vo.ForgetVo;
import com.tweetapp.vo.LoginVo;
import com.tweetapp.vo.ReplyVo;
import com.tweetapp.vo.UpdateVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TweetAppServiceTest {

    @InjectMocks
    private TweetAppServiceImpl tweetAppService;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private TweetUserRepository tweetUserRepository;

    @Mock
    private SecurityConfig securityConfig;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TweetAppUserDetails tweetAppUserDetails;

    @Mock
    JwtUtils jwtUtils;

//    @Mock
//    private KafkaTemplate<String,String> kafkaTemplate;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Add Register")
    void addRegisterTest() {
        TweetUser tweetUser = new TweetUser();
        Assertions.assertThrows(TweetAppException.class, () -> tweetAppService.saveUser(tweetUser));

        tweetUser.setLoginId("aiden_57");
        tweetUser.setFirstName("aiden");
        tweetUser.setLastName("pearce");
        tweetUser.setEmail("aidenPearce");
        tweetUser.setContactNumber("9688586698");
        tweetUser.setRoles(Arrays.asList("USER"));
        tweetUser.setConfirmPassword("9688586698");
        tweetUser.setPassword("96885866981");
        Assertions.assertThrows(TweetAppException.class, () -> tweetAppService.saveUser(tweetUser));

        Mockito.when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder());
        tweetUser.setPassword("9688586698");
        Mockito.when(tweetUserRepository.save(tweetUser)).thenReturn(tweetUser);
        Assertions.assertEquals(tweetAppService.saveUser(tweetUser), tweetUser);
    }


    @Test
    @DisplayName("Get All Users")
    void getAllUsersTest(){
        TweetUser tweetUser = new TweetUser();
        tweetUser.setLoginId("aiden_57");
        tweetUser.setFirstName("aiden");
        tweetUser.setLastName("pearce");
        tweetUser.setEmail("aidenPearce");
        tweetUser.setContactNumber("9688586698");
        tweetUser.setRoles(Arrays.asList("USER"));
        tweetUser.setConfirmPassword("9688586698");
        tweetUser.setPassword("9688586698");
        Mockito.when(tweetUserRepository.findAll()).thenReturn(Arrays.asList(tweetUser));
        Assertions.assertNotNull(tweetAppService.getAllUsers());
    }


    @Test
    @DisplayName("Get Tweet User")
    void getTweetUser(){
        TweetUser tweetUser = new TweetUser();
        tweetUser.setLoginId("aiden_57");
        tweetUser.setFirstName("aiden");
        tweetUser.setLastName("pearce");
        tweetUser.setEmail("aidenPearce");
        tweetUser.setContactNumber("9688586698");
        tweetUser.setRoles(Arrays.asList("USER"));
        tweetUser.setConfirmPassword("9688586698");
        tweetUser.setPassword("9688586698");
        tweetUser.setSecretKey("secret");
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.getTweetUser("aiden_57"));
        Mockito.when(tweetUserRepository.findById("aiden_57")).thenReturn(Optional.of(tweetUser));
        Assertions.assertNotNull(tweetAppService.getTweetUser("aiden_57"));
    }

    @Test
    @DisplayName("Add Tweet")
    void addTweetTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
        Assertions.assertNotNull(tweetAppService.saveTweet("aiden_57",tweet));
    }



    @Test
    @DisplayName("Update Tweet")
    void updateTweetTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        UpdateVo updateVo = new UpdateVo();
        updateVo.setUpdatedTweet("tweetChanged");
        updateVo.setTag("adw@");
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.updateTweet(tweet.getUserTweetId(),tweet.getTweetId(),updateVo));
        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(Optional.of(tweet));
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
        Assertions.assertNotNull(tweetAppService.updateTweet(tweet.getUserTweetId(),tweet.getTweetId(),updateVo));
    }

    @Test
    @DisplayName("Delete Tweet")
    void deleteTweetTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        tweetRepository.deleteById(tweet.getTweetId());
        tweetAppService.deleteTweet(tweet.getUserTweetId(),tweet.getTweetId());
    }

    @Test
    @DisplayName("Like Tweet")
    void liketweetTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.likeTweet(tweet.getUserTweetId(),tweet.getTweetId()));

        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(Optional.of(tweet));
        Assertions.assertNotNull(tweetAppService.likeTweet(tweet.getUserTweetId(),tweet.getTweetId()));

        tweet.setLike(0L);
        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(Optional.of(tweet));
        Assertions.assertNotNull(tweetAppService.likeTweet(tweet.getUserTweetId(),tweet.getTweetId()));
    }

    @Test
    @DisplayName("Get All Tweet")
    void getAllTweetsTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        Mockito.when(tweetRepository.findAll()).thenReturn(Arrays.asList(tweet));
        Assertions.assertNotNull(tweetAppService.getAllTweets());
    }


    @Test
    @DisplayName("Get Tweet By UserName")
    void getTweetByUserTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setReplyVo(Arrays.asList(new ReplyVo()));
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        Mockito.when(tweetRepository.findByUserTweetId(tweet.getUserTweetId())).thenReturn(Arrays.asList(tweet));
        Assertions.assertNotNull(tweetAppService.getTweetsByUserName(tweet.getUserTweetId()));
    }


    @Test
    @DisplayName("Reply Tweet")
    void replyTweetTest() {
        Tweet tweet = new Tweet();
        tweet.setTweetId("12L");
        tweet.setLikedBy(new HashSet<>());
        tweet.setTweet("tweet added");
        tweet.setTag("@iiht");
        tweet.setUserTweetId("aiden_57");
        tweet.setLike(1L);
        ReplyVo replyvo = new ReplyVo();
        replyvo.setUserReplyId("aiden_56");
        replyvo.setReplied("tweetReplied");
        List<ReplyVo> replyVoList = new ArrayList<>();
        replyVoList.add(replyvo);
        tweet.setReplyVo(replyVoList);
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.replyTweet(tweet.getUserTweetId(),tweet.getTweetId(),replyvo));
        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(Optional.of(tweet));
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
        Assertions.assertNotNull(tweetAppService.replyTweet(tweet.getUserTweetId(),tweet.getTweetId(),replyvo));
    }

    @Test
    @DisplayName("Login Tweet")
    void loginTest(){
        LoginVo loginTweet = new LoginVo("aiden_57","aiden");
        Mockito.when(tweetAppUserDetails.loadUserByUsername(loginTweet.getUserName())).thenReturn(new User("aiden_57","aiden",new ArrayList<>()));
        Assertions.assertNotNull(tweetAppService.authenticate(loginTweet));
    }


    @Test
    @DisplayName("forget password")
    void forgetPassword(){
        TweetUser tweetUser = new TweetUser();
        tweetUser.setLoginId("aiden_57");
        tweetUser.setFirstName("aiden");
        tweetUser.setLastName("pearce");
        tweetUser.setEmail("aidenPearce");
        tweetUser.setContactNumber("9688586698");
        tweetUser.setRoles(Arrays.asList("USER"));
        tweetUser.setConfirmPassword("9688586698");
        tweetUser.setPassword("9688586698");

        ForgetVo forgetVo = new ForgetVo();
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.forgetPassword(forgetVo));

        forgetVo.setSecret("secret");
        forgetVo.setChangePassword("aiden");
        forgetVo.setUserName("aiden_57");
        Assertions.assertThrows(TweetAppException.class,()->tweetAppService.forgetPassword(forgetVo));

        Mockito.when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder());
        Mockito.when(tweetUserRepository.findById("aiden_57")).thenReturn(Optional.ofNullable(tweetUser));
        tweetAppService.forgetPassword(forgetVo);
    }

}
