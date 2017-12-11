package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.ProgrammeCRUD;
import com.bootcamp.entities.Programme;
import static java.net.InetAddress.getByName;
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

    public Programme create(Programme programme) throws SQLException {
        programme.setDateDebut(System.currentTimeMillis());
        ProgrammeCRUD.create(programme);
        return programme;
    }
    
    public int update(Programme programme) throws SQLException {
        ProgrammeCRUD.update(programme);
        return programme.getId();
    }
    
    public boolean delete(int id) throws Exception {
        Programme programme = read(id);
        return ProgrammeCRUD.delete(programme);
    }

    public List<Programme> findAll(HttpServletRequest request) throws SQLException {
        return ProgrammeCRUD.read();
    }
    
    public Programme read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Programme> likeTables = ProgrammeCRUD.read(criterias);
        return likeTables.get(0);
    }
    
    public boolean exist(int id) throws Exception{
        if(read(id)!=null)
            return true;
        return false;
    }
    
}
