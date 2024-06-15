package app.m.advise.endpoint.controller;

import static app.m.advise.endpoint.mapper.MediaTypeMapper.parseMediaTypeFromBytes;

import app.m.advise.endpoint.rest.model.MedicalInfo;
import app.m.advise.service.MedicalInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MedicalInfoController {
  private final MedicalInfoService service;

  @GetMapping("/patients/{id}/medical_info")
  public ResponseEntity<byte[]> getMedicalInfo(@PathVariable("id") String pId) {
    var file = service.getMedicalInfo(pId);
    return ResponseEntity.ok().contentType(parseMediaTypeFromBytes(file)).body(file);
  }

  @PutMapping("/patients/{id}/medical_info")
  public MedicalInfo crupdateMedicalInfo(@RequestBody MedicalInfo medicalInfo) {
    return service.crupdateMedicalInfo(medicalInfo);
  }
}
