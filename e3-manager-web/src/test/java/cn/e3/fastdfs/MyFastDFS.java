package cn.e3.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3.utils.FastDFSClient;

public class MyFastDFS {
	
	/**
	 * 需求:使用fastDFS客户端测试文件上传
	 * @throws Exception 
	 */
	@Test
	public void uploadPictest01() throws Exception{
		//指定连接服务器客户端配置文件client.conf 连接tracker服务
		String conf = "E:\\current_course\\maven\\temp\\workspace1\\e3mall\\e3-manager-web"
				+ "\\src\\main\\resources\\conf\\client.conf";
		//指定上传图片地址
		String pic = "C:\\Users\\luyao\\Desktop\\touxiang.jpg";
		//使用fastdfs api实现上传
		//加载client配置文件,连接tracker服务器
		ClientGlobal.init(conf);
		
		//创建tracker客户端对象
		TrackerClient tClient = new TrackerClient();
		//从tracker客户端对象中获取连接
		TrackerServer tServer = tClient.getConnection();
		
		StorageServer storageServer = null;
		//创建storage客户端对象
		StorageClient sClient = new StorageClient(tServer, storageServer);
		//使用存储客户端上传文件
		String[] urls = sClient.upload_file(pic, "jpg", null);
		//打印返回值
		for (String url : urls) {
			System.out.println(url);
		}
	}
	
	/**
	 * 需求:使用工具类fastDFS客户端测试文件上传
	 * @throws Exception 
	 */
	@Test
	public void uploadPictest02() throws Exception{
		
		//指定连接服务器客户端配置文件client.conf 连接tracker服务
		String conf = "E:\\current_course\\maven\\temp\\workspace1\\e3mall\\e3-manager-web"
				+ "\\src\\main\\resources\\conf\\client.conf";
		//指定上传图片地址
		String pic = "C:\\Users\\luyao\\Desktop\\touxiang.jpg";
		
		//创建工具类对象
		FastDFSClient fc = new FastDFSClient(conf);
		
		//上传
		String url = fc.uploadFile(pic);
		
		System.out.println(url);
		
	}

}
