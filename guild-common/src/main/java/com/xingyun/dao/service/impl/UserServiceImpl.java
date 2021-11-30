package com.xingyun.dao.service.impl;

import com.xingyun.dao.entity.User;
import com.xingyun.dao.mapper.UserMapper;
import com.xingyun.dao.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xingyun
 * @since 2021-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
