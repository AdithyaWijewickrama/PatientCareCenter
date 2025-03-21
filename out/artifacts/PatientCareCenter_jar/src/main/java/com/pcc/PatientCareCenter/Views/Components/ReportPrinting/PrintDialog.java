package com.pcc.PatientCareCenter.Views.Components.ReportPrinting;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.pcc.PatientCareCenter.Views.Components.ReportPrinting.PrintUtils.populateMediaSizeComboBox;
import static com.pcc.PatientCareCenter.Views.GlobalsViews.showErrorAlert;
import static com.pcc.PatientCareCenter.Views.GlobalsViews.showInformationAlert;

public class PrintDialog {

    private File pdfFile; // PDF file to be printed

    public PrintDialog(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Print Settings");

        ComboBox<String> printerComboBox = new ComboBox<>();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            printerComboBox.getItems().add(service.getName());
        }
        if (!printerComboBox.getItems().isEmpty()) {
            printerComboBox.setValue(printerComboBox.getItems().get(0)); // Set default printer
        }

        ComboBox<MediaSizeName> sizeComboBox = new ComboBox<>();
        populateMediaSizeComboBox(sizeComboBox);
        sizeComboBox.setValue(MediaSizeName.ISO_A4); // Default: A4

        ToggleGroup orientationGroup = new ToggleGroup();
        RadioButton portraitRadio = new RadioButton("Portrait");
        RadioButton landscapeRadio = new RadioButton("Landscape");
        portraitRadio.setToggleGroup(orientationGroup);
        landscapeRadio.setToggleGroup(orientationGroup);
        portraitRadio.setSelected(true);

        Spinner<Integer> copiesSpinner = new Spinner<>(1, 100, 1); // Min:1, Max:100, Default:1
        CheckBox showPrintDialog = new CheckBox("Show Print Dialog");
        showPrintDialog.setSelected(false); // Default: No print dialog

        // Print button
        Button printButton = new Button("Print");
        printButton.setOnAction(e -> printPDF(
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

    private void printPDF(String selectedPrinter, MediaSizeName paperSize, boolean isLandscape, int copies, boolean showDialog) {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(paperSize);
        printRequestAttributeSet.add(new Copies(copies));
        printRequestAttributeSet.add(isLandscape ? OrientationRequested.LANDSCAPE : OrientationRequested.PORTRAIT);
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService selectedService = Arrays.stream(services)
                .filter(service -> service.getName().equalsIgnoreCase(selectedPrinter.trim()))
                .findFirst()
                .orElse(null);

        if (selectedService == null) {
            showErrorAlert("Error: Printer not found!");
            return;
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFPageable pageable = new PDFPageable(document);
            DocPrintJob printJob = selectedService.createPrintJob();
            Doc doc = new SimpleDoc(pageable, DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);

            if (showDialog) {
                printJob.print(doc, printRequestAttributeSet); // Show print dialog
            } else {
                printJob.print(doc, null); // Print without dialog
            }

            showInformationAlert("Print successful!");
        } catch (IOException | PrintException e) {
            showErrorAlert("Error printing: " + e.getMessage());
        }
    }
}
