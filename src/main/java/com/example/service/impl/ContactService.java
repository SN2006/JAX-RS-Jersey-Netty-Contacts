package com.example.service.impl;

import com.example.domain.Contact;
import com.example.exceptions.ContactDataException;
import com.example.repository.impl.ContactRepository;
import com.example.service.AppService;
import com.example.utils.ContactValidator;

import java.util.Collections;
import java.util.List;

public class ContactService implements AppService<Contact> {

    private final ContactRepository repository = new ContactRepository();
    private final ContactValidator validator = new ContactValidator();

    @Override
    public Contact create(Contact obj) throws ContactDataException {
        Contact validatedContact = validator.validateContact(obj);
        repository.create(validatedContact);
        return repository.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<Contact> fetchAll() {
        return repository.fetchAll().orElse(Collections.emptyList());
    }

    @Override
    public Contact fetchById(Long id) {
        return repository.fetchById(id).orElse(null);
    }

    @Override
    public Contact update(Long id, Contact obj) throws ContactDataException{
        Contact validatedContact = validator.validateContact(obj);
        if (repository.fetchById(id).isPresent()) {
            repository.update(id, validatedContact);
        }
        return repository.fetchById(id).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        if (repository.fetchById(id).isPresent()) {
            repository.delete(id);
            return true;
        }
        return false;
    }

    public List<Contact> fetchByName(String name){
        return repository.fitchByName(name).orElse(Collections.emptyList());
    }
}
