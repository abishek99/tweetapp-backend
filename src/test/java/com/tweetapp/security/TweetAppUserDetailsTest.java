package com.tweetapp.security;

import com.tweetapp.model.TweetUser;
import com.tweetapp.repository.TweetUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TweetAppUserDetailsTest {

    @InjectMocks
    TweetAppUserDetails tweetAppUserDetails;

    @Mock
    private TweetUserRepository tweetUserRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadByUserNameTest(){
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
        Mockito.when(tweetUserRepository.findById("aiden_57")).thenReturn(Optional.of(tweetUser));
        Assertions.assertNotNull(tweetAppUserDetails.loadUserByUsername("aiden_57"));
    }

}
