package com.om.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.Comment;
import com.om.domain.DataSource;
import com.om.domain.User;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;
import com.om.service.CommentService;
import com.om.service.UserService;
import com.om.service.WriterFactorService;
import com.om.service.WriterService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class CommentServiceImpl extends DaoSupportImpl<Comment>implements CommentService {

	public List<Comment> getByWriter(Writer writer) {
		return (List<Comment>) getSession()//
				.createQuery(//
				"FROM Comment c where c.writerByWriterId = ?")//
				.setParameter(0, writer)//
				.list();
	}

	public List<Comment> getByTipReplyWriter(Writer writer) {
		return (List<Comment>) getSession()//
				.createQuery(//
				"FROM Comment c where c.writerByAtSomeone = ?")//
				.setParameter(0, writer)//
				.list();
	}

	@Override
	public List<Comment> getByTipReplyWriterName(String writername) {
		return (List<Comment>) getSession()//
				.createQuery(//
				"FROM Comment c where c.writerByAtSomeone.writerName = ?")//
				.setParameter(0, writername)//
				.list();
	}

	@Override
	public List<Comment> getByDataSource(DataSource dataSource) {
		return (List<Comment>) getSession()//
				.createQuery(//
				"FROM Comment c where c.dataSource = ?")//
				.setParameter(0, dataSource)//
				.list();
	}

}
