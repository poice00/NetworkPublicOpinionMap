package com.om.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.om.domain.DataSource;
import com.om.service.DataSourceNoTagService;
import com.om.service.DataSourceService;
import com.om.util.NLPIR.CLibrary;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/home")
public class HomeController  {
	
	@Resource
	private DataSourceNoTagService dataSourceNoTagService;
	@Resource
	private DataSourceService dataSourceService;
	
	/** 分类 */
	@RequestMapping("/classify")
	public String list(Model model,HttpServletRequest request) throws Exception {
		/*Map<String, Double> maps = new HashMap<String,Double>();
		String zz = "政治";
		String hb = "生态";
		String jj = "经济";
		String js = "军事";
		String sh = "社会";
		List<String> typeList = new ArrayList<String>();
		typeList.add(zz);
		typeList.add(hb);
		typeList.add(jj);
		typeList.add(js);
		typeList.add(sh);
		List<DataSource> dataSoureList = this.dataSourceNoTagService.findAll();
		double totalSize = dataSoureList.size();
		for (String type : typeList) {
			List<DataSource> typeDataList = this.dataSourceNoTagService.getByName(type);
			System.out.println(type + "共有: " + typeDataList.size() +" 篇");
			double re = typeDataList.size()/totalSize;
			double x = re*100;
			BigDecimal b = new BigDecimal(x);  
			double ret = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  	
			maps.put(type,ret);
		}
		for (String a : maps.keySet()) {
			System.out.print(a + ":" + maps.get(a));
			System.out.println();
		}
		//写入json
		String str = convertToJson(maps);
		writeToPath(str,"F:\\eclipse_workspace_J2EE\\NetworkPublicOpinionMap\\src\\main\\webapp\\data\\classify.json");*/
		String str = getTByPath(request.getSession().getServletContext().getRealPath("/") + "data/classify.json");
		model.addAttribute("classify", str);
		
		//详细数据
		/*Map<String, Double> maps = new HashMap<String,Double>();
		List<DataSource> typeDataList = this.dataSourceNoTagService.getByName("经济");
		double totalSize = typeDataList.size();
		Set<String> typeSet = new HashSet<String>();
		for (DataSource dataSource : typeDataList) {
			typeSet.add(dataSource.getClusterResult());
		}
		System.out.println(typeSet);
		for (String type : typeSet) {
			List<DataSource> dataList = this.dataSourceNoTagService.getByClusterResult(type);
			System.out.println(type + "共有: " + dataList.size() +" 篇");
			double re = dataList.size()/totalSize;
			double x = re*100;
			BigDecimal b = new BigDecimal(x);  
			double ret = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  	
			maps.put(type,ret);
		}
		System.out.println(maps);
		String detailStr = convertdetail(maps);
		writeToPath(detailStr,"F:\\eclipse_workspace_J2EE\\NetworkPublicOpinionMap\\src\\main\\webapp\\data\\detail.txt");*/
		
		String detailStr = getTByPath(request.getSession().getServletContext().getRealPath("/") + "data/detail.txt");
		model.addAttribute("detail", detailStr);
		return "/home/classify";
	}

