package com.assignment.controller;

import com.assignment.exception.PersistException;
import com.assignment.exception.RecordNotFoundException;
import com.assignment.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage/documents")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @PostMapping
    public ResponseEntity<String> createDocument(@RequestBody String data) {
        try {
            String docId = service.createDocument(data);
            return new ResponseEntity<>(docId,HttpStatus.CREATED);

        } catch (PersistException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{docId}")
    public ResponseEntity<String> getDocument(@PathVariable("docId") String docId){
        try {
            String data = service.retrieveData(docId);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (PersistException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{docId}")
    public ResponseEntity<String> updateDocument(@PathVariable("docId") String docId, @RequestBody String data) {

        try {
            service.updateDocument(docId, data);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (PersistException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<String> deleteDocument(@PathVariable("docId") String docId) {
        try {
            service.deleteData(docId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
