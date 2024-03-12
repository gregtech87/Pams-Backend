package com.example.pamsbackend;

import com.example.pamsbackend.entity.Note;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.service.NoteServiceImpl;
import com.example.pamsbackend.service.UserServiceImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PDFtest {
    private final UserServiceImpl userServiceimpl;
    private final NoteServiceImpl noteService;
    private boolean fileNameExists = false;
    private String filename = "-UserReport.pdf";
    private String fullFilename;

    @Autowired
    public PDFtest(UserServiceImpl userServiceimpl, NoteServiceImpl noteService) {
        this.userServiceimpl = userServiceimpl;
        this.noteService = noteService;
    }

    public void test(User u) throws JRException, IOException {
        System.out.println("PPPPPPPPPPDDDDDDDDDDDDDDFFFFFFFFFFFFFF");

/*        //       *****************
                System.setProperty("java.awt.headless", "false");
//          Output file location to create report in pdf form
        String outputFile = "C:\\Users\\xxgregot\\KravSpec\\Jasper\\" + "JasperReportExample.pdf";
//         List to hold Items
        List<User> listItems = new ArrayList<>();
        listItems.add(u);

//         Convert List to JRBeanCollectionDataSource
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
//         Map to hold Jasper report Parameters
        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("Parameter1", itemsJRBean);
        parameters.put("Parameter2", u.getConfirmationToken().getCreatedAt());
        parameters.put("CollectionBeanParam", itemsJRBean);
        String filepath = "src/main/resources/pdfTemplate/Invoice_Table_Based.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(filepath);
//
//         Using jasperReport object to generate PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        System.out.println("File Opened");
//                 **************************/

        //     ****** InvolveInInnovation ******
        String filepath = "src/main/resources/pdfTemplate/Invoice_Table_Based.jrxml";

        List<Note> listItems = new ArrayList<>();
        List<String> noteIds = u.getNotes();
        Collections.reverse(noteIds);
        for (String s: noteIds){
            Optional<Note> note = noteService.findById(s);
            if (note.isPresent()) {
                Note n = note.get();
                if (n.getNote().length() > 70){
                    n.setNote(n.getNote().substring(0, 70));
                }
                listItems.add(n);
            }
        }


//         Convert List to JRBeanCollectionDataSource
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("CollectionBeanParam", itemsJRBean);
        parameters.put("firstName",u.getFirstName());
        parameters.put("lastName",u.getLastName());
        parameters.put("username",u.getUsername());
        parameters.put("email",u.getEmail());
        parameters.put("phone",u.getPhone());
        parameters.put("dateOfBirth",u.getDateOfBirth());
        parameters.put("createdAt", u.getConfirmationToken().getCreatedAt());
        parameters.put("confirmedAt", u.getConfirmationToken().getConfirmedAt());
        parameters.put("street",u.getAddress().getStreet());
        parameters.put("streetNo",u.getAddress().getStreetNumber());
        parameters.put("postalCode",u.getAddress().getPostalCode());
        parameters.put("city",u.getAddress().getCity());
        if (u.isCustomLocation()){
            parameters.put("customLat",u.getCustomLat());
            parameters.put("customLong",u.getCustomLong());
        }

        //read jrxml file and creating jasperdesign object
        JasperReport jasperReport = JasperCompileManager.compileReport(filepath);
        //         Using jasperReport object to generate PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        /*
         * Checks if folder contain file with same name.
         * If file exists, make sure new file overwrite the old.
         * If error finding folder, create it.
         * */
        Path userPath = Paths.get("User-Files" + "/" + u.getUsername());
        if (!Files.exists(userPath)) {
            Files.createDirectories(userPath);
        }
            Files.list(userPath).forEach(file -> {
                if (file.getFileName().toString().endsWith("UserReport.pdf")) {
                    fileNameExists = true;
                    fullFilename = file.getFileName().toString();
                  }
            });
        String outputFile = "";
        String fileCode = RandomStringUtils.randomAlphanumeric(15);
        if (!fileNameExists) {
            outputFile = userPath + "/" + fileCode + filename;
        } else {
            outputFile = userPath + "/" + fullFilename;
        }


//        outputFile = "src/main/resources/pdfTemplate/" + "JasperReportExample.html";
//        JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFile);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

        System.out.println("File Generated");
        //     ****** InvolveInInnovation ******

/*

//        ***** infybuzz ***

        */
        /* Output file location to create report in pdf form *//*

        String outputFile = "C:\\Users\\xxgregot\\KravSpec\\Jasper\\" + "JasperReportExample.pdf";

        //read jrxml file and creating jasperdesign object
//        InputStream input = new FileInputStream(new File("src/main/resources/pdfTemplate/Flower.jrxml"));
//        InputStream input = new FileInputStream(new File("src/main/resources/pdfTemplate/Flower_Table_Based.jrxml"));
        InputStream input = new FileInputStream(new File("src/main/resources/pdfTemplate/Invoice_Table_Based.jrxml"));

        JasperDesign jasperDesign = JRXmlLoader.load(input);

        Map<String, Object> paramet = new HashMap<>();
        paramet.put("NameInJasperStudioParam", u.getFirstName());

        Address address1 = new Address("street1", 1, 828282, "city1");
        Address address2 = new Address("street2", 2, 939393, "city2");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(addressList);
        JasperReport report = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint print = JasperFillManager.fillReport(report, paramet, dataSource);
        JasperExportManager.exportReportToPdfFile(print, outputFile);
        System.out.println("File Generated");
//           **************
*/

    }
}
