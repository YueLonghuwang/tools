package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ChunkEntity;
import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.service.FileFruitService;
import com.rengu.tools7.utils.ResultUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
/**
 * @author 徐子钦
 * @date 2020/5/19
 * 文件的成果的增删改查 和下载
 */
@RestController
@RequestMapping(value = "/api/v1/fileFruit")
/**
 * 文件成果
 */
public class FileFruitController {


    @Autowired
    private FileFruitService fileFruitService;
    /**
     * 上传文件成果
     * @param chunkEntity
     * @param multipartFile
     * @param userId
     * @param fileId
     * @param size
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/fruit")
    public ResultEntity uploadFruit(ChunkEntity chunkEntity, @RequestParam(value = "file") MultipartFile multipartFile, String userId, String fileId, String size) throws IOException {
        fileFruitService.saveChunk(chunkEntity, multipartFile);
        fileFruitService.mergeChunksFruit(chunkEntity,fileId,userId,size);
        return ResultUtils.success("ok");
    }

    /**
     * 修改成果状态码
     * @param id
     * @param state
     * @return
     */
    @PostMapping("/upload/state")
    public ResultEntity fruitstate(String id ,String state){
        fileFruitService.fruitstate(id,state);
        return ResultUtils.success("1");
    }


    /**
     *  判断状态码如果为私有则根据用户id和状态码查询数据
     * @param
     * @param userId
     * @return
     */
    @GetMapping("/find/private/fruit")
    public ResultEntity findPrivateFruit(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size, String scope, String userId, String poolId){
        return ResultUtils.success(fileFruitService.findPrivateFruit(page,size,scope,userId,poolId));
    }

    /**
     * 判断状态码如果为公有则根据状态码查询所有
     * @param state
     * @return
     */
    @GetMapping("/find/public/fruit")
    public ResultEntity findPublicFruit (@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size , String state, String fileId){
        return ResultUtils.success(fileFruitService.findPublicFruit(page,size,state,fileId));
    }

    /**
     * 根据id删除文件
     * @param id
     * @return
     */
    @DeleteMapping("/deleteFruit")
    public ResultEntity deleteFruit(String id){
        fileFruitService.deleteFruit(id);
        return ResultUtils.success("ok");
    }

    /**
     * 文件文件id 下载文件
     * @param id
     * @param httpServletResponse
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/{id}/export")
    public ResultEntity exportComponentFileById(@PathVariable(value = "id") String id, HttpServletResponse httpServletResponse) throws IOException {
        File exportFile = fileFruitService.exportComponentFileById(id);
        String mimeType = URLConnection.guessContentTypeFromName(exportFile.getName()) == null ? "application/octet-stream" : URLConnection.guessContentTypeFromName(exportFile.getName());
        httpServletResponse.setContentType(mimeType);
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(exportFile.getName().getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        httpServletResponse.setContentLengthLong(exportFile.length());
        // 文件流输出
        IOUtils.copy(new FileInputStream(exportFile), httpServletResponse.getOutputStream());
        httpServletResponse.flushBuffer();
      //  fileService.downloadStatistics(id);
        return  ResultUtils.success("1");
    }

}
