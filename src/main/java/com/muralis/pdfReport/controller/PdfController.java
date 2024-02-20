package com.muralis.pdfReport.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@RestController
public class PdfController {


    @GetMapping(value = "/pdf" ,produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getMethodName() throws Exception {

        //config do template
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //contexto de variaveis
        Context context = new Context();
        context.setVariable("nomeDoAtributo", "[parametro configuravel]");
        context.setVariable("areaValor", "dasdas");
        String html = templateEngine.process("templates/home", context);
        System.err.println(html);
        ITextRenderer renderer = new ITextRenderer();


        //define caminho para pegar imagens da pasta resource/static
               String baseUrl = FileSystems
                                .getDefault()
                                .getPath("src", "main", "resources","static")
                                .toUri()
                                .toURL()
                                .toString();
        renderer.setDocumentFromString(html, baseUrl);
        renderer.layout();
        //salva pdf em disco
        OutputStream outputStream = new FileOutputStream("testeSaucer.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

        //le pdf em disco para devolver para o front
        InputStream input = new FileInputStream("testeSaucer.pdf");
        InputStreamResource inputStreamResource = new InputStreamResource(input);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
            ContentDisposition.builder("inline").filename("testeSaucer.pdf").build()
          );
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }

}
