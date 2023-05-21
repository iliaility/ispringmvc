package com.epam.springmvc.util;

import com.epam.springmvc.model.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGenerator {
    public static byte[] generatePdf(List<Ticket> tickets) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();

        for (Ticket ticket : tickets) {
            Paragraph paragraph1 = new Paragraph("Ticket ID: " + ticket.getId());
            Paragraph paragraph2 = new Paragraph("User ID: " + ticket.getUserId());
            Paragraph paragraph3 = new Paragraph("Event ID: " + ticket.getEventId());
            Paragraph paragraph4 = new Paragraph("Place: " + ticket.getPlace());
            Paragraph paragraph5 = new Paragraph("Category: " + ticket.getCategory());
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
        }

        document.close();
        writer.close();

        return outputStream.toByteArray();
    }
}