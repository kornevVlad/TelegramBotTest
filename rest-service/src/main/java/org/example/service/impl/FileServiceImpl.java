package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.example.dao.AppDocumentDao;
import org.example.dao.AppPhotoDao;
import org.example.entity.AppDocument;
import org.example.entity.AppPhoto;
import org.example.entity.BinaryContent;
import org.example.service.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Log4j
@Service
public class FileServiceImpl implements FileService {

    private final AppDocumentDao appDocumentDao;

    private final AppPhotoDao appPhotoDao;

    public FileServiceImpl(AppDocumentDao appDocumentDao, AppPhotoDao appPhotoDao) {
        this.appDocumentDao = appDocumentDao;
        this.appPhotoDao = appPhotoDao;
    }

    @Override
    public AppDocument getDocument(String docId) {
        //парсим id
        //TODO добавить дефишрование в хеш-строку
        var id = Long.parseLong(docId);
        return appDocumentDao.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        //парсим id
        //TODO добавить дефишрование в хеш-строку
        var id = Long.parseLong(photoId);
        return appPhotoDao.findById(id).orElse(null);
    }

    /**
     * преобразование массива байтов из БД в объект класса
     */
    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {

        try {
            //TODO добавить генерацию имени временного файла
            File temp = File.createTempFile("tempFile", "bin");
            temp.deleteOnExit();
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}
