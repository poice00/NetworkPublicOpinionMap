package com.om.service;

import java.util.List;

import com.om.base.DaoSupport;
import com.om.domain.Comment;
import com.om.domain.DataSource;
import com.om.domain.User;
import com.om.domain.Writer;

public interface CommentService extends DaoSupport<Comment> {


	List<Comment> getByWriter(Writer writer);

	List<Comment> getByTipReplyWriter(Writer writer);

	List<Comment> getByTipReplyWriterName(String writername);

	List<Comment> getByDataSource(DataSource dataSource);
	
}
