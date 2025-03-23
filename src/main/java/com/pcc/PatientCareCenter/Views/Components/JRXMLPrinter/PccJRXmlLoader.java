package com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter;

import com.pcc.PatientCareCenter.Views.Components.DCConnection.DataComponentConnection;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PccJRXmlLoader {
    public static final String JRXML_PATH = "src/main/resources/com/pcc/PatientCareCenter/Jrxml/";
    private String fileName;
    String jasperPath;
    Map<String, Object> parameters = new HashMap<>();
    JasperReport jasperReport;

    public PccJRXmlLoader(String fileName) throws JRException {
        this.fileName = fileName;
        File file = new File(JRXML_PATH + fileName + ".jasper");
        jasperPath = file.getAbsolutePath();
        jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperPath);
    }

    public void addParams(Map<String, Object> params) {
        for (String key : params.keySet()) {
            parameters.put(key, params.get(key));
        }
    }

    public boolean printWithDCConnection(List<String> paramDCConnection, DataComponentConnection[] connections) throws JRException {
        if (paramDCConnection.size() != connections.length) throw new RuntimeException("Lengths does not match");
        for (int i = 0; i < connections.length; i++) {
            DataComponentConnection connection = connections[i];
            parameters.put(paramDCConnection.get(i), connection.getData());
        }
        parameters.forEach((k, o) -> {
            if (o != null) {
                System.out.println(k + ":\t" + o.getClass());
            }
        });
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//        String s = JasperFillManager.fillReportToFile(jasperPath, parameters);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
        }
//        PrintDialog printDialog = new PrintDialog(file);
//        Stage stage = new Stage();
//        printDialog.start(stage);
        System.out.println(file);
        if (file.exists()) {
            try {
                // Open the file using the default application
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist: " + file.getAbsolutePath());
        }
        boolean printSuccess = true; // true = show print dialog
//        printSuccess = JasperPrintManager.printReport(jasperPrint, true);
        return printSuccess;
    }

    public PccJRXmlLoader(String fileName, Map<String, Object> parameters) {
        this.fileName = fileName;
        this.parameters = parameters;
//        JRBeanCollectionDataSource jrBeanCollectionDataSource=new JRBeanCollectionDataSource(list<objects>); fields
    }

    public void print() throws JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
    }

    public static void main(String[] args) {
//        try {
////            PccJRXmlLoader jrXmlLoader = new PccJRXmlLoader("Prescription");
////            jrXmlLoader.pr
//
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        }
    }
}
