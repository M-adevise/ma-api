package app.m.advise.service.handler;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;
import static app.m.advise.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.m.advise.endpoint.rest.model.MedicalInfo;
import app.m.advise.model.Department;
import app.m.advise.model.Doctor;
import app.m.advise.model.exception.ApiException;
import app.m.advise.model.exception.BadRequestException;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class TemplateHandler {

  public byte[] generatePdf(
      Doctor doctor, Department department, MedicalInfo medicalInfo, String template) {
    ITextRenderer renderer = new ITextRenderer();
    loadStyle(renderer, doctor, department, medicalInfo, template);
    renderer.layout();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      renderer.createPDF(outputStream);
    } catch (DocumentException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
    return outputStream.toByteArray();
  }

  private void loadStyle(
      ITextRenderer renderer,
      Doctor doctor,
      Department department,
      MedicalInfo medicalInfo,
      String template) {
    renderer.setDocumentFromString(
        parseTemplateToString(doctor, department, medicalInfo, template));
  }

  private String parseTemplateToString(
      Doctor doctor, Department department, MedicalInfo medicalInfo, String template) {
    TemplateEngine templateEngine = configureTemplate();
    Context context = configureContext(doctor, department, medicalInfo);
    return templateEngine.process(template, context);
  }

  private Context configureContext(Doctor doctor, Department department, MedicalInfo medicalInfo) {
    Context context = new Context();
    var date = Date.from(medicalInfo.getPatient().getBirthDate());
    context.setVariable("doctor", doctor);
    context.setVariable("department", department);
    context.setVariable("patient", medicalInfo.getPatient());
    context.setVariable("additionalInformation", medicalInfo.getPatientAdditionalInfo());
    context.setVariable("treatments", medicalInfo.getTreatment());
    context.setVariable("birthdate", date);
    context.setVariable("postoperative", medicalInfo.getContinualPostoperative());
    return context;
  }

  public static String base64Image(byte[] image) {
    if (image == null) {
      return null;
    }
    return Base64.getEncoder().encodeToString(image);
  }

  public static byte[] toByteArray(File file) {
    try (FileInputStream fileInputStream = new FileInputStream(file); ) {
      int fileSize = (int) file.length();
      byte[] result = new byte[fileSize];
      int readBytes = fileInputStream.read(result);
      if (fileSize != readBytes) {
        throw new ApiException(
            CLIENT_EXCEPTION, "File" + file.getName() + " could not be entirely read. ");
      }
      return result;
    } catch (IOException e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  private TemplateEngine configureTemplate() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setTemplateMode(TemplateMode.HTML);

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }
}
