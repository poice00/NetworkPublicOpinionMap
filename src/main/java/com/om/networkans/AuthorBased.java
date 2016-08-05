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

import com.om.domain.DataSource;
import com.om.domain.Writer;
import com.om.service.DataSourceNoTagService;

/**
 * 主体对象社会网络的构建过程如下：
 * 第一步：确定领域内热点主题，构建分类主题词库。
 * 第二步：使用文本分类方法对领域内原帖进行分类。
 * 第三步：找到每一个主题对应帖子的发帖作者。
 * 第四步：建立发帖作者与具体主题的对应关系。
 * 第五步：通过可视化工具进行可视化。
 * @author ssy
 *
 */
public class AuthorBased {
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
	public void authorBasedAnalysis(){
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		List<String> retList = new ArrayList<String>();
		Set<String> rSet = new HashSet<String>();
		System.out.println("一共： "+ dataList.size()+" 篇");
		//找出经济领域下 
		for (DataSource data : dataList) {
			if(!data.getClusterResult().equals("其他")){
				retList.add(data.getWriter().getWriterName() +	"," + data.getClusterResult());
			}
		}
		System.out.println(retList.size());
		for (String s : retList) {
			rSet.add(s);
		}
		List<String> retList1 = new ArrayList<String>();
		List<String> retList2 = new ArrayList<String>();
		List<String> retList3 = new ArrayList<String>();
		for (String x : rSet) {
			if(x.split(",")[1].equals("股票")){
				retList1.add(x);
			}
			else if(x.split(",")[1].equals("房地产")){
				retList2.add(x);
			}
			else{
				retList3.add(x);
			}
		}
		System.out.println("股票 ：" + (double)retList1.size());
		System.out.println("房地产 ：" + (double)retList2.size());
		System.out.println("发展 ：" + (double)retList3.size());
		System.out.println("股票 ：" + (double)retList1.size()/rSet.size());
		System.out.println("房地产 ：" + (double)retList2.size()/rSet.size());
		System.out.println("发展 ：" + (double)retList3.size()/rSet.size());
		System.out.println(rSet.size());
		System.out.println((double)2/rSet.size());
		System.out.println((double)3/rSet.size());
//		writerToPath(rSet,"D:/data.txt");
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
	public static void main(String[] args) {
		new AuthorBased().authorBasedAnalysis();
	}
}
	
