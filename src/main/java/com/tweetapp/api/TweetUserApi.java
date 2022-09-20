package com.tweetapp.api;


import com.tweetapp.model.TweetUser;
import com.tweetapp.service.TweetAppService;
import com.tweetapp.vo.ForgetVo;
import com.tweetapp.vo.LoginResponseVo;
import com.tweetapp.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/tweets")
public class TweetUserApi {

    @Autowired
    private TweetAppService tweetAppService;

    /**
     *
     * @param tweetUser
     * @return TweetUser
     */
    @PostMapping(value = "/register")
    public ResponseEntity<TweetUser> userRegister(@Valid @RequestBody TweetUser tweetUser){
        return new ResponseEntity<>(tweetAppService.saveUser(tweetUser),HttpStatus.OK);
    }

//    /**
//     *
//     * @param
//     * @return
//     */
//    @GetMapping(value = "/authenticate/{token}" ,name="validate_token")
//    public ResponseEntity<Boolean> authenticate(@PathVariable("token") String token){
//        return new ResponseEntity<>(tweetAppService.validate(token),HttpStatus.OK);
//    }

    /**
     *
     * @param loginVo
     * @return LoginResponseVo
     */
    //@GetMapping(value = "/login",name = "LOGIN")
    @PostMapping(value = "/login",name = "LOGIN")
    public ResponseEntity<LoginResponseVo> userLogin(@RequestBody LoginVo loginVo) {
        return new ResponseEntity<>(tweetAppService.authenticate(loginVo),HttpStatus.OK);
    }

    @PostMapping(value = "/{username}/forgot",name = "FORGET_PASSWORD")
    public void forgotPassword(@PathVariable("username") String userName,@RequestBody ForgetVo forgetVo){
        tweetAppService.forgetPassword(forgetVo);
    }

    /**
     *
     * @return List<TweetUser>
     */
    @GetMapping(value = "/users/all",name = "GET_ALL_USERS")
    public ResponseEntity<List<TweetUser>> getAllUsers() {
        return new ResponseEntity<>(tweetAppService.getAllUsers(), HttpStatus.OK);
    }


    /**
     *
     * @param userName
     * @return TweetUser
     */
    @GetMapping(value = "/user/search/{username}",name = "GET_TWEET_BY_USERNAME")
    public ResponseEntity<TweetUser> getTweetUser(@RequestHeader("Authorization") String token,@PathVariable("username") String userName) {
        return new ResponseEntity<>(tweetAppService.getTweetUser(userName),HttpStatus.OK);
    }


}
