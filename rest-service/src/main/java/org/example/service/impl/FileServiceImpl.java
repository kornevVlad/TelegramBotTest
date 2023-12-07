package org.example.service.impl;

import lombok.extern.log4j.Log4j;
import org.example.CryptoTool;
import org.example.dao.AppDocumentDao;
import org.example.dao.AppPhotoDao;
import org.example.entity.AppDocument;
import org.example.entity.AppPhoto;
import org.example.service.FileService;
import org.springframework.stereotype.Service;



@Log4j
@Service
public class FileServiceImpl implements FileService {

    private final AppDocumentDao appDocumentDao;

    private final AppPhotoDao appPhotoDao;

    private final CryptoTool cryptoTool;

    public FileServiceImpl(AppDocumentDao appDocumentDao, AppPhotoDao appPhotoDao, CryptoTool cryptoTool) {
        this.appDocumentDao = appDocumentDao;
        this.appPhotoDao = appPhotoDao;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public AppDocument getDocument(String docId) {
        //парсим id
        var id = cryptoTool.idOf(docId);
        if (id == null) {
            return null;
        }
        return appDocumentDao.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        //парсим id
        var id = cryptoTool.idOf(photoId);
        if (id == null) {
            return null;
        }
        return appPhotoDao.findById(id).orElse(null);
    }
}
