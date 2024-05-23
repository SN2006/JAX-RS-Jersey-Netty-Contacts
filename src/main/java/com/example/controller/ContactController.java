package com.example.controller;

import com.example.domain.Contact;
import com.example.exceptions.ContactDataException;
import com.example.network.ResponseEntity;
import com.example.network.ResponseEntityList;
import com.example.network.ResponseMessage;
import com.example.service.impl.ContactService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@Path("/api/v1/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactController {

    private final ContactService contactService = new ContactService();

    @GET
    public Response fetchAll(){
        List<Contact> contacts = contactService.fetchAll();
        if (!contacts.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                    true, contacts).toString())
                    .status(Response.Status.OK).build();
        }else{
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query-by-name")
    public Response fetchByName(@QueryParam("name") String name){
        List<Contact> contacts = contactService.fetchByName(name);
        if (!contacts.isEmpty()){
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, contacts).toString())
                    .status(Response.Status.OK).build();
        }
        return Response.ok(new ResponseEntityList<>(404, "Not Found",
                        false, Collections.emptyList()).toString())
                .status(Response.Status.NOT_FOUND).build();
    }

    @GET()
    @Path("{id: [1-9][0-9]*}")
    public Response fetchById(@PathParam("id") Long id){
        Contact contact = contactService.fetchById(id);
        if (contact != null) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contact.toString()).toString())
                    .status(Response.Status.OK).build();
        }
        return Response.ok(new ResponseEntity<>(404, "Not Found",
                        true, ResponseMessage.NOT_FOUND).toString())
                .status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Contact contact) {
        try {
            Contact createdContact = contactService.create(contact);
            if (createdContact != null) {
                return Response.ok(new ResponseEntity<>(201, "Created",
                                true, createdContact).toString())
                        .status(Response.Status.CREATED).build();
            }
            return Response.ok(new ResponseEntity<>(204, "No Content",
                            false, ResponseMessage.NO_CONTENT).toString())
                    .status(Response.Status.NO_CONTENT).build();
        }catch (ContactDataException e){
            return Response.ok(new ResponseEntity<>(400, "Bad Request",
                    false, ResponseMessage.BAD_REQUEST).toString())
                    .status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id: [1-9][0-9]*}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Contact contact) {
        Contact contactToUpdate = contactService.fetchById(id);
        if (contactToUpdate != null){
            try {
                Contact updatedContact = contactService.update(id, contact);
                return Response.ok(new ResponseEntity<>(200, "OK",
                                true, updatedContact).toString())
                        .status(Response.Status.OK).build();
            }catch (ContactDataException e){
                return Response.ok(new ResponseEntity<>(400, "Bad Request",
                                false, ResponseMessage.BAD_REQUEST).toString())
                        .status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.ok(new ResponseEntity<>(404, "Not Found",
                        true, ResponseMessage.NOT_FOUND).toString())
                .status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id: [1-9][0-9]*}")
    public Response delete(@PathParam("id") Long id){
        if (contactService.delete(id)){
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, ResponseMessage.DELETED).toString())
                    .status(Response.Status.OK).build();
        }
        return Response.ok(new ResponseEntity<>(404, "Not Found",
                        true, ResponseMessage.NOT_FOUND).toString())
                .status(Response.Status.NOT_FOUND).build();
    }

}
