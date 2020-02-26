package com.assignment.service;

import com.assignment.exception.PersistException;
import com.assignment.exception.RecordNotFoundException;
import com.assignment.repository.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class DocumentService {

    @Autowired
    @Qualifier("PS")
    private PersistenceService persistenceService;

    public String createDocument(String data) throws PersistException {
        return persistenceService.save(data);
    }

    public String retrieveData(String docId) throws PersistException, RecordNotFoundException {
        return persistenceService.retrieve(docId);
    }

    public boolean deleteData(String docId) throws RecordNotFoundException {
        return persistenceService.delete(docId);
    }

    public void updateDocument(String docId, String data) throws PersistException, RecordNotFoundException {
        persistenceService.update(docId,data);
    }
}
