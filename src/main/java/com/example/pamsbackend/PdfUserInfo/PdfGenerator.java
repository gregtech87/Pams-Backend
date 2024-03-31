package com.example.pamsbackend.PdfUserInfo;

import com.example.pamsbackend.dao.UserService;
import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.fileUpAndDownload.InspectFolder;
import com.example.pamsbackend.service.NoteServiceImpl;
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
public class PdfGenerator {

    private final UserService userService;
    private final NoteServiceImpl noteService;

    @Autowired
    public PdfGenerator(UserService userService, NoteServiceImpl noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    public User generateUserPDF(User user) throws JRException, IOException {
        String filename = "-UserReport.pdf";
        String outputFile;
        // Destination path
        Path userPath = Paths.get("User-Files" + "/" + user.getUsername());
        // Template path
        String filepath = "src/main/resources/pdfTemplateUserInfo/User_Info.jrxml";
        // Read jrxml file and creating jasperDesign object
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
        boolean fileNameExists = new InspectFolder().inspectFileName(filename.substring(1), user.getUsername(), userPath);

        // Checks if file have identifier or not.
        String fileCode = RandomStringUtils.randomAlphanumeric(15);
        if (!fileNameExists) {
            outputFile = userPath + "/" + fileCode + filename;
            user.setPdfUser(new PdfUser(fileCode, LocalDateTime.now().format(ISO_DATE_TIME)));
            userService.save(user);
        } else {
            outputFile = userPath + "/" + user.getPdfUser().getUserInfoPdfIdentifier() + filename;
            user.getPdfUser().setCreatedAt(LocalDateTime.now().format(ISO_DATE_TIME));
            userService.save(user);
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
