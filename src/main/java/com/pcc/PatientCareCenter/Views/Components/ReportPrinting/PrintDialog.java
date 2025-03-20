package com.pcc.PatientCareCenter.Views.Components;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class PrintDialog extends Application {

    private JasperPrint jasperPrint; // JasperPrint object to be printed

    public PrintDialog(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Print Settings");

        // ComboBox for selecting printer
        ComboBox<String> printerComboBox = new ComboBox<>();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            printerComboBox.getItems().add(service.getName());
        }
        if (!printerComboBox.getItems().isEmpty()) {
            printerComboBox.setValue(printerComboBox.getItems().get(0)); // Set default printer
        }

        // ComboBox for selecting paper size
        ComboBox<MediaSizeName> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(
                MediaSizeName.ISO_A4,
                MediaSizeName.ISO_A3,
                MediaSizeName.ISO_A5,
                MediaSizeName.NA_LETTER,
                MediaSizeName.NA_LEGAL
        );
        sizeComboBox.setValue(MediaSizeName.ISO_A4); // Default: A4

        // RadioButtons for orientation
        ToggleGroup orientationGroup = new ToggleGroup();
        RadioButton portraitRadio = new RadioButton("Portrait");
        RadioButton landscapeRadio = new RadioButton("Landscape");
        portraitRadio.setToggleGroup(orientationGroup);
        landscapeRadio.setToggleGroup(orientationGroup);
        portraitRadio.setSelected(true); // Default selection

        // Spinner for selecting number of copies
        Spinner<Integer> copiesSpinner = new Spinner<>(1, 100, 1); // Min:1, Max:100, Default:1

        // CheckBox for showing print dialog
        CheckBox showPrintDialog = new CheckBox("Show Print Dialog");
        showPrintDialog.setSelected(false); // Default: No print dialog

        // Print Button
        Button printButton = new Button("Print");
        printButton.setOnAction(e -> printReport(
                printerComboBox.getValue(),
                sizeComboBox.getValue(),
                landscapeRadio.isSelected(),
                copiesSpinner.getValue(),
                showPrintDialog.isSelected()
        ));

        // Layout
        VBox layout = new VBox(10,
                new Label("Select Printer:"), printerComboBox,
                new Label("Select Paper Size:"), sizeComboBox,
                new Label("Select Orientation:"), portraitRadio, landscapeRadio,
                new Label("Number of Copies:"), copiesSpinner,
                showPrintDialog, printButton
        );
        layout.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(layout, 300, 350));
        primaryStage.show();
    }

    private void printReport(String selectedPrinter, MediaSizeName paperSize, boolean isLandscape, int copies, boolean showDialog) {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(paperSize);
        printRequestAttributeSet.add(new Copies(copies));
        printRequestAttributeSet.add(isLandscape ? OrientationRequested.LANDSCAPE : OrientationRequested.PORTRAIT);

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPrintDialog(showDialog);

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setConfiguration(configuration);

        // Find selected printer
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService selectedService = null;
        for (PrintService service : services) {
            if (service.getName().equals(selectedPrinter)) {
                selectedService = service;
                break;
            }
        }

        if (selectedService != null) {
            try {
                exporter.exportReport();
                System.out.println("Print successful!");
            } catch (JRException e) {
                System.out.println("Error printing: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Printer not found!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
