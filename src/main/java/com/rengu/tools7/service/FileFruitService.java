package com.rengu.tools7.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rengu.tools7.entity.ChunkEntity;
import com.rengu.tools7.entity.tb_Toolsdatastorage;
import com.rengu.tools7.enums.SystemErrorEnum;
import com.rengu.tools7.exception.SystemException;
import com.rengu.tools7.mapper.ToolsdatastorageMapper;
import com.rengu.tools7.utils.ApplicationConfig;
import com.rengu.tools7.utils.ApplicationMessages;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@Service
public class FileFruitService {
    @Autowired
    private ToolsdatastorageMapper toolsdatastorageMapper;
    public void deleteFruit(String id) {
        tb_Toolsdatastorage byId = toolsdatastorageMapper.findById(id);
           toolsdatastorageMapper.delete(id);
        deleteFile( new File(byId.getFilePath()));
    }


    private  void deleteFile(File file){
        file.delete();
    }

    // 合并文件块
    public tb_Toolsdatastorage mergeChunksFruit(ChunkEntity chunkEntity, String fileId, String userId, String size) throws IOException {
        File file = null;
        String extension = FilenameUtils.getExtension(chunkEntity.getFilename());
        if (StringUtils.isEmpty(extension)) {
            file = new File(ApplicationConfig.FILES_FRUIT_SAVE_PATH + File.separator + chunkEntity.getIdentifier());
        } else {
            file = new File(ApplicationConfig.FILES_FRUIT_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + "." + FilenameUtils.getExtension(chunkEntity.getFilename()));
        }
        return mergeChunksFruit(file, chunkEntity,fileId, userId,size);


    }

    private tb_Toolsdatastorage mergeChunksFruit(File file, ChunkEntity chunkEntity, String fileId, String userId, String size) throws IOException {
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
        String[] strArr = chunkEntity.getFilename().split("\\.");
        return saveFileFruit(file,fileId,userId,size,chunkEntity.getIdentifier(), strArr[0], strArr[1]);
    }

    // 保存文件信息
    @CacheEvict(value = "File_Cache", allEntries = true)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public tb_Toolsdatastorage saveFileFruit(File file,String fileId,String userId,String size,String md5,String name,String type) throws IOException {
        if (file ==null){
            throw new SystemException(SystemErrorEnum.FIlE_IS_NULL);
        }
        if (fileId==null){
            throw  new SystemException(SystemErrorEnum.POOL_ID_IS_NULL);
        }
        if (userId ==null){
            throw new  SystemException(SystemErrorEnum.VERSION_CONTENT_ISNOLL);
        }
        if (name ==null){
            throw new  SystemException(SystemErrorEnum.EILENAME_CONTENT_ISNOLL);
        }
        if (type==null){
            throw new  SystemException(SystemErrorEnum.EILETYPE_CONTENT_ISNOLL);
        }

      //  @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
      //  String MD5 = DigestUtils.md5Hex(fileInputStream);
        if (hasFileByMD5(md5)) {
            throw new RuntimeException(ApplicationMessages.FILE_MD5_EXISTED + md5);
        }

        tb_Toolsdatastorage toolsdatastorage=new tb_Toolsdatastorage();
        toolsdatastorage.setId(UUID.randomUUID().toString());
        toolsdatastorage.setFileId(fileId);
        toolsdatastorage.setUserId(userId);
        toolsdatastorage.setSize(Integer.parseInt(size));
        toolsdatastorage.setState("1");
        toolsdatastorage.setMd5(md5);
        toolsdatastorage.setName(name);
        toolsdatastorage.setType(type);
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
            throw new RuntimeException("文件过大"+"已上传"+Mbdemo(size1));

        }

        else if (integer>=10){
            throw new SystemException(SystemErrorEnum.ROW_IS_FULL);

        }
        else {
            toolsdatastorageMapper.save(toolsdatastorage);
            return  toolsdatastorage;
        }
    }

    // 根据Md5判断文件是否存在
    public boolean hasFileByMD5(String MD5) {
        if (MD5==null){
            throw new SystemException(SystemErrorEnum.MD5_IS_NULL);
        }
        if (StringUtils.isEmpty(MD5)) {
            return false;
        }
        if (toolsdatastorageMapper.findByMD5(MD5)==null){
            return false;
        }else {
            return true;
        }

    }
    public String Mbdemo(int size){

        Double d =(double)size/1048576;
        DecimalFormat df = new DecimalFormat("0.00");
     return df.format(d)+" 兆";
    }

    // 根据Id导出组件文件
    public File exportComponentFileById(String id) throws IOException {
        if (id==null){
            throw new SystemException( SystemErrorEnum.FILE_ID_ISEMPTY);
        }
        tb_Toolsdatastorage toolsdatastorage = toolsdatastorageMapper.findById(id);
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
        File exportFile = new File(FileUtils.getTempDirectoryPath() + File.separator + toolsdatastorage.getName() + "." + toolsdatastorage.getType());
        FileUtils.copyFile(new File(toolsdatastorage.getFilePath()), exportFile);
        return exportFile;
        // }
    }

    // 保存文件块
    public void saveChunk(ChunkEntity chunkEntity, MultipartFile multipartFile) throws IOException {
        File chunk = new File(ApplicationConfig.CHUNKS_SAVE_PATH + File.separator + chunkEntity.getIdentifier() + File.separator + chunkEntity.getChunkNumber() + ".tmp");
        // chunkEntity.setId(UUID.randomUUID().toString());
        //  chunkEntity.setCreateTime(new ChunkEntity().getCreateTime());
        //  fileMapper.add(chunkEntity);
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
     * 修改成果私有共有状态码
     * @param id 成果id
     * @param state 状态码
     */
    public void fruitstate(String id, String state) {
        if (id==null){
            throw  new SystemException( SystemErrorEnum.FILE_ID_ISEMPTY);
        }
        if (state==null){
            throw  new SystemException( SystemErrorEnum.STATE_ISEMPTY) ;
        }
        if (state .equals("1")||state.equals("2")){
        toolsdatastorageMapper.fruitstate(id,state);
    }else {
            throw  new SystemException(SystemErrorEnum.STATE_ISRUWN);
        }
    }


    /**
     * 根据用户id和状态码查询工具成果
     * @param
     * @param userId
     * @return
     */
    public /*List<tb_Toolsdatastorage>*/PageInfo findPrivateFruit(int page,int size,String scope, String userId, String poolId) {

        PageHelper.startPage(page,size);
       // List<tb_Toolsdatastorage> all = toolsdatastorageMapper.findAll();
        List<tb_Toolsdatastorage> privateFruit = toolsdatastorageMapper.findPrivateFruit(scope, userId, poolId);
        return new PageInfo(privateFruit) ;
    }
    /**
     * 查询所有共有的结果
     * @param state
     * @return
     */
    public/* List<tb_Toolsdatastorage>*/ PageInfo findPublicFruit(int page, int size , String state,String fileId) {
        PageHelper.startPage(page,size);
        List<tb_Toolsdatastorage> publicFruit = toolsdatastorageMapper.findPublicFruit(state,fileId);
        return new PageInfo(publicFruit) ;
    }
}
