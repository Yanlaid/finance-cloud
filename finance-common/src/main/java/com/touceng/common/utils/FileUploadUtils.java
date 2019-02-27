package com.touceng.common.utils;


import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.touceng.common.constant.TCConfigConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.response.EResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: 文件上传工具类
 * @createTime 2018/8/3 上午11:38
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
public class FileUploadUtils {


    /**
     * @methodDesc: 功能描述: 文件上传
     * @author Wu, Hua-Zheng
     * @createTime 2018/8/3 上午11:40
     * @version v1.0.0
     */
    public static String fileUpload(MultipartFile multipartFile, String fileType, String filePath) {

        //非空判断
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BusinessException(EResultEnum.FILE_NOT_EMPTY);
        }

        //文件类型判断
        try {
            //没有后缀名直接错误
            String currFileType = TCFileUtils.getFileType(multipartFile.getOriginalFilename());
            if (ValidatorToolUtils.isNullOrEmpty(currFileType)) {
                throw new BusinessException(EResultEnum.FILE_FORMAT_ERROR);
            }

            //指定类型判断
            if (ValidatorToolUtils.isNotNullOrEmpty(fileType) && !fileType.equalsIgnoreCase(currFileType)) {
                throw new BusinessException(EResultEnum.FILE_FORMAT_ERROR);
            }

        } catch (Exception e) {
            log.error("[FileUploadUtils-fileUpload异常]-{}", e);
            throw new BusinessException(EResultEnum.FILE_FORMAT_ERROR);
        }

        //文件路径配置
        log.info("上传:name={},type={},保存路径={}", multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                TCConfigConstant.file_path_root + filePath);

        //文件保存
        try {
            filePath = filePath + TCFileUtils.saveFile(multipartFile, TCConfigConstant.file_path_root + filePath);
        } catch (IOException e) {
            log.error("[FileUploadUtils-fileUpload异常]-{}", e);
            throw new BusinessException(EResultEnum.SAVE_FILE_ERROE);
        }

        //返回文件路径
        return filePath;
    }
}
