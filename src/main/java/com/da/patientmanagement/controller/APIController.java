package com.da.patientmanagement.controller;

import com.da.patientmanagement.dto.DTOWrapper;
import com.da.patientmanagement.dto.DatatablesResponseDTO;
import com.da.patientmanagement.dto.PatientDTO;
import com.da.patientmanagement.model.AjaxRequestBody;
import com.da.patientmanagement.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/patient")
@Slf4j
public class APIController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public PatientDTO findById(@PathVariable("id") Long id) {
        return patientService.findById(id);
    }

    @PostMapping("/ajax")
    public DatatablesResponseDTO findAllPageableAjax(@RequestBody AjaxRequestBody body) {
        DatatablesResponseDTO datatablesResponseDTO = new DatatablesResponseDTO();
        DTOWrapper dtoWrapper;
        if (body.getOrder().length > 0) {
            body.getOrder()[0].setColumnName(body.getColumns()[body.getOrder()[0].getColumn()].getData());
        }
        if (StringUtils.isEmpty(body.getSearch().getValue())) {
            dtoWrapper = patientService.findAllPageableAjax(body.getStart(), body.getLength(), body.getShowHidden(), body.getOrder());
        } else {
            String searchValue = body.getSearch().getValue();
            dtoWrapper = patientService.searchByCriteriaOrderByFirstName(searchValue, searchValue, searchValue, body.getStart(), body.getLength(), body.getShowHidden());
        }

        datatablesResponseDTO.setDraw(body.getDraw());
        datatablesResponseDTO.setData(dtoWrapper.getPatientDTOList().toArray());
        datatablesResponseDTO.setRecordsTotal(dtoWrapper.getPatientDTOList().size());
        datatablesResponseDTO.setRecordsFiltered(dtoWrapper.getTotalRecords());

        return datatablesResponseDTO;
    }


    @PutMapping("/deactivate")
    public int softDelete(@RequestParam Long id) {
        if (patientService.patientExistById(id)) {
            return patientService.softDeletePatient(id);
        } else {
            return -1;
        }
    }

    @PutMapping("/reactivate")
    public int reactivate(@RequestParam Long id) {
        if (patientService.patientExistById(id)) {
            return patientService.reactivatePatient(id);
        } else {
            return -1;
        }
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
        if (patientService.patientExistById(id)) {
            patientService.deletePatient(id);
        } else {
            log.info("Nothing changed. Patient with id {} not found", id);
        }
    }
}
