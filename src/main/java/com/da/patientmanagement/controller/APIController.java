package com.da.patientmanagement.controller;

import com.da.patientmanagement.dto.PatientDTO;
import com.da.patientmanagement.model.AjaxRequestBody;
import com.da.patientmanagement.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
@Slf4j
public class APIController {

    @Autowired
    private PatientService patientService;

    @Autowired
    protected Validator validator;

    @GetMapping("/all")
    public List<PatientDTO> findAllPageable(Integer page, Integer size) {
        return patientService.findAllPageable(page, size);
    }

    @PostMapping("/ajax")
    public List<PatientDTO> findAllPageableAjax(@RequestBody AjaxRequestBody body) {
        if (body.getOrder().length > 0) {
            body.getOrder()[0].setColumnName(body.getColumns()[body.getOrder()[0].getColumn()].getData());
        }
        if (StringUtils.isEmpty(body.getSearch().getValue())) {
            return patientService.findAllPageableAjax(body.getStart(), body.getLength(), body.getOrder());
        } else {
            String searchValue = body.getSearch().getValue();
            return patientService.searchByCriteriaOrderByFirstName(searchValue, searchValue, searchValue, body.getStart(), body.getLength());
        }
    }

    @GetMapping("/filter")
    public List<PatientDTO> findAllByIsActive(@RequestParam Boolean isActive, Integer page, Integer size) {
        return patientService.findAllByIsActive(isActive, page, size);
    }

    @PutMapping("/softDelete")
    public int softDelete(@RequestParam Long id) {
        if (patientService.patientExistById(id)) {
            return patientService.softDeletePatient(id);
        } else {
            return -1;
        }
    }

    @GetMapping("/searchBy")
    public List<PatientDTO> searchByCriteriaOrderByFirstName(@RequestParam(required = false) String firstName,
                                                             @RequestParam(required = false) String lastName,
                                                             @RequestParam(required = false) String country,
                                                             Integer page, Integer size) {
        return patientService.searchByCriteriaOrderByFirstName(firstName, lastName, country, page, size);
    }

    @PostMapping("/create")
    public PatientDTO createNewPatient(@RequestBody @Valid PatientDTO patient) {
        return patientService.save(patient);
    }

    @PutMapping("/update")
    public void updatePatient(@RequestBody PatientDTO patient) {
        if (patientService.patientExistById(patient.getId())) {
            patientService.save(patient);
        } else {
            log.info("No existing record found for patient with id: {}", patient.getId());
        }
    }

    @DeleteMapping("/delete")
    public void deletePatient(@RequestParam Long id) {
        patientService.deletePatient(id);
    }
}
