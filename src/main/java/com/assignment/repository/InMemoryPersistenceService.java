package com.assignment.repository;

import com.assignment.exception.PersistException;
import com.assignment.exception.RecordNotFoundException;
import com.assignment.model.DocData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.util.PendingException;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component("PS")
public class InMemoryPersistenceService implements PersistenceService {
    private ConcurrentHashMap<String,DocData> hashMap = new ConcurrentHashMap<>();

    @Override
    public String save(String data) throws PersistException {
        DocData docData = new DocData();
        String docId = createUniqueDocID();
        docData.setDocId(docId);
        docData.setFileName(docId+"file");
        docData.setSize(data.length());
        hashMap.put(docId,docData);
        try {
            createFile(docData.getFileName(), data);
        } catch(IOException e){
            throw new PersistException("Error while creating record",e);
        }
        return docId;
    }

    @Override
    public String retrieve(String docId) throws PersistException,RecordNotFoundException {
        if(hashMap.get(docId)!=null){
            try {
                return readFile(hashMap.get(docId).getFileName());
            } catch(IOException e){
                throw new PersistException("Error while reading record for docId "+docId ,e);
            }
        }else {
            throw new RecordNotFoundException("Record not found with docId " + docId);
        }
    }

    @Override
    public boolean delete(String docId) throws RecordNotFoundException {
        if(hashMap.get(docId)!=null){
             hashMap.remove(docId);
        }else {
            throw new RecordNotFoundException("Record not found with docId " + docId);
        }
        return false;
    }

    @Override
    public void update(String docId, String data) throws PersistException,RecordNotFoundException {
        if(hashMap.get(docId)!=null){
            try {
                createFile(docId + "file", data);
            } catch(IOException e){
                throw new PersistException("Error while updating record for docId "+docId ,e);
            }
        }else {
            throw new RecordNotFoundException("Record not found with docId " + docId);
        }
    }

    private String createUniqueDocID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,20);
    }

    private void createFile(String fileName, String data) throws IOException {
        BufferedWriter fileWrite = new BufferedWriter(new FileWriter(fileName));
        fileWrite.write(data);
        fileWrite.close();
    }

    private String readFile(String fileName) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String data = fileReader.readLine();
        fileReader.close();
        return data;
    }
}
