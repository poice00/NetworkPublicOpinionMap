import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.om.domain.DataSource;
import com.om.domain.Datasource1;
import com.om.domain.Writer;
import com.om.service.DataSourceService;
import com.om.service.WriterService;


//import sun.net.www.protocol.gopher.GopherClient;

/**
 * Spring的单元测试
 * @author sys
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration
(locations = { "classpath*:applicationContext.xml", "classpath*:spring-context.xml" }) 
 //加载配置文件  
//-
public class BaseJunit4Test {
	@Resource
    ApplicationContext ac;
	
	@Resource
	private DataSourceService dataSourceService;
	
	@Test
	public void testSessionFactory() throws Exception {
		SessionFactory sf = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sf);
		
	}
	@Test
	public void testBeans() throws Exception {
		WriterService writerService = (WriterService) ac.getBean("writerServiceImpl");
		List<Writer> dataList = writerService.findAll();
		for (Writer data : dataList) {
			if(data.getFansNum()==null){
				data.setFansNum(0);
			}
			writerService.update(data);
		}
	}
	
	@Test
	public void DataSource(){
		DataSourceService dataSourceService = (DataSourceService) ac.getBean("dataSourceServiceImpl");
		List<DataSource> dataSources = dataSourceService.getByYearAndMonth(2014, 3);
		
		for (DataSource dataSource : dataSources) {
			System.out.println(dataSource.getContent());
		}
	}
	
	@Test
	public void test() throws Exception {
		System.out.println(Math.round(75.0));
	}
}
