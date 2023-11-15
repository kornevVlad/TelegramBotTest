package org.example.service;

import org.example.entity.AppDocument;
import org.example.entity.AppPhoto;
import org.example.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileService {

    //получение документа
    AppDocument getDocument(String id);

    //получение фото
    AppPhoto getPhoto(String id);

    //получение массива байтов для преобразования в ответ передачи контента(HTTP ответа)
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
