package com.om.calEmotionFn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.om.domain.Comment;

public class DBManager {
	private final static String selectCom="select comment_id,comment_content from comment where state='Preprocess' and parent_id is null limit 0,200000 ;";
	private final static String updateCom="update comment set emotion_value=? , state='hasProcessed' where comment_id=? ;";
	private final static String updateCom1="update comment set state='beProcessed' where comment_id=? ;";


	public static List<Comment> getCommentList() {
		List<Comment> list=new ArrayList<Comment>();
		try {
			PreparedStatement pstmt = ConnectionManager.getConnection()
					.prepareStatement(selectCom);
			ResultSet resultSet = pstmt.executeQuery();
			Comment ds=null;
			while(resultSet.next()){
				ds=new Comment();
				String id=resultSet.getString(1);
				String comment_content=resultSet.getString("comment_content");
				ds.setCommentId(id);
				ds.setCommentContent(comment_content);
				list.add(ds);
			}
		}catch(Exception e){
			System.out.println("连接异常~~");
		}
		return list;
	}
	public static void updateCom(Comment com) {
		try {
			PreparedStatement pstmt = ConnectionManager.getConnection()
					.prepareStatement(updateCom);
			pstmt.setDouble(1,com.getEmotionValue());
			pstmt.setString(2,com.getCommentId());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			System.out.println("连接异常~~");
		}
	}
	public static void updateCom1(Comment com) {
		try {
			PreparedStatement pstmt = ConnectionManager.getConnection()
					.prepareStatement(updateCom1);
			pstmt.setString(1,com.getCommentId());
			pstmt.execute();
			pstmt.close();
		}catch(Exception e){
			System.out.println("连接异常~~");
		}
	}
}
