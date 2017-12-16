/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Programme;
import com.bootcamp.services.ProgrammeService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leger
 */
@RestController("ProgrammeController")
@RequestMapping("/programmes")
@CrossOrigin(origins = "*")
@Api(value = "Programme API", description = "Programme API")
public class ProgrammeController {

    @Autowired
    ProgrammeService programmeService;

    @Autowired
    HttpServletRequest request;

    /**
     * Get all the programs in the database
     *
     * @return programs list
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of programs", notes = "Get list of programs")
    public ResponseEntity<List<Programme>> findAll() throws SQLException {
        HttpStatus httpStatus = null;
        List<Programme> programmes = programmeService.findAll(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Programme>>(programmes, httpStatus);
    }

    /**
     * Insert the given program in the database
     *
     * @param programme
     * @return programme
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new programm", notes = "Create a new programm")
    public ResponseEntity<Programme> create(@RequestBody @Valid Programme programme) {
        HttpStatus httpStatus = null;

        try {
            programme = programmeService.create(programme);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(ProgrammeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<Programme>(programme, httpStatus);
    }

    /**
     * Update the given program in the database
     *
     * @param programme
     * @return program id
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a projet", notes = "update a projet")
    public ResponseEntity<Integer> update(@RequestBody @Valid Programme programme) throws Exception {
        int done = programmeService.update(programme);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Delete a program in the database by its id
     *
     * @param id
     * @return program id
     * @throws Exception
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete programm", notes = "delete a programm")
    public ResponseEntity<Integer> delete(@PathVariable int id) throws Exception, IllegalAccessException, DatabaseException, InvocationTargetException {
        if (programmeService.exist(id));
        boolean done = programmeService.delete(id);
        return new ResponseEntity(done, HttpStatus.OK);
    }
}
