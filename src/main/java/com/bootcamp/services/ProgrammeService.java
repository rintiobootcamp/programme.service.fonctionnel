package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.ProgrammeCRUD;
import com.bootcamp.entities.Programme;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leger
 */
@Component
public class ProgrammeService implements DatabaseConstants {

    /**
     * Insert the given program in the database
     *
     * @param programme
     * @return programme
     * @throws SQLException
     */
    public Programme create(Programme programme) throws SQLException {
        programme.setDateDebut(System.currentTimeMillis());
        ProgrammeCRUD.create(programme);
        return programme;
    }

    /**
     * Update the given program in the database
     *
     * @param programme
     * @return program id
     * @throws SQLException
     */
    public int update(Programme programme) throws SQLException {
        ProgrammeCRUD.update(programme);
        return programme.getId();
    }

    /**
     * Delete the given program in the database
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(int id) throws Exception {
        Programme programme = read(id);
        return ProgrammeCRUD.delete(programme);
    }

    /**
     * Get all the programs in the database matching the given request
     *
     * @param request
     * @return programs list
     * @throws SQLException
     */
    public List<Programme> findAll(HttpServletRequest request) throws SQLException {
        return ProgrammeCRUD.read();
    }

    /**
     * Get a program by its id
     *
     * @param id
     * @return programme
     * @throws SQLException
     */
    public Programme read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Programme> likeTables = ProgrammeCRUD.read(criterias);
        return likeTables.get(0);
    }

    /**
     * Check if a program exist in the database
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean exist(int id) throws Exception {
        if (read(id) != null) {
            return true;
        }
        return false;
    }

}
