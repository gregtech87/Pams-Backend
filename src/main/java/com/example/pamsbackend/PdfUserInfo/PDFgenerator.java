package com.example.pamsbackend.PdfUserInfo;

import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.InstpectFolder;
import com.example.pamsbackend.service.NoteServiceImpl;
import com.example.pamsbackend.service.UserServiceImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
public class PDFgenerator {

    //TODO link pdf to user
    private final UserServiceImpl userServiceimpl;
    private final NoteServiceImpl noteService;
    private boolean fileNameExists = false;
    private String filename = "-UserReport.pdf";
    private String fullFilename;

    @Autowired
    public PDFgenerator(UserServiceImpl userServiceimpl, NoteServiceImpl noteService) {
        this.userServiceimpl = userServiceimpl;
        this.noteService = noteService;
    }

    public User generateUserPDF(User user) throws JRException, IOException {

        // Destination path
        Path userPath = Paths.get("User-Files" + "/" + user.getUsername());

        // Template path
        String filepath = "src/main/resources/pdfTemplateUserInfo/User_Info.jrxml";

        // Read jrxml file and creating jasperdesign object
        JasperReport jasperReport = JasperCompileManager.compileReport(filepath);

        List<Note> listItems = new ArrayList<>();
        List<String> noteIds = user.getNotes();
        Collections.reverse(noteIds);
        for (String s : noteIds) {
            Optional<Note> note = noteService.findById(s);
            if (note.isPresent()) {
                Note n = note.get();
                if (n.getNote().length() > 70) {
                    n.setNote(n.getNote().substring(0, 70));
                }
                listItems.add(n);
            }
        }

        //Convert List to JRBeanCollectionDataSource
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

        //Set parameters.
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("CollectionBeanParam", itemsJRBean);
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("username", user.getUsername());
        parameters.put("email", user.getEmail());
        parameters.put("phone", user.getPhone());
        parameters.put("dateOfBirth", user.getDateOfBirth());
        parameters.put("createdAt", user.getConfirmationToken().getCreatedAt());
        parameters.put("confirmedAt", user.getConfirmationToken().getConfirmedAt());
        parameters.put("street", user.getAddress().getStreet());
        parameters.put("streetNo", user.getAddress().getStreetNumber());
        parameters.put("postalCode", user.getAddress().getPostalCode());
        parameters.put("city", user.getAddress().getCity());
        if (user.isCustomLocation()) {
            parameters.put("customLat", user.getCustomLat());
            parameters.put("customLong", user.getCustomLong());
        }

        // Using jasperReport object to generate PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        /*
         * Checks if folder contain file with same name.
         * If file exists, make sure new file overwrite the old.
         * If not finding folder, create it.
         * */
        fileNameExists = new InstpectFolder().inspectFileName(filename.substring(1 ), user.getUsername());
//        try {
//            Files.list(userPath).forEach(file -> {
//                if (file.getFileName().toString().endsWith(filename)) {
//                    fileNameExists = true;
//                    fullFilename = file.getFileName().toString();
//                }
//            });
//        } catch (IOException e) {
//            if (!Files.exists(userPath)) {
//                System.err.println("******************* NEW DIRECTORY CREATED: " + userPath + " ******************");
//                Files.createDirectories(userPath);
//            }
//            System.err.println("First time user: " + user.getUsername() + " generate a PDF!");
//        }

        // Checks if file have identifier or not.
        String outputFile = "";
        String fileCode = RandomStringUtils.randomAlphanumeric(15);
        if (!fileNameExists) {
            outputFile = userPath + "/" + fileCode + filename;
            user.setPdfUser(new PdfUser(fileCode, LocalDateTime.now().format(ISO_DATE_TIME)));
            userServiceimpl.save(user);
        } else {
            outputFile = userPath + "/" + user.getPdfUser().getUserInfoPdfIdentifier() + filename;
            user.getPdfUser().setCreatedAt(LocalDateTime.now().format(ISO_DATE_TIME));

            System.out.println(LocalDateTime.now().format(ISO_DATE_TIME));
//            System.out.println(LocalDateTime.now().format(RFC_1123_DATE_TIME));
            userServiceimpl.save(user);
        }


        // Generate the  HTML
//        outputFile = "src/main/resources/pdfTemplate/" + "JasperReportExample.html";
//        JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFile);

        // Generate the PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

        System.out.println("PDF file Generated for User: " + user.getUsername()+ "!");
        return user;
    }
}
