/**
 * The CompensationServiceImpl class implements the CompensationService interface to provide implementation
 * for creating and reading compensations. It uses an instance of CompensationRepository to interact with the database.
 *
 * This class is annotated with @Service, which means that it is eligible for Spring auto-detection through classpath scanning.
 * It manages a logger object to log debug statements.
 *
 * @see CompensationService: Interface for managing compensation-related operations.
 * @see CompensationRepository: Interface for accessing the Compensation entities stored in the database.
 */

package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    /**
     * Creates a new compensation within the database.
     *
     * @param compensation the compensation object to be created
     * @return the newly created compensation
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        compensationRepository.insert(compensation);

        return compensation;
    }

    /**
     * Reads a compensation based on the given employee id.
     *
     * @param id the employee id to read the corresponding compensation
     * @return the compensation object corresponding to the given employee id
     * @throws RuntimeException if the supplied employee id does not exist in the
     *                          database
     */
    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with employeeId [{}]", id);

        Compensation compensation = compensationRepository.findByEmployee(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensation;
    }
}