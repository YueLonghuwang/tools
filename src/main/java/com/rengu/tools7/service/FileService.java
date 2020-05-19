package com.rengu.tools7.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rengu.tools7.entity.*;
import com.rengu.tools7.enums.SystemErrorEnum;
import com.rengu.tools7.exception.SystemException;
import com.rengu.tools7.mapper.*;
import com.rengu.tools7.utils.ApplicationConfig;
import com.rengu.tools7.utils.ApplicationMessages;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-24 11:03
 **/

@Slf4j
@Service
@Transactional
public class FileService {

   @Autowired
    private FileMapper fileMapper;
   @Autowired
   private ToolsBaseinfoMapper toolsBaseinfoMapper;
   @Autowired
   private ToolsstorageMapper toolsstorageMapper;
   @Autowired
   private VersionManagementMapper versionManagementMapper;
   @Autowired
   private ClassifiedManagementMapper classifiedManagementMapper;
   @Autowired
   private  UpDownLoadManagementMapper upDownLoadManagementMapper;
   @Autowired
   private ToolsevaluateMapper toolsevaluateMapper;
   @Autowired
   private ToolsdatastorageMapper toolsdatastorageMapper;
    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // 保存文件块
    public void saveChunk(ChunkEntity chunkEntity, MultipartFile multipartFile) throws IOException {
        File chunk = new File(ApplicationConfig.CHUNKS_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + File.separator + chunkEntity.getChunkNumber() + ".tmp");
       // chunkEntity.setId(UUID.randomUUID().toString());
      //  chunkEntity.setCreateTime(new ChunkEntity().getCreateTime());
      //  fileMapper.add(chunkfruitstateEntity);
        if (chunkEntity==null){
             throw  new SystemException(SystemErrorEnum.CUNKENTITY_IS_NULL);
        }
        if (multipartFile==null){
             throw new SystemException(SystemErrorEnum.FIlE_IS_NULL);
        }
        chunk.getParentFile().mkdirs();
        chunk.createNewFile();
        IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(chunk));

    }


    /**
     * 添加工具
     * @param toolsBaseinfoEntity
     */
    public void addPool( tb_ToolsBaseinfoEntity toolsBaseinfoEntity) {
        if (toolsBaseinfoEntity==null){
            throw  new SystemException(SystemErrorEnum.TB_TOOLSBASEINFOENTITY_IS_NULL);
        }
       // tb_ToolsBaseinfoEntity  toolsBaseinfoEntity=new tb_ToolsBaseinfoEntity();
        toolsBaseinfoEntity.setId(UUID.randomUUID().toString());
//        toolsBaseinfoEntity.setScope(scope);
//        toolsBaseinfoEntity.setCopyright(copyright);
//        toolsBaseinfoEntity.setSummarize(summarize);
//        toolsBaseinfoEntity.setUpdateTime(date);
//        toolsBaseinfoEntity.setProvider(provider);
//        toolsBaseinfoEntity.setDevelopers(developers);
//        toolsBaseinfoEntity.setDescribe(describe);
//        toolsBaseinfoEntity.setScore(grade);
//        toolsBaseinfoEntity.setTypeId(typeId);
        toolsBaseinfoMapper.save(toolsBaseinfoEntity);

    }


    /**
     * 检查文件块是否存在
     * @param chunkEntity
     * @return
     */
    public boolean hasChunk(ChunkEntity chunkEntity) {
        if (chunkEntity==null){
            throw new SystemException(SystemErrorEnum.CUNKENTITY_IS_NULL);
        }
        File chunk = new File(ApplicationConfig.CHUNKS_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + File.separator + chunkEntity.getChunkNumber() + ".tmp");
        return chunk.exists() && chunkEntity.getChunkSize() == FileUtils.sizeOf(chunk);
    }

    /**
     * 根据Id判断文件是否存在
     * @param fileId
     * @return
     */
    public boolean hasFileById(String fileId) {
        if (fileId ==null){
            throw new SystemException(SystemErrorEnum.FILE_ID_ISEMPTY);
        }
        if (StringUtils.isEmpty(fileId)) {
            return false;
        }
       if (fileMapper.findById(fileId).equals(null)){
            return false;
       }else{
           return  true;
       }
    }

    /**
     * 根据Md5判断文件是否存在
     * @param MD5
     * @return
     */
    public boolean hasFileByMD5(String MD5) {
        if (MD5==null){
            throw new SystemException(SystemErrorEnum.MD5_IS_NULL);
        }
        if (StringUtils.isEmpty(MD5)) {
            return false;
        }
        if (fileMapper.findByMD5(MD5)==null){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 根据Id查询文件
     * @param fileId
     * @return
     */
    @Cacheable(value = "File_Cache", key = "#fileId")
    public FileEntity getFileById(String fileId) {
        if (fileId==null){
            throw new SystemException(SystemErrorEnum.FILE_ID_ISEMPTY);
        }
        if (!hasFileById(fileId)) {
            throw new RuntimeException(ApplicationMessages.FILE_ID_NOT_FOUND + fileId);
        }
        return fileMapper.findById(fileId);
    }

    /**
     * 根据MD5查询文件
     * @param MD5
     * @return
     */
    @Cacheable(value = "File_Cache", key = "#MD5")
    public FileEntity getFileByMD5(String MD5) {
        if (!hasFileByMD5(MD5)) {
            throw new RuntimeException(ApplicationMessages.FILE_MD5_NOT_FOUND + MD5);
        }
        return fileMapper.findByMD5(MD5);
    }

//   //  查询所有文件
//    public Page<FileEntity> getFiles(Pageable pageable) {
//        return fileRepository.findAll(pageable);
//    }

    /**
     * 合并文件块
     * @param chunkEntity
     * @param poolId
     * @param versionContent
     * @param versionNumber
     * @return
     * @throws IOException
     */
    public FileEntity mergeChunks(ChunkEntity chunkEntity,String poolId,String versionContent,String versionNumber) throws IOException {
       if (poolId==null){
            throw new SystemException(SystemErrorEnum.POOL_ID_IS_NULL);
       }
       if (versionContent==null){
           throw new SystemException(SystemErrorEnum.VERSION_CONTENT_ISNOLL);
       }
       if (versionNumber==null){
           throw new SystemException(SystemErrorEnum.VERSION_NUMBER_ISNOLL);
       }
        if (hasFileByMD5(chunkEntity.getIdentifier())) {
            return getFileByMD5(chunkEntity.getIdentifier());
        } else {
            File file = null;
            String extension = FilenameUtils.getExtension(chunkEntity.getFilename());
            if (StringUtils.isEmpty(extension)) {
                file = new File(ApplicationConfig.FILES_SAVE_PATH + File.separator + chunkEntity.getIdentifier());
            } else {
                file = new File(ApplicationConfig.FILES_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + "." + FilenameUtils.getExtension(chunkEntity.getFilename()));
            }
            return mergeChunks(file, chunkEntity,poolId,versionContent,versionNumber);

        }
    }

    private FileEntity mergeChunks(File file, ChunkEntity chunkEntity,String poolId,String versionContent,String versionNumber) throws IOException {
        file.delete();
        file.getParentFile().mkdirs();
        file.createNewFile();
        for (int i = 1; i <= chunkEntity.getTotalChunks(); i++) {
            File chunk = new File(ApplicationConfig.CHUNKS_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + File.separator + i + ".tmp");
            if (chunk.exists()) {

                FileUtils.writeByteArrayToFile(file, FileUtils.readFileToByteArray(chunk), true);
            } else {
                throw new RuntimeException(ApplicationMessages.FILE_CHUNK_NOT_FOUND + chunk.getAbsolutePath());
            }
        }
//        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
//        if (!chunkEntity.getIdentifier().equals(DigestUtils.md5Hex(fileInputStream))) {
//            throw new RuntimeException("文件合并失败，请检查：" + file.getAbsolutePath() + "是否正确。");
//        }

        return saveFile(file, chunkEntity,poolId,versionContent,versionNumber);
    }

    /**
     * 保存文件信息
     * @param file
     * @param chunkEntity
     * @param poolId
     * @param versionContent
     * @param versionNumber
     * @return
     * @throws IOException
     */
    @CacheEvict(value = "File_Cache", allEntries = true)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FileEntity saveFile(File file,ChunkEntity chunkEntity,String poolId,String versionContent,String versionNumber) throws IOException {
        if (file ==null){
            throw new SystemException(SystemErrorEnum.FIlE_IS_NULL);
        }
        if (chunkEntity==null){
            throw new SystemException(SystemErrorEnum.CUNKENTITY_IS_NULL);
        }
        if (poolId==null){
            throw  new SystemException(SystemErrorEnum.POOL_ID_IS_NULL);
        }
        if (versionContent ==null){
            throw new  SystemException(SystemErrorEnum.VERSION_CONTENT_ISNOLL);
        }
        String toolsbaseinfoID=poolId;
        String toolsstorageID =UUID.randomUUID().toString();
        String versionmanagementID =UUID.randomUUID().toString();
        String updounloadmanagementID =UUID.randomUUID().toString();

        FileEntity fileEntity = new FileEntity();
        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        String MD5 = DigestUtils.md5Hex(fileInputStream);
        if (hasFileByMD5(MD5)) {
            throw new RuntimeException(ApplicationMessages.FILE_MD5_EXISTED + MD5);
        }
        fileEntity.setMd5(chunkEntity.getIdentifier());
        fileEntity.setType(FilenameUtils.getExtension(file.getName()));
        fileEntity.setSize(FileUtils.sizeOf(file));
        fileEntity.setLocalPath(file.getAbsolutePath());
        fileEntity.setFilePath(file.toString());
        fileEntity.setToolsbaseinfoId(toolsbaseinfoID);
        fileEntity.setToolsstorageId(toolsstorageID);
        fileEntity.setFileName(chunkEntity.getFilename());
        fileEntity.setVersionmanagementId(versionmanagementID);
        fileEntity.setUpdownloadmanagementId(updounloadmanagementID);
        fileEntity.setAverageScore(0);
        fileEntity.setDownloadStatistics(0);
        //版本添加
        tb_VersionManagementEntity versionManagementEntity=new tb_VersionManagementEntity();
        versionManagementEntity.setVersionId(versionmanagementID);
        versionManagementEntity.setRelease_notes(versionContent);
        versionManagementEntity.setVersion_number(versionNumber);

        fileMapper.add(fileEntity);
        versionManagementMapper.save(versionManagementEntity);

        return  fileEntity;
    }



//    // 合并文件块
//    public tb_Toolsdatastorage mergeChunksFruit(ChunkEntity chunkEntity,String fileId,String userId,String size) throws IOException {
//            File file = null;
//            String extension = FilenameUtils.getExtension(chunkEntity.getFilename());
//            if (StringUtils.isEmpty(extension)) {
//                file = new File(ApplicationConfig.FILES_FRUIT_SAVE_PATH + File.separator + chunkEntity.getIdentifier());
//            } else {
//                file = new File(ApplicationConfig.FILES_FRUIT_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + "." + FilenameUtils.getExtension(chunkEntity.getFilename()));
//            }
//            return mergeChunksFruit(file, chunkEntity,fileId, userId,size);
//
//
//    }

    /**
     * 合并成果
     * @param file
     * @param chunkEntity
     * @param fileId
     * @param userId
     * @param size
     * @return
     * @throws IOException
     */
    private tb_Toolsdatastorage mergeChunksFruit(File file, ChunkEntity chunkEntity,String fileId,String userId,String size) throws IOException {
        file.delete();
        file.getParentFile().mkdirs();
        file.createNewFile();
        for (int i = 1; i <= chunkEntity.getTotalChunks(); i++) {
            File chunk = new File(ApplicationConfig.CHUNKS_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + File.separator + i + ".tmp");
            if (chunk.exists()) {

                FileUtils.writeByteArrayToFile(file, FileUtils.readFileToByteArray(chunk), true);
            } else {
                throw new RuntimeException(ApplicationMessages.FILE_CHUNK_NOT_FOUND + chunk.getAbsolutePath());
            }
        }
//        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
//        if (!chunkEntity.getIdentifier().equals(DigestUtils.md5Hex(fileInputStream))) {
//            throw new RuntimeException("文件合并失败，请检查：" + file.getAbsolutePath() + "是否正确。");
//        }

        return saveFileFruit(file,fileId,userId,size);
    }

    /**
     * 保存文件成果
     * @param file
     * @param fileId
     * @param userId
     * @param size
     * @return
     * @throws IOException
     */
    @CacheEvict(value = "File_Cache", allEntries = true)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public tb_Toolsdatastorage saveFileFruit(File file,String fileId,String userId,String size) throws IOException {
        if (file ==null){
            throw new SystemException(SystemErrorEnum.FIlE_IS_NULL);
        }
        if (fileId==null){
            throw  new SystemException(SystemErrorEnum.POOL_ID_IS_NULL);
        }
        if (userId ==null){
            throw new  SystemException(SystemErrorEnum.VERSION_CONTENT_ISNOLL);
        }

        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        String MD5 = DigestUtils.md5Hex(fileInputStream);
        if (hasFileByMD5(MD5)) {
            throw new RuntimeException(ApplicationMessages.FILE_MD5_EXISTED + MD5);
        }

        tb_Toolsdatastorage toolsdatastorage=new tb_Toolsdatastorage();
        toolsdatastorage.setId(UUID.randomUUID().toString());
        toolsdatastorage.setFileId(fileId);
        toolsdatastorage.setUserId(userId);
        toolsdatastorage.setSize(Integer.parseInt(size));
        toolsdatastorage.setState("1");
        toolsdatastorage.setFilePath(file.toString());

        Integer size1 = toolsdatastorageMapper.calculateFileSize(fileId);
        if (size1==null){
            size1=0;
        }
        Integer integer = toolsdatastorageMapper.StatisticalLine(fileId);
        if (integer==null){
            integer=0;
        }
        if (size1+toolsdatastorage.getSize()>52428800){
            throw new SystemException(SystemErrorEnum.FILE_IS_BIG);

        }

       else if (integer>=10){
            throw new SystemException(SystemErrorEnum.ROW_IS_FULL);

        }
       else {
            toolsdatastorageMapper.save(toolsdatastorage);
            return  toolsdatastorage;
        }

    }







    //  /**
   //  * 加载文件
    // * @param fileName 文件名
    // * @return 文件
    // */
 /*   public Resource loadFileAsResource(String fileName, String id) {
        try {
            Path filePath = Paths.get(fileMapper.findById(id).getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileException("File not found " + fileName, ex);
        }
    }*/

    /**
     * 更新状态码
     * @param id
     * @param state
     */
    public void state(String id, String state) {
        fileMapper.state(id,state);
    }

    /**
     * 根据文件星星排名查询
     * @param page
     * @param size
     * @return
     */
    public /*List<Map<String,Object>>*/PageInfo findAll(int page,int size) {
        PageHelper.startPage(page,size);
        List<Map<String, Object>> all = fileMapper.findAll();
        return new PageInfo(all);
    }
    /**
     * 根据下载数量排名查询
     * @param page
     * @param size
     * @return
     */
    public PageInfo/*List<Map<String,Object>> */KfindAll(int page,int size) {
        PageHelper .startPage(page,size);
        List<Map<String, Object>> maps = fileMapper.KfindAll();
        return new PageInfo(maps);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(String id) {
        if (id==null){
            throw new SystemException(SystemErrorEnum.FILE_ID_ISEMPTY);
        }
        FileEntity fileEntity = fileMapper.findById(id);

         String toolsstorageId = fileEntity.getToolsstorageId();
         String versionmanagementId = fileEntity.getVersionmanagementId();
         String classifiedmanagementId = fileEntity.getClassifiedmanagementId();
         String updownloadmanagementId = fileEntity.getUpdownloadmanagementId();


        if (toolsstorageMapper.findById(toolsstorageId)!=null){
            toolsstorageMapper.delete(toolsstorageId);
        }
        if (versionManagementMapper.findById(versionmanagementId)!=null){
            versionManagementMapper.delete(versionmanagementId);
        }
        if (classifiedManagementMapper.findById(classifiedmanagementId)!=null){
             classifiedManagementMapper.delete(classifiedmanagementId);
        }
        if (upDownLoadManagementMapper.findById(updownloadmanagementId)!=null){
            upDownLoadManagementMapper.delete(updownloadmanagementId);
        }

        toolsevaluateMapper.Pdelete(id);





        System.out.println(fileEntity);
        deleteFile (new File(fileEntity.getFilePath()));
        fileMapper.delete(id);

    }

    /**
     * 更新工具
     * @param toolsBaseinfoEntity
     */
    public void updatePool( tb_ToolsBaseinfoEntity  toolsBaseinfoEntity) {



        toolsBaseinfoMapper.update(toolsBaseinfoEntity);
    }

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    public PageInfo/*List<FileEntity>*/ findByName(int page,int size,String name) {
        PageHelper .startPage(page,size);
        List<FileEntity> byName = fileMapper.findByName(name);
        return new PageInfo(byName);
    }

    /**
     * 根据名字和所属类别查询
     * @param name
     * @param scope
     * @return
     */
    public PageInfo /*List<FileEntity>*/ findByNameAndScope(int page, int size,String name, String scope) {
       PageHelper.startPage(page,size);
        List<FileEntity> byNameAndScope = fileMapper.findByNameAndScope(name, scope);
        return new PageInfo(byNameAndScope);
    }

    /**
     * 根据id进行下载
     */

    public File exportComponentFileById(String id) throws IOException {
        FileEntity fileEntity = fileMapper.findById(id);
//        if (componentFileEntity.isFolder()) {//判断对象是不是文件夹
//            // 如果是文件夹初始化导出目录
////            File exportDir = new File(FileUtils.getTempDirectoryPath() + File.separator + UUID.randomUUID().toString());
//            File exportDir = new File(FileUtils.getTempDirectoryPath() + File.separator + componentFileEntity.getName());
//            exportDir.mkdirs();//建立一个子目录
//            exportComponentFiles(componentFileEntity, exportDir);//递归
////            return CompressUtils.compress(exportDir, new File(FileUtils.getTempDirectoryPath() + File.separator + System.currentTimeMillis() + ".zip"));
//            return compress(exportDir, new File(FileUtils.getTempDirectoryPath() + File.separator + componentFileEntity.getName()+ ".zip"));
//        } else {
            //如果是一个文件
            File exportFile = new File(FileUtils.getTempDirectoryPath() + File.separator + fileEntity.getFileName() + "." + fileEntity.getType());
            FileUtils.copyFile(new File(fileEntity.getLocalPath()), exportFile);
            return exportFile;
       // }
    }

    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    public tb_ToolsBaseinfoEntity findByIdPool(String id) {
        if (id ==null){
            throw new SystemException(SystemErrorEnum.POOL_ID_IS_NULL);
        }
       return toolsBaseinfoMapper.findById(id);
    }

    /**
     * 删除本地文件
     * @param file
     */
    private  void deleteFile(File file/*,String fileName*/){
//
//        if(file.isDirectory()){
//
//            File[] files = file.listFiles();
//
//            for (File temp : files) {
//
//                delete(temp,fileName);
//
//            }
//
//        }else{

           // String name=file.getName();

           // if(fileName.equals(name)){

                file.delete();

          //  }

       // }

    }


    public void deletePool(String id) {
        toolsBaseinfoMapper.delete(id);
    }

    public List<Map<String,Object>> findByIds(String id) {
       return fileMapper.findByIds(id);
    }

    public void poolEvaluate(tb_Toolsevaluate toolsevaluate) {
        if (toolsevaluate==null){
            throw new SystemException(SystemErrorEnum.TB_TOOLSEVALUATE_ISNULL);
        }
        toolsevaluateMapper.save(toolsevaluate);
        toolsevaluateMapper.statisticsEvaluateStart(toolsevaluate.getPoolId());
        Float priceCar =  toolsevaluateMapper.statisticsEvaluateStart(toolsevaluate.getPoolId());
        // 设置位数
        int scale = 2;
        // 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
      //  int roundingMode = 4;
        BigDecimal bd = new BigDecimal((float) priceCar);
        bd = bd.setScale(scale);
        priceCar = bd.floatValue();
        fileMapper.addEvaluate(toolsevaluate.getPoolId(),priceCar);
    }

    /**
     * 统计下载次数
     * @param componentfileId
     */
    public void downloadStatistics(String componentfileId) {
        fileMapper.downloadStatistics(componentfileId);
    }


    public tb_Toolsevaluate SeeEvaluate(String poolId, String userId) {
       return toolsevaluateMapper.findById(poolId,userId);
    }

    /**
     * 删除评价
     * @param poolId
     * @param userId
     * @return
     */
    public int deleteEvaluate(String poolId, String userId) {
        return toolsevaluateMapper.delete(poolId,userId);
    }

    public void arrayFile(String poolid, String[] fileIds) {
        for (int i = 0; i < fileIds.length; i++) {
            FileEntity byId = fileMapper.findById(fileIds[i]);
            byId.setId(UUID.randomUUID().toString());
            byId.setToolsbaseinfoId(poolid);
            fileMapper.add(byId);
        }

    }
}
