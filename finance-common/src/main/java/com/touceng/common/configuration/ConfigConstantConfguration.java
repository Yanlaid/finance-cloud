package com.touceng.common.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.touceng.common.constant.TCConfigConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 项目配置文件初始化
 * @createTime 2018年8月2日 下午9:21:14
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
@Component
@Order(value = 1)
public class ConfigConstantConfguration implements CommandLineRunner {

	/**
	 * 配置上传文件大小的配置
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个数据大小
		factory.setMaxFileSize("100Mb");
		/// 总上传数据大小
		factory.setMaxRequestSize("100Mb");
		return factory.createMultipartConfig();
	}

	@Override
	public void run(String... args) throws Exception {

		TCConfigConstant.server_port = server_port;// 项目端口号
		TCConfigConstant.project_name = project_name;// 项目名称

		TCConfigConstant.datacenter_code = datacenter_code;// 数据中心代码
		TCConfigConstant.machine_code = machine_code;// 机器标识代码
		TCConfigConstant.pool_num = pool_num;// 线程池数量

		TCConfigConstant.file_path_root = file_path_root;// 本地配置文件根
		TCConfigConstant.file_upload_path = file_upload_path;// 文件上传地址
		TCConfigConstant.file_download_path = file_download_path;// 文件下载地址
		TCConfigConstant.file_create_path = file_create_path;// 文件创建地址
		TCConfigConstant.http_prefix = http_prefix;// 文件访问地址前缀
		TCConfigConstant.http_finance_prefix = http_finance_prefix;// 账务系统访问地址前缀

		TCConfigConstant.kft_root_id = kft_root_id;// 快付通一级商户
		TCConfigConstant.kft_cert_type = kft_cert_type;// 证书类型
		TCConfigConstant.kft_cert_path = kft_cert_path;// 证书路径
		TCConfigConstant.kft_download_path = kft_download_path;// 下载路径
		TCConfigConstant.kft_keystore_password = kft_keystore_password;// 证书密码
		TCConfigConstant.kft_key_password = kft_key_password;// 证书容器密码
		TCConfigConstant.kft_service_ip = kft_service_ip;// 商户服务器IP(callerIp)
		TCConfigConstant.kft_server_version = kft_server_version;// 快付通添加二级商户版本
		TCConfigConstant.kft_service_secmerchant_add = kft_service_secmerchant_add;// 快付通添加二级商户接口名称
		TCConfigConstant.kft_server_url = kft_server_url;// 二级商户增加接口请求路径

		TCConfigConstant.bg_app_id = bg_app_id;// AppID标示配置
		TCConfigConstant.bg_check_point = bg_check_point;// checkPoint 接入点标示配置
		TCConfigConstant.bg_post_url = bg_post_url;// BigData接口请求路径

		log.info(">>>>>>>>>>>>>>>项目名称【{}】，端口号【{}】，项目配置加载完成 <<<<<<<<<<<<<", TCConfigConstant.project_name,
				TCConfigConstant.server_port);
	}

	@Value("${server.port}")
	private String server_port;// 项目端口号
	@Value("${custom.project_name}")
	private String project_name;// 项目名称

	@Value("${custom.datacenter_code}")
	public long datacenter_code;// 数据中心代码
	@Value("${custom.machine_code}")
	public long machine_code;// 机器标识代码
	@Value("${custom.pool_num}")
	public int pool_num;// 线程池数量

	@Value("${custom.file_path_root}")
	private String file_path_root;// 本地配置文件根
	@Value("${custom.file_upload_path}")
	private String file_upload_path;// 文件上传地址
	@Value("${custom.file_download_path}")
	private String file_download_path;// 文件下载地址
	@Value("${custom.file_create_path}")
	private String file_create_path;// 文件创建地址
	@Value("${custom.http_prefix}")
	private String http_prefix;// 文件访问地址前缀
	@Value("${custom.http_finance_prefix}")
	private String http_finance_prefix;// 账务系统访问地址前缀

	@Value("${kft.root_id}")
	private String kft_root_id;// 快付通一级商户
	@Value("${kft.cert_type}")
	private String kft_cert_type;// 证书类型
	@Value("${kft.cert_path}")
	private String kft_cert_path;// 证书路径
	@Value("${kft.download_path}")
	private String kft_download_path;// 下载路径
	@Value("${kft.keystore_password}")
	private String kft_keystore_password;// 证书密码
	@Value("${kft.key_password}")
	private String kft_key_password;// 证书容器密码
	@Value("${kft.service_ip}")
	private String kft_service_ip;// 商户服务器IP(callerIp)
	@Value("${kft.server_version}")
	private String kft_server_version;// 快付通添加二级商户版本
	@Value("${kft.service_secmerchant_add}")
	private String kft_service_secmerchant_add;// 快付通添加二级商户接口名称
	@Value("${kft.server_url}")
	private String kft_server_url;// 二级商户增加接口请求路径

	@Value("${bg.app_id}")
	private String bg_app_id;// AppID标示配置
	@Value("${bg.check_point}")
	private String bg_check_point;// checkPoint 接入点标示配置
	@Value("${bg.post_url}")
	private String bg_post_url;// BigData接口请求路径

}
