package com.om.networkans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.Comment;
import com.om.domain.DataSource;
import com.om.domain.Writer;
import com.om.service.CommentService;
import com.om.service.DataSourceNoTagService;

/**
 * 主体对象社会网络的构建过程如下：
 * 第一步：提取该主题下的热点事件，通过事件热度指标进行筛选。
 * 第二步：通过热点事件得到原始帖子以及相关评论。
 * 第三步：处理每一条评论。查询每一条评论预处理中提取出的@论坛用户列表，
 * 		如果列表为空，建立评论用户ID 和评论原帖发布者ID 之间的对应关系；
 * 		如果列表不为空，建立评论用户ID 和@的用户ID 之间的对应关系。
 * 第四步：将所有的对应关系写入Excel 文件，转存为csv 格式的文件。
 * 第五步：使用可视化工具gephi 进行可视化
 * @author ssy
 *
 */
public class UnqiuedAnalysis {
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
	Session session=null;
	Transaction tx=null;
	public void open(){
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	public void close(){
		tx.commit();
		session.close();
		ctx.close();
	}
	public void unqiuedAnalysis(){
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		CommentService commentService = (CommentService) ctx.getBean("commentServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByClusterResult("房地产");
//		List<DataSource> dataList = dataSourceNoTagService.getTopByHotNum("房地产", 5);
		Set<String> resultSet  = new HashSet<String>();
//		List<Writer> writerList = new ArrayList<Writer>();
		for (DataSource dataSource : dataList) {
//			System.out.println("发帖人： " + dataSource.getTitle() + "," + dataSource.getWriter().getWriterName() + "," + dataSource.getHotNum());
			List<Comment> commentList = commentService.getByDataSource(dataSource);
			System.out.println(dataSource.getTitle()+"下的评论一共: "+commentList.size());
			for (Comment comment : commentList) {
				if(comment.getWriterByAtSomeone()==null){
					String result = comment.getWriterByWriterId().getWriterName()+","+dataSource.getWriter().getWriterName();
//					System.out.println(result);
					resultSet.add(result);
				}else{
					String result = comment.getWriterByWriterId().getWriterName()+","+comment.getWriterByAtSomeone().getWriterName();
//					System.out.println(result);
					resultSet.add(result);
				}
			}
		}
		System.out.println("------------------结果--------------------");
		System.out.println("---------"+resultSet.size()+"-----------");
		for (String result : resultSet) {
			System.out.println(result);
		}
		writerToPath(resultSet,"D:/uniquedata.txt");
//			String origialName = writer.getWriterName();
//			replyerRecursion(origialName);
//			List<String> replyerList = showReplyer(origialName,origialName);
//			
//			for (String replyname : replyerList) {
//				replyerList = showReplyer(replyname,origialName);
//				for (String replyname1 : replyerList) {
//					replyerList = showReplyer(replyname1,origialName);
//					for (String replyname2 : replyerList) {
//						showReplyer(replyname2,origialName);
//					}
//				}
//			}
//		}
	}
	private void writerToPath(Set<String> rSet,String path) {
//		BufferedWriter bw = null;
	    System.out.println("开始写入文件。。");
	    
		File sourceFile = new File(path);//创建文件
		try {
			sourceFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileWriter fr = null;
		try {
			fr = new FileWriter(sourceFile);
//			bw = new BufferedWriter(fr);
//			fr.write("用户" + "," + "主题" + "\r\n");
			for (String ret : rSet) {
				fr.write(ret+"\r\n");
				System.out.println(ret);
			}
//			bw.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("写入完毕！");
		
	}
	/**
	 * 
	 * @param writername 当前需要计算回复的人
	 * @param origialName 初始的发帖人
	 * @return
	 */
	private List<String> showReplyer(String writername,String origialName) {
		CommentService commentService = (CommentService) ctx.getBean("commentServiceImpl");
		List<Comment> commentList = commentService.getByTipReplyWriterName(writername);
		List<String> nameList = showIt(commentList,writername,origialName);
		return nameList;
		
		
	}
	private List<String> showIt(List<Comment> commentList,String writername,String origialName) {
		Set<String> nameSet  = new HashSet<String>();
		List<String> nameList  = new ArrayList<String>();
		for (Comment comment : commentList) {
			if(!origialName.equals(comment.getWriterByWriterId().getWriterName()))
				nameSet.add(comment.getWriterByWriterId().getWriterName());
		}
		for (String name : nameSet) {
			nameList.add(name);
			System.out.println( name +" 回复了： " + writername);
		}
		return nameList;
		
	}
	public static void main(String[] args) {
		new UnqiuedAnalysis().unqiuedAnalysis();
	}
}
	
