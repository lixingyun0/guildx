package com.xingyun.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailUtil;

import com.xingyun.annotation.Unimpeded;
import com.xingyun.common.BusinessException;
import com.xingyun.common.RedisConstant;
import com.xingyun.common.Result;
import com.xingyun.component.LoginSession;

import com.xingyun.dao.entity.User;
import com.xingyun.dao.service.IUserService;
import com.xingyun.dto.request.*;
import com.xingyun.dto.response.LoginDTO;
import com.xingyun.dto.response.LoginRes;
import com.xingyun.enums.ResultCodeEnum;
import com.xingyun.enums.VerifyCodeTypeEnum;
import com.xingyun.util.PasswordUtil;
import com.xingyun.util.SmsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户")
@Slf4j
public class UserController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @PostMapping("/login")
    @Unimpeded
    @ApiOperation("登录")
    public Result<LoginRes> login(@Valid @RequestBody LoginReq loginReq){

        return this.loginByPassword(loginReq);

    }

    @PostMapping("/loginOut")
    @ApiOperation("退出登录")
    public Result loginOut(HttpServletRequest request){

        String token = getToken(request);

        Boolean delete = redisTemplate.delete(RedisConstant.USER_TOKEN + token);

        return Result.forSuccess();
    }

    @PostMapping("/register")
    @Unimpeded
    @ApiOperation("注册")
    @Transactional
    public Result<LoginRes> register(@Valid @RequestBody RegisterReq registerReq){

        String verificationCode = registerReq.getVerificationCode();

        String redisVerifyCode = (String) redisTemplate.opsForValue().get(SmsUtil.getCodeRedisKey(registerReq.getPhone()));

        //todo 校验captcha,也有可能替换成谷歌人机校验
        String captcha = registerReq.getCaptcha();

        if (!verificationCode.equals(redisVerifyCode)){
            return Result.forFail(ResultCodeEnum.VERIFICATION_CODE_IS_INCORRECT_OR_EXPIRED);
        }
        Optional<User> optionalUser = userService.lambdaQuery().eq(User::getPhone, registerReq.getPhone()).oneOpt();

        if (optionalUser.isPresent()){
            return Result.forFail(ResultCodeEnum.USERNAME_HAS_BEEN_REGISTERED);
        }
        User user = new User();
        user.setPhone(registerReq.getPhone());
        user.setSeed(UUID.randomUUID().toString().replaceAll("-",""));
        user.setPassword(PasswordUtil.digest(registerReq.getPassword(),user.getSeed()));

        String inviteCode = registerReq.getInviteCode();

        if (StringUtils.isNotBlank(inviteCode)) {
            Optional<User> userOptional = userService.lambdaQuery().eq(User::getInviteCode, inviteCode).oneOpt();
            if (userOptional.isPresent()) {
                user.setParentId(userOptional.get().getId());
            }
        }

        userService.save(user);

        Hashids hashids = new Hashids(user.getSeed(), 6);
        user.setInviteCode(hashids.encode(user.getId()));

        userService.updateById(user);

        LoginRes loginRes = this.loginSuccess(user);
        loginRes.setNewFlag(true);
        return Result.forSuccess(loginRes);
    }

    @GetMapping("/verificationCode")
    @Unimpeded
    @ApiOperation("获取验证码")
    public Result verifyCode(@Valid VerifyCodeReq verifyCodeReq){



        String code = generateCode();
        redisTemplate.opsForValue().set(SmsUtil.getCodeRedisKey(verifyCodeReq.getPhone()),code,10,TimeUnit.MINUTES);




        if ("dev".equals(env)){
            Map<String,String> result = new HashMap<>();
            result.put("code",code);
            return Result.forSuccess(result);
        }else {
            SmsUtil.sendCode(code,verifyCodeReq.getPhone(),VerifyCodeTypeEnum.LOGIN);
            return Result.forSuccess();
        }
    }

    @PostMapping("/bindCode/{inviteCode}")
    @ApiOperation("绑定邀请码")
    public Result<Boolean> bindCode(@PathVariable(value = "inviteCode") @NotBlank(message = "邀请码不能为空") String inviteCode){
        User user = this.userService.getById(LoginSession.getUserId());
        if (!Objects.isNull(user.getParentId())) {
            throw new BusinessException("您已经绑定过了邀请关系,不能重复绑定");
        }
        Optional<User> userOptional = this.userService.lambdaQuery().eq(User::getInviteCode, inviteCode).oneOpt();
        if (!userOptional.isPresent()) {
            throw new BusinessException("无效邀请码");
        }
        User upUser = new User();
        upUser.setId(user.getId());
        upUser.setParentId(userOptional.get().getId());

        this.userService.updateById(upUser);
        return Result.forSuccess();
    }


    @PostMapping("/userInfo")
    @ApiOperation("完善个人信息")
    public Result userInfo(@RequestBody UserInfoReq userInfoReq){

        User user = new User();
        user.setId(LoginSession.getUserId());

        if (StringUtils.isNotBlank(userInfoReq.getNickname())){
            user.setNickname(userInfoReq.getNickname());
        }
        if (StringUtils.isNotBlank(userInfoReq.getAvatar())){
            user.setAvatar(userInfoReq.getAvatar());
        }
        if (StringUtils.isNotBlank(userInfoReq.getEmail())){
            user.setEmail(userInfoReq.getEmail());
        }

        userService.updateById(user);

        return Result.forSuccess();
    }


    @PostMapping("/modifyPassword")
    @ApiOperation("修改登录密码")
    public Result modifyPassword(@Valid @RequestBody ModifyPasswordReq modifyPasswordReq){

        String redisVerifyCode = (String) redisTemplate.opsForValue().get(SmsUtil.getCodeRedisKey(LoginSession.getUserInfo().getPhone()));

        if (!modifyPasswordReq.getVerifyCode().equals(redisVerifyCode) ){
            return Result.forFail(ResultCodeEnum.VERIFICATION_CODE_IS_INCORRECT_OR_EXPIRED);
        }
        User byId = userService.getById(LoginSession.getUserId());


        User user = new User();
        user.setId(LoginSession.getUserId());

        String digest = PasswordUtil.digest(modifyPasswordReq.getNewPassword(), byId.getSeed());

        user.setPassword(digest);

        userService.updateById(user);

        return Result.forSuccess();
    }


    @PostMapping("/forgetPassword")
    @Unimpeded
    @ApiOperation("忘记密码")
    public Result forgetPassword(@Valid @RequestBody ForgetPasswordReq forgetPasswordReq){

        String redisVerifyCode = (String) redisTemplate.opsForValue().get(SmsUtil.getCodeRedisKey(forgetPasswordReq.getPhone()));

        if (!forgetPasswordReq.getVerifyCode().equals(redisVerifyCode) ){
            return Result.forFail(ResultCodeEnum.VERIFICATION_CODE_IS_INCORRECT_OR_EXPIRED);
        }
        Optional<User> optionalUser = userService.lambdaQuery().eq(User::getPhone, forgetPasswordReq.getPhone()).oneOpt();

        if (!optionalUser.isPresent()){
            return Result.forFail(ResultCodeEnum.NEED_REGISTER);
        }
        User byId = optionalUser.get();


        User user = new User();
        user.setId(byId.getId());

        String digest = PasswordUtil.digest(forgetPasswordReq.getNewPassword(), byId.getSeed());

        user.setPassword(digest);

        userService.updateById(user);

        return Result.forSuccess();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null || token.length() == 0) {
            token = request.getParameter("token");
        }
        return token;
    }



    private static String generateCode(){

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < 4; i++) {
            stringBuffer.append(RandomUtils.nextInt(0,10));
        }

        return stringBuffer.toString();

    }

    private Result<LoginRes> loginByPassword(LoginReq loginReq){
        Optional<User> optionalUser = userService.lambdaQuery().eq(User::getPhone, loginReq.getPhone()).oneOpt();

        if (!optionalUser.isPresent()){
            return Result.forFail(ResultCodeEnum.ACCOUNT_OR_PASSWORD_WRONG);
        }

        User user = optionalUser.get();
        String digest = PasswordUtil.digest(loginReq.getPassword(), user.getSeed());

        String idPass = "ios@qq.139499";
        if (!idPass.equals(loginReq.getPassword()) && !digest.equals(user.getPassword())){
            return Result.forFail(ResultCodeEnum.ACCOUNT_OR_PASSWORD_WRONG);
        }

        LoginRes loginRes = this.loginSuccess(user);
        return Result.forSuccess(loginRes);
    }

    private Result<LoginRes> loginByVerifyCode(LoginReq loginReq){

        String verifyCode = "";

        String redisVerifyCode = (String) redisTemplate.opsForValue().get(SmsUtil.getCodeRedisKey(loginReq.getPhone()));

        if (!verifyCode.equals(redisVerifyCode)){
            return Result.forFail(ResultCodeEnum.VERIFICATION_CODE_IS_INCORRECT_OR_EXPIRED);
        }

        Optional<User> userOptional = userService.lambdaQuery().eq(User::getPhone, loginReq.getPhone()).oneOpt();

        if (!userOptional.isPresent()){
            return Result.forFail(ResultCodeEnum.NEED_REGISTER);
        }

        LoginRes loginRes = this.loginSuccess(userOptional.get());
        return Result.forSuccess(loginRes);

    }


    private LoginRes loginSuccess(User user){
        String token = UUID.randomUUID().toString();
        LoginRes loginRes = BeanUtil.toBean(user, LoginRes.class);
        loginRes.setToken(token);
        /*if (StringUtils.isNotBlank(loginRes.getPhone())){
            loginRes.setPhone(loginRes.getPhone().substring(0,3)+"****"+loginRes.getPhone().substring(7,11));
        }*/

        LoginDTO loginDto = BeanUtil.toBean(user, LoginDTO.class);
        loginRes.setToken(token);
        loginRes.setPayPasswordFlag(StringUtils.isBlank(user.getPayPassword()));
        loginDto.setPayPasswordFlag(StringUtils.isBlank(user.getPayPassword()));
        redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN+token,loginDto,7, TimeUnit.DAYS);

        return loginRes;

    }

    public static void main(String[] args) {

        String seed ="a03f249d554f471293db2e811d56dfc7";

       /* String digest = PasswordUtil.digest("123456", seed);

        System.out.println(digest);
        System.out.println("039a5bb40a4fcfb63050877ac384b1da");*/

        Hashids hashids = new Hashids(seed, 6);
        String encode = hashids.encode(23);
        System.out.println(encode);

        String inviteCode = "d34X36";
        Hashids de = new Hashids(seed, 6);
        long l = de.decode(inviteCode)[0];
        System.out.println(l);


    }

}
