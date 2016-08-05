package com.om.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.User;
import com.om.service.UserService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserServiceImpl extends DaoSupportImpl<User>implements UserService {


}
