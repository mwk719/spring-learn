
package com.mwk.utils.poi;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

import java.io.IOException;
import java.io.InputStream;

/** 
* @Description 自定义 XWPFDocument，并重写 createPicture()方法 
* @author Minweikai
* @date 2016年9月7日 下午5:58:49  
*/
public class CustomXWPFDocument extends XWPFDocument {    
    public CustomXWPFDocument(InputStream in) throws IOException {    
        super(in);    
    }    
    
    public CustomXWPFDocument() {    
        super();    
    }    
    
    public CustomXWPFDocument(OPCPackage pkg) throws IOException {    
        super(pkg);    
    }    
    

    /** 
     * @param paragraph  段落 
     * @param 图片id 
     * @param width 宽 
     * @param height 高 
     * @param picAttch 图片后跟的内容
     */  
    public void createPicture(XWPFParagraph paragraph, String id, int width, int height, String picAttch) {   
    	createPicture(paragraph, id, width, height, picAttch, -1, true);
    }
    
    
    /** 
     * @param paragraph  段落 
     * @param 图片id 
     * @param width 宽 
     * @param height 高 
     * @param picAttch 图片后跟的内容
     */  
    public void createPicture(XWPFParagraph paragraph, String id, int width, int height, String picAttch, int runPos, Boolean isNewLine) {    
        final int EMU = 9525;    
        width *= EMU;    
        height *= EMU;    
        String blipId = id;

        Long docPrId = (long) blipId.hashCode();

        //调整加入图片所在的顺序位置
        runPos = runPos < 0 ? paragraph.getRuns().size() -1 : runPos;
        XWPFRun xwpfRun = isNewLine ? paragraph.getRuns().get(runPos) : paragraph.insertNewRun(runPos);
        CTInline inline = xwpfRun.getCTR().addNewDrawing().addNewInline();
        paragraph.insertNewRun(runPos).setText(picAttch);  
        
        String picXml = ""
                + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"    
                + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"    
                + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"    
                + "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""    
                + docPrId    
                + "\" name=\"Generated\"/>"    
                + "            <pic:cNvPicPr/>"    
                + "         </pic:nvPicPr>"    
                + "         <pic:blipFill>"    
                + "            <a:blip r:embed=\""    
                + blipId    
                + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"    
                + "            <a:stretch>"    
                + "               <a:fillRect/>"    
                + "            </a:stretch>"    
                + "         </pic:blipFill>"    
                + "         <pic:spPr>"    
                + "            <a:xfrm>"    
                + "               <a:off x=\"0\" y=\"0\"/>"    
                + "               <a:ext cx=\""    
                + width    
                + "\" cy=\""    
                + height    
                + "\"/>"    
                + "            </a:xfrm>"    
                + "            <a:prstGeom prst=\"rect\">"    
                + "               <a:avLst/>"    
                + "            </a:prstGeom>"    
                + "         </pic:spPr>"    
                + "      </pic:pic>"    
                + "   </a:graphicData>" + "</a:graphic>";    
    
        inline.addNewGraphic().addNewGraphicData();    
        XmlToken xmlToken = null;    
        try {    
            xmlToken = XmlToken.Factory.parse(picXml);    
        } catch (XmlException xe) {    
            xe.printStackTrace();    
        }    
        inline.set(xmlToken);   
          
        inline.setDistT(0);      
        inline.setDistB(0);      
        inline.setDistL(0);      
        inline.setDistR(0);      
          
        CTPositiveSize2D extent = inline.addNewExtent();    
        extent.setCx(width);    
        extent.setCy(height);    
          
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        
        docPr.setId(Long.valueOf(docPrId));      
        docPr.setName("图片" + docPrId);      
        docPr.setDescr("测试");   
    }
} 
