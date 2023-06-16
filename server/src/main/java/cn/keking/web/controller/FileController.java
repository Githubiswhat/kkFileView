package cn.keking.web.controller;

import cn.keking.bean.ShortLink;
import cn.keking.config.ConfigConstants;
import cn.keking.model.ReturnResponse;
import cn.keking.service.shorturl.ShortLinkServiceImpl;
import cn.keking.utils.KkFileUtils;
import cn.keking.utils.RarUtils;
import cn.keking.utils.URLUtil;
import cn.keking.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.codec.binary.Base64;

/**
 * @author yudian-it
 *  2017/12/1
 */
@RestController
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String fileDir = ConfigConstants.getFileDir();
    private final String demoDir = "demo";
    private final String demoPath = demoDir + File.separator;
    public static final String BASE64_DECODE_ERROR_MSG = "Base64解码失败，请检查你的 %s 是否采用 Base64 + urlEncode 双重编码了！";

    @Autowired
    ShortLinkServiceImpl shortLinkService;

    @PostMapping("/fileUpload")
    public ReturnResponse<Object> fileUpload(@RequestParam("file") MultipartFile file) {
        ReturnResponse<Object> checkResult = this.fileUploadCheck(file);
        if (checkResult.isFailure()) {
            return checkResult;
        }
        File outFile = new File(fileDir + demoPath);
        if (!outFile.exists() && !outFile.mkdirs()) {
            logger.error("创建文件夹【{}】失败，请检查目录权限！", fileDir + demoPath);
        }
        String fileName = checkResult.getContent().toString();
        logger.info("上传文件：{}{}{}", fileDir, demoPath, fileName);
        try (InputStream in = file.getInputStream(); OutputStream out = Files.newOutputStream(Paths.get(fileDir + demoPath + fileName))) {
            StreamUtils.copy(in, out);
            return ReturnResponse.success(null);
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            return ReturnResponse.failure();
        }
    }

    @GetMapping("/deleteFile")
    public ReturnResponse<Object> deleteFile(String fileName,String password) {
        ReturnResponse<Object> checkResult = this.deleteFileCheck(fileName);
        if (checkResult.isFailure()) {
            return checkResult;
        }
         fileName = checkResult.getContent().toString();
        if(!ConfigConstants.getPassword().equalsIgnoreCase(password)) {
            logger.error("删除文件【{}】失败，密码错误！",fileName);
            return ReturnResponse.failure("删除文件失败，密码错误！");
        }
        File file = new File(fileDir + demoPath + fileName);
        logger.info("删除文件：{}", file.getAbsolutePath());
        if (file.exists() && !file.delete()) {
            String msg = String.format("删除文件【%s】失败，请检查目录权限！", file.getPath());
            logger.error(msg);
            return ReturnResponse.failure(msg);
        }
        return ReturnResponse.success();
    }

    @GetMapping("/listFiles/**")
    public List<Map<String, String>> getFiles(HttpServletRequest request) {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        List<Map<String, String>> list = new ArrayList<>();
        String demoPath;
        if (path.equals("/listFiles")){
            path = path.substring("/listFiles".length());
        }else {
            path = path.substring("/listFiles/".length());
        }
        if (path != null && !path.equals("") && !path.equals("/")) {
            demoPath = path;
        } else {
            demoPath = "";
        }
        File file = new File(fileDir + demoPath);
        if (file.exists()) {
            File[] files = Objects.requireNonNull(file.listFiles());
            Arrays.sort(files, (f1, f2) -> {
                if (f1.isDirectory() && !f2.isDirectory()) {
                    return -1; // f1排在f2前面
                } else if (!f1.isDirectory() && f2.isDirectory()) {
                    return 1; // f1排在f2后面
                } else {
                    return f1.getName().compareTo(f2.getName()); // 目录和文件类型相同，按名字排序
                }
            });
            Arrays.stream(files).forEach(file1 -> {
            Map<String, String> fileName = new HashMap<>();
                fileName.put("fileName", file1.getName());
                fileName.put("path", demoPath);
                fileName.put("isDirectory", String.valueOf(file1.isDirectory()));
                if (!file1.isDirectory()) {
                    String url = URLUtil.getUrlStart(request);
                    String longUrl = url + "onlinePreview?url=" + URLEncoder.encode(Base64.encodeBase64String((url + demoPath + "/" + file1.getName()).getBytes()));
                    fileName.put("shortUrl", addShortUrl(longUrl, url));
                }else {
                    fileName.put("shortUrl", "");
                }
                list.add(fileName);
            });
            if (demoPath != ""){
                Map<String, String> prevDirectory = new HashMap<>();
                prevDirectory.put("fileName", "..");
                prevDirectory.put("path", !demoPath.contains("/") ? "" : demoPath.substring(0, demoPath.lastIndexOf("/")));
                prevDirectory.put("isDirectory", "true");
                list.add(0, prevDirectory);
            }
        }
        return list;
    }

    private String addShortUrl(String longLink, String url){
        ShortLink shortLink = shortLinkService.addShortLink(longLink, url);
        return shortLink.getShortLink();
    }

    /**
     * 上传文件前校验
     *
     * @param file 文件
     * @return 校验结果
     */
    private ReturnResponse<Object> fileUploadCheck(MultipartFile file) {
        if (ConfigConstants.getFileUploadDisable()) {
            return ReturnResponse.failure("文件传接口已禁用");
        }
        String fileName = WebUtils.getFileNameFromMultipartFile(file);
        if(fileName.lastIndexOf(".")==-1){
            return ReturnResponse.failure("不允许上传的类型");
        }
        if (!KkFileUtils.isAllowedUpload(fileName)) {
            return ReturnResponse.failure("不允许上传的文件类型: " + fileName);
        }
        if (KkFileUtils.isIllegalFileName(fileName)) {
            return ReturnResponse.failure("不允许上传的文件名: " + fileName);
        }
        // 判断是否存在同名文件
        if (existsFile(fileName)) {
            return ReturnResponse.failure("存在同名文件，请先删除原有文件再次上传");
        }
        return ReturnResponse.success(fileName);
    }


    /**
     * 删除文件前校验
     *
     * @param fileName 文件名
     * @return 校验结果
     */
    private ReturnResponse<Object> deleteFileCheck(String fileName) {
        if (ObjectUtils.isEmpty(fileName)) {
            return ReturnResponse.failure("文件名为空，删除失败！");
        }
        try {
            fileName = WebUtils.decodeUrl(fileName);
        } catch (Exception ex) {
            String errorMsg = String.format(BASE64_DECODE_ERROR_MSG, fileName);
            return ReturnResponse.failure(errorMsg + "删除失败！");
        }
        assert fileName != null;
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        if (KkFileUtils.isIllegalFileName(fileName)) {
            return ReturnResponse.failure("非法文件名，删除失败！");
        }
        return ReturnResponse.success(fileName);
    }

    @GetMapping("/directory")
    public Object directory(String urls) {
        String fileUrl;
        try {
            fileUrl = WebUtils.decodeUrl(urls);
        } catch (Exception ex) {
            String errorMsg = String.format(BASE64_DECODE_ERROR_MSG, "url");
            return ReturnResponse.failure(errorMsg);
        }
        if (KkFileUtils.isIllegalFileName(fileUrl)) {
            return ReturnResponse.failure("不允许访问的路径:");
        }
        return RarUtils.getTree(fileUrl);
    }

    private boolean existsFile(String fileName) {
        File file = new File(fileDir + demoPath + fileName);
        return file.exists();
    }
}