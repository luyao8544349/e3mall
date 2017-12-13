package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3.utils.FastDFSClient;
import cn.e3.utils.JsonUtils;
import cn.e3.utils.PicResult;
@Controller
public class UploadController {
	
	
	//注入图片服务地址
	@Value("${IMAGE_URL}")
	private String IMAGE_URL;
	
	/**
	 * 需求:fastdfs文件上传
	 * 请求:/pic/upload
	 * 参数:uploadFile
	 * 返回值:json对象PicResult
	 * //成功时
		{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
		}
		//失败时
		{
        "error" : 1,
        "message" : "错误信息"
		}
		json对象
		json字符串对象
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile){
		
		try {
			
			//获取文件名称
			String originalFilename = uploadFile.getOriginalFilename();
			//截取文件扩展名
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			
			//创建fastdfs工具类对象
			FastDFSClient fClient = new FastDFSClient("classpath:conf/client.conf");
			
			//上传
			//上传成功,返回文件存储路径:/group1/M00/00/00/wKhCQ1ovSVmASxIVAAvea_OGt2M978.jpg
			String url = fClient.uploadFile(uploadFile.getBytes(), extName, null);
			//组合图片服务器绝对地址 协议+ip+虚拟磁盘路径
			url = IMAGE_URL+url;
			
			//上传成功
			PicResult result = new PicResult();
			result.setError(0);
			result.setUrl(url);
			
			//把返回值对象转换成json字符串
			String picJson = JsonUtils.objectToJson(result);
			
			return picJson;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//上传失败
			PicResult result = new PicResult();
			result.setError(1);
			result.setMessage("上传失败");
			
			//把返回值对象转换成json字符串
			String picJson = JsonUtils.objectToJson(result);
			
			return picJson;
		}
		
	}
	

}
