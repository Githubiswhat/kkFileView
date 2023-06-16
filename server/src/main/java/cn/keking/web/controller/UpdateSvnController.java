package cn.keking.web.controller;

import cn.keking.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


@RestController
public class UpdateSvnController {

    private final Logger logger = LoggerFactory.getLogger(UpdateSvnController.class);

    @GetMapping( "/updateSVN")
    public String go2Index(){
        String fileDir = ConfigConstants.getFileDir();
        String command = "cd " + fileDir + "; svn update;";
        StringBuilder sb = new StringBuilder("\n");
        try {
            System.out.println(command);
            logger.info(command);
            ProcessBuilder processBuilder = new ProcessBuilder();
            // 设置命令和参数
            processBuilder.command(Arrays.asList("bash", "-c", command));
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            int exitVal = process.waitFor();
            logger.info("命令结果：" + exitVal + sb.toString());
            return "更新SVN成功" + sb.toString();
        } catch (IOException | InterruptedException e) {
            logger.info("异常：" + e.toString());
            return "更新SVN失败" + e.toString();
        }
    }
}