	private String getTByPath(String path) {
		String str  = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String in;
			while((in=br.readLine())!=null){
				str += in;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(str);
		return str;
		
	}

	private String convertToJson(Map<String, Double> maps) {
		String str = "";
		str="[";
		int length=maps.size();
		for (String word:maps.keySet()) {
			if (length>1) {
				str+="{ name: \""+word+"\", y: "+maps.get(word)+",drilldown: \""+word+"\" },";
				length=length-1;
			} else {
				str+="{ name: \""+word+"\", y: "+maps.get(word)+",drilldown: \""+word+"\" }";
			}
		}
		str+="]";
		return str;
	}
	private String convertdetail(Map<String, Double> maps) {
		String str = "";
		str="[";
		int length=maps.size();
		for (String word:maps.keySet()) {
			if (length>1) {
				str+="[\""+word+"\","+maps.get(word)+"],";
				length=length-1;
			} else {
				str+="[\""+word+"\","+maps.get(word)+"]";;
			}
		}
		str+="]";
		return str;
	}

	@SuppressWarnings("unused")
	private void writeToPath(String str, String path) throws IOException {
		BufferedWriter bw = null;
	    System.out.println("开始写入JSon");
	    
		File sourceFile = new File(path);//创建文件
		sourceFile.createNewFile();
		try {
			FileWriter fr = new FileWriter(sourceFile);
			bw = new BufferedWriter(fr);
			fr.write(str);
			bw.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("写入完毕！");
		
	}
	
	/** 主页 */
	@RequestMapping("/index")
	public String index() throws Exception {
		return "/home/home" ;
	}
	/** 网络图 */
	@RequestMapping("/networkAnalysis")
	public String networkAnalysis() throws Exception {
		return "/home/networkAnalysis" ;
	}
	/** 专题传播网络*/
	@RequestMapping("/uniqueNetworkAnalysis")
	public String uniqueNetworkAnalysis() throws Exception {
		return "/home/uniqueNetworkAnalysis" ;
	}
	/** 专题热帖*/
	@RequestMapping("/topicHot")
	public String topicHot(Model model) throws Exception {
		List<DataSource> topicHotList = this.dataSourceNoTagService.getTopByHotNum("房地产", 5);//
		model.addAttribute("topicHot", topicHotList);
		return "/home/topicHot" ;
	}
	

	@RequestMapping("/wordFre/{year}/{month}")
	public String wordFrequency(@PathVariable("year")String year, @PathVariable("month")String month, Model model) throws UnsupportedEncodingException{
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		
		List<DataSource> newList = dataSourceService.getByYearAndMonth(y, m);
		
		if(0 == newList.size())
			return "";
		
		String argu = "E:\\eclipse\\NetworkPublicOpinionMap";
		// String system_charset = "GBK";//GBK----0
		String system_charset = "GBK";
		int charset_type = 1;
		// int charset_type = 0;
		// 调用printf打印信息
		int init_flag;
		init_flag = CLibrary.Instance.NLPIR_Init(argu
				.getBytes(system_charset), charset_type, "1"
				.getBytes(system_charset));

		if (0 == init_flag) {
			System.err.println("初始化失败！");
			return "";
		}
		
		
		/*=======================================词频统计=======================================*/
		
		HashMap<String, Integer> segaments = new HashMap<>();	//分词词频统计
		HashMap<String, Integer> keyWords = new HashMap<>();	//关键词词频统计
		
		/*存储分词和关键词提取结果*/
		String analyResult;
		String[] array;
		
		/**
		 * 以词性标记为分隔符分隔分词结果，
		 * 分隔结果里包含词性标记
		 */
		String regEx = "/[a-z0-9]+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher;
		
		for (DataSource dataSource : newList) {
			try {
				analyResult = CLibrary.Instance.NLPIR_ParagraphProcess(dataSource.getContent(), 1);
				matcher = pattern.matcher(analyResult);
				
				array = analyResult.split(regEx);
				for (String str : array) {
					str = str.trim();
					/*把词性标记追加在分词后面*/
					if(matcher.find())
						str += matcher.group();
					
					if(segaments.keySet().contains(str))
						segaments.put(str,segaments.get(str) + 1);
					else
						segaments.put(str, 1);
				}
				
				analyResult = CLibrary.Instance.NLPIR_GetKeyWords(dataSource.getContent(), 10, false);
				array = analyResult.split("#");
				for (String str : array) {
					str = str.trim();
					if(keyWords.keySet().contains(str))
						keyWords.put(str, keyWords.get(str) + 1);
					else
						keyWords.put(str, 1);
				}
				
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		
		CLibrary.Instance.NLPIR_Exit();
		
//		int count = 0;
//		for (String key : segaments.keySet()) {
//			System.out.print(key + ":" + segaments.get(key) + "   ");
//			
//			if(10 == ++count) {
//				count -= 10;
//				System.out.println();
//			}
//		}
		
		/*分别筛选出名称、动词、形容词词频的前十个*/
		HashMap<String, Integer> noun = new HashMap<>();
		HashMap<String, Integer> verb = new HashMap<>();
		HashMap<String, Integer> adjective = new HashMap<>();
		
		int index = 0;
		char ch = ' ';
		for (String key : segaments.keySet()) {
			index = key.indexOf('/');
			if(-1 == index) continue;
			
			ch = key.substring(index + 1, index + 2).toCharArray()[0]; 
			
			switch (ch) {
			case 'n':
				if (10 > noun.size())
					noun.put(key, segaments.get(key));
				else {
					for (String str : noun.keySet())
						if (segaments.get(key) > noun.get(str)) {
							noun.remove(str);
							noun.put(key, segaments.get(key));
							break;
						}
				}
				break;
			case 'v':
				if (10 > verb.size())
					verb.put(key, segaments.get(key));
				else {
					for (String str : verb.keySet())
						if (segaments.get(key) > verb.get(str)) {
							verb.remove(str);
							verb.put(key, segaments.get(key));
							break;
						}
				}
				break;
			case 'a':
				if (10 > adjective.size())
					adjective.put(key, segaments.get(key));
				else {
					for (String str : adjective.keySet())
						if (segaments.get(key) > adjective.get(str)) {
							adjective.remove(str);
							adjective.put(key, segaments.get(key));
							break;
						}
				}
				break;
			default:
				break;
			}
		}
		
		/*对HashMap按value排序*/
		Set<Entry<String, Integer>> es = noun.entrySet();
		List<Entry<String, Integer>> listNoun = new ArrayList<Map.Entry<String,Integer>>(es);
		List<Entry<String, Integer>> listVerb = new ArrayList<Map.Entry<String,Integer>>(verb.entrySet());
		List<Entry<String, Integer>> listAdjective = new ArrayList<Map.Entry<String,Integer>>(adjective.entrySet());
		
		Collections.sort(listNoun, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
				if(o1.getValue() > o2.getValue())
					return -1;
				else if(o1.getValue() < o2.getValue())
					return 1;
				
				return 0;
			}
		});
		
		StringBuilder sbKey = new StringBuilder("[");
		StringBuilder sbValue = new StringBuilder("[");
		String temp;
		for (int i = listNoun.size() - 1; i >= 0; i--) {
			temp = listNoun.get(i).getKey();
			temp = temp.substring(0, temp.indexOf('/'));
			
			sbKey.append("'").append(temp).append("',");
			sbValue.append(listNoun.get(i).getValue()).append(",");
		}
		sbKey.replace(sbKey.length() - 1, sbKey.length(), "");
		sbValue.replace(sbValue.length() - 1, sbValue.length(), "");
		sbKey.append("]");
		sbValue.append("]");
		
		model.addAttribute("nounKey",sbKey.toString());
		model.addAttribute("nounValue",sbValue.toString());
		
		Collections.sort(listVerb, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
				if(o1.getValue() > o2.getValue())
					return -1;
				else if(o1.getValue() < o2.getValue())
					return 1;
				
				return 0;
			}
		});
		
		sbKey = new StringBuilder("[");
		sbValue = new StringBuilder("[");
		for (int i = listVerb.size() - 1; i >= 0; i--) {
			temp = listVerb.get(i).getKey();
			temp = temp.substring(0, temp.indexOf('/'));
			
			sbKey.append("'").append(temp).append("',");
			sbValue.append(listVerb.get(i).getValue()).append(",");
		}
		sbKey.replace(sbKey.length() - 1, sbKey.length(), "");
		sbValue.replace(sbValue.length() - 1, sbValue.length(), "");
		sbKey.append("]");
		sbValue.append("]");
		
		model.addAttribute("verbKey",sbKey.toString());
		model.addAttribute("verbValue",sbValue.toString());
		
		Collections.sort(listAdjective, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
				if(o1.getValue() > o2.getValue())
					return -1;
				else if(o1.getValue() < o2.getValue())
					return 1;
				
				return 0;
			}
		});
		
		sbKey = new StringBuilder("[");
		sbValue = new StringBuilder("[");
		for (int i = listAdjective.size() - 1; i >= 0; i--) {
			temp = listAdjective.get(i).getKey();
			temp = temp.substring(0, temp.indexOf('/'));
			
			sbKey.append("'").append(temp).append("',");
			sbValue.append(listAdjective.get(i).getValue()).append(",");
		}
		sbKey.replace(sbKey.length() - 1, sbKey.length(), "");
		sbValue.replace(sbValue.length() - 1, sbValue.length(), "");
		sbKey.append("]");
		sbValue.append("]");
		
		model.addAttribute("adjectiveKey",sbKey.toString());
		model.addAttribute("adjectiveValue",sbValue.toString());
		
		/*==========================================关键词提取==========================================*/
		StringBuilder sb = new StringBuilder();
		
		/*{
            name: "Sam S Club",
            value: 10000,
            itemStyle: createRandomItemStyle()
        }*/
		for (String key: keyWords.keySet()) {
			
			if(1 == keyWords.get(key)) continue;
			
			sb.append("{")
			  .append("name:'").append(key).append("',")
			  .append("value:").append(keyWords.get(key) * 1000).append(",")
			  .append("itemStyle: createRandomItemStyle()")
			  .append("},");
		}
		
		sb.replace(sb.length() - 1, sb.length(), "");
		model.addAttribute("keyWords",sb.toString());
		
		/*============================================== 实体抽取 ==============================================*/
		/*乱码，回头仔细研究*/
//		String url = "http://api.ltp-cloud.com/analysis";
//		String param = "api_key=p5e197h0CANvVHmzOP4rNbFPizSXxQybrmnb7QRh&text=" 
//				+ URLEncoder.encode(newList.get(0).getContent(),"utf-8") + "&pattern=ner&format=plain&only_ner=true";
//		
//		System.out.println(sendPost(url, param));
		
		return "/home/word_frequency"; 
	}

	/**地图1*/
	@RequestMapping("/map")
	public String map(Model model) throws Exception {
		return "/home/map" ;
	}

	/**
	 * @desc 发送get请求
	 * @param url 请求url
	 * @param param 参数
	 * @return 响应结果
	 * @author yanbaobin@yeah.net
	 * @date Dec 1, 2015 1:36:53 AM
	 */
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
    
	/**地图2*/
	@RequestMapping("/map2")
	public String map2(Model model) throws Exception {
		return "/home/map2" ;
	}
	
	
}




