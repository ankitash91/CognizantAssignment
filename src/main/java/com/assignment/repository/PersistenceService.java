package com.assignment.repository;

import com.assignment.exception.PersistException;
import com.assignment.exception.RecordNotFoundException;
import com.assignment.model.DocData;

import java.io.IOException;

public interface PersistenceService {
    String save(String data) throws PersistException;
    String retrieve(String docId) throws PersistException,RecordNotFoundException;
    boolean delete(String docId) throws RecordNotFoundException;
    void update(String docId,String data) throws PersistException,RecordNotFoundException;
}
