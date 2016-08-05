package com.om.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.User;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;
import com.om.service.UserService;
import com.om.service.WriterFactorService;
import com.om.service.WriterService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class WriterServiceImpl extends DaoSupportImpl<Writer>implements WriterService {

}
