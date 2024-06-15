package app.m.advise.service.handler;

import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.endpoint.rest.model.Patient;
import app.m.advise.model.exception.ApiException;
import app.m.advise.model.exception.BadRequestException;
import com.lowagie.text.DocumentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;
import static app.m.advise.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public class TemplateHandler {

    public byte[] generatePdf(Doctor doctor, Patient patient,
                              byte[] logoAsBytes, String template) {
        ITextRenderer renderer = new ITextRenderer();
        loadStyle(renderer, doctor, patient, logoAsBytes, template);
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
            Patient patient,
            byte[] logoAsBytes,
            String template) {
        renderer.setDocumentFromString(
                parseTemplateToString(doctor, patient, logoAsBytes, template));
    }

    private String parseTemplateToString(
            Doctor doctor, Patient patient, byte[] logoAsBytes, String template) {
        TemplateEngine templateEngine = configureTemplate();
        Context context = configureContext(doctor, patient, logoAsBytes);
        return templateEngine.process(template, context);
    }

    private Context configureContext(
            Doctor doctor, Patient patient, byte[] logoAsBytes) {
        Context context = new Context();

        context.setVariable("doctor", doctor);
        context.setVariable("patient", patient);
        context.setVariable("logo", base64Image(logoAsBytes));
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
