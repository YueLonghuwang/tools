package com.rengu.tools7.controller;

import com.rengu.tools7.entity.ChunkEntity;
import com.rengu.tools7.entity.ResultEntity;
import com.rengu.tools7.service.FileService;
import com.rengu.tools7.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
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
 * 上传的文件的增删改查
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/file")
public class FileController {

    @Autowired
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    /**
     * 按照评价星星查询所有上传的文件
     * @return
     */
    @GetMapping("/findAll")
    public ResultEntity findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        return  ResultUtils.success(fileService.findAll(page,size));
    }


    /**
     * 按照下载数量排名
     * @return
     */
    @GetMapping("/KfindAll")
    public ResultEntity KfindAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        return  ResultUtils.success(fileService.KfindAll(page,size));
    }

    /**
     * 根据id删除上传的文件
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResultEntity deleteFile(String id ){
           fileService.delete(id);
        return ResultUtils.success("1");
    }

    /**
     * 根据id查询上传文件
     * @param id 文件id
     * @return 上传的文件
     */
    @GetMapping("/findById")
    public ResultEntity findByIds(String id){
        return ResultUtils.success(fileService.findByIds(id));
    }
//
//    @GetMapping(value = "/chunks")
//    public void hasChunk(HttpServletResponse httpServletResponse, ChunkEntity chunkEntity) {
//        if (!fileService.hasChunk(chunkEntity)) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_GONE);
//        }
//    }

    /**
     * 上传文件
     * @param chunkEntity
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/chunks")
    public ResultEntity saveChunk(ChunkEntity chunkEntity, @RequestParam(value = "file") MultipartFile multipartFile, String poolId, String versionNumber, String versionContent) throws IOException {
        fileService.saveChunk(chunkEntity, multipartFile);
        fileService.mergeChunks(chunkEntity,poolId,versionNumber,versionContent);
        return ResultUtils.success("ok");
    }

    /**
     * 根据md5查询文件是否存在
     * @param MD5
     * @return
     */
    @GetMapping(value = "/hasmd5")
    public ResultEntity hasFileByMD5(@RequestParam(value = "MD5") String MD5) {
        return ResultUtils.success(fileService.hasFileByMD5(MD5) ? fileService.getFileByMD5(MD5) : fileService.hasFileByMD5(MD5));
    }

    /**
     * 修改文件状态
     *
     */
    @PostMapping("/state")
    public ResultEntity state(String id ,String state){
      fileService.state(id,state);
      return ResultUtils.success("1");
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
        File exportFile = fileService.exportComponentFileById(id);
        String mimeType = URLConnection.guessContentTypeFromName(exportFile.getName()) == null ? "application/octet-stream" : URLConnection.guessContentTypeFromName(exportFile.getName());
        httpServletResponse.setContentType(mimeType);
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(exportFile.getName().getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        httpServletResponse.setContentLengthLong(exportFile.length());
        // 文件流输出
        IOUtils.copy(new FileInputStream(exportFile), httpServletResponse.getOutputStream());
        httpServletResponse.flushBuffer();
        fileService.downloadStatistics(id);
        return  ResultUtils.success("1");
    }





}