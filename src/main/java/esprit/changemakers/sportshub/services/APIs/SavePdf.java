package esprit.changemakers.sportshub.services.APIs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Jozef
 */
public class SavePdf {


    public static void downloadPDF(List<byte[]> s, Window window){
        try {

            FileChooser dirChooser = new FileChooser();
            File selectedDir = dirChooser.showSaveDialog(window);
            if(selectedDir != null){
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(selectedDir.getAbsolutePath()+".pdf"));
                document.open();


                Font municipalityName = new Font();
                municipalityName.setStyle(Font.BOLD);
                municipalityName.setSize(18);


                Paragraph p0 = new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10));
                p0.add("Tunisian republic\n");
                p0.setAlignment(Element.ALIGN_CENTER);


                Paragraph p1 = new Paragraph("Aaslema"+"\n",municipalityName);
                p1.setAlignment(Element.ALIGN_CENTER);


                Paragraph p2 = new Paragraph("Exercice ejaw\n\n\n");
                p2.setAlignment(Element.ALIGN_CENTER);



                document.add(p0);
                document.add(p1);
                document.add(p2);
                document.add(new LineSeparator());

                for (int i = 0; i < s.size(); i++) {
                    //"src/main/resources/assets/imgs/imgHome.jpg"
                    //com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(s.get(i).gIn);
                    com.itextpdf.text.Image img =Image.getInstance(s.get(i));
                    //maintaining aspect ratio
                    float imgW = 600;
                    float imgH = 700;
                    float padding = 60;
                    img.setAlignment(Element.ALIGN_CENTER);
                    //img.setAbsolutePosition(padding,document.getPageSize().getHeight() - (imgH+padding));
                    Paragraph p9 = new Paragraph("\n\n\n\n\n\n\n\n\n");
                    document.add(p9);
                    //img.scaleAbsolute(imgW,imgH);
                    document.add(img);
                }






                document.close();
                writer.close();

            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("itext PDF program executed");
    }
}
