package com.pcc.PatientCareCenter.Views.Components.JRXMLPrinter;

import com.pcc.PatientCareCenter.Views.Components.DCConnection.DataComponentConnection;
import com.pcc.PatientCareCenter.Views.Components.ReportPrinting.PrintDialog;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PccJRXmlLoader {
    public static final String JRXML_PATH = "/com/pcc/PatientCareCenter/Jrxml/";
    private String fileName;
    String jasperPath;
    Map<String, Object> parameters = new HashMap<>();
    JasperReport jasperReport;

    public PccJRXmlLoader(String fileName) throws JRException {
        this.fileName = fileName;
        File file = new File(Objects.requireNonNull(getClass().getResource(JRXML_PATH + fileName + ".jasper")).getFile());
        jasperPath = file.getAbsolutePath();
        jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getAbsolutePath());
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
        PrintDialog printDialog=new PrintDialog(jasperPrint);
        printDialog.start(new Stage());
        boolean printSuccess=true; // true = show print dialog
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
