package com.tweetapp.security;

import com.tweetapp.model.TweetUser;
import com.tweetapp.repository.TweetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetAppUserDetails implements UserDetailsService {

    @Autowired
    private TweetUserRepository tweetUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TweetUser> tweetUser = tweetUserRepository.findById(username);
        if(tweetUser.isPresent()){
            Collection<? extends GrantedAuthority> grantedAuthority =tweetUser.get().getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
            return new User(tweetUser.get().getLoginId(),tweetUser.get().getPassword(),grantedAuthority);
        }else{
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
