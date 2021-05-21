package com.mwk.utils.poi;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordReporter {
    private String tempLocalPath;
    private XWPFDocument xwpfDocument = null;
    private FileInputStream inputStream = null;
    private OutputStream outputStream = null;

    public WordReporter(){

    }
    public WordReporter(String tempLocalPath){
        this.tempLocalPath = tempLocalPath;
    }

    /**
     *  设置模板路径
     * @param tempLocalPath
     */
    public void setTempLocalPath(String tempLocalPath) {
        this.tempLocalPath = tempLocalPath;
    }

    /**
     *  初始化
     * @throws IOException
     */
    public void init() throws IOException {
        inputStream = new FileInputStream(new File(this.tempLocalPath));
        xwpfDocument = new XWPFDocument(inputStream);
    }

    /**
     *  导出方法
     * @param params
     * @param tableIndex
     * @return
     * @throws Exception
     */
    public boolean export(Map<String,Object> param, List<Map<String,String>> params, int tableIndex) throws Exception{
        this.insertValueToTable(xwpfDocument, param, params,tableIndex);
        return true;
    }

    public boolean export(Map<String,Object> params) throws Exception{
        this.replaceInPara(xwpfDocument, params);
        return true;
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private boolean matcherRow(String str) {
        Pattern pattern = Pattern.compile("\\$\\[(.+?)\\]",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 替换段落里面的变量
     *
     * @param doc
     *            要替换的文档
     * @param params
     *            参数
     * @throws Exception
     */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) throws Exception {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para
     *            要替换的段落
     * @param params
     *            参数
     * @throws Exception
     * @throws IOException
     */
    private boolean replaceInPara(XWPFParagraph para, Map<String, Object> params) throws Exception {
        boolean data = false;
        List<XWPFRun> runs;
        //有符合条件的占位符
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            data = true;
            Map<Integer,String> tempMap = new HashMap<Integer,String>();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                //以"$"开头
                boolean begin = runText.indexOf("$")>-1;
                boolean end = runText.indexOf("}")>-1;
                if(begin && end){
                    tempMap.put(i, runText);
                    fillBlock(para, params, tempMap, i);
                    continue;
                }else if(begin && !end){
                    tempMap.put(i, runText);
                    continue;
                }else if(!begin && end){
                    tempMap.put(i, runText);
                    fillBlock(para, params, tempMap, i);
                    continue;
                }else{
                    if(tempMap.size()>0){
                        tempMap.put(i, runText);
                        continue;
                    }
                    continue;
                }
            }
        } else if (this.matcherRow(para.getParagraphText())) {
            runs = para.getRuns();
            data = true;
        }
        return data;
    }

    /**
     * 填充run内容
     * @param para
     * @param params
     * @param tempMap
     * @throws InvalidFormatException
     * @throws IOException
     * @throws Exception
     */
    private void fillBlock(XWPFParagraph para, Map<String, Object> params,
                           Map<Integer, String> tempMap, int index)
            throws InvalidFormatException, IOException, Exception {
        Matcher matcher;
        if(tempMap!=null&&tempMap.size()>0){
            String wholeText = "";
            List<Integer> tempIndexList = new ArrayList<Integer>();
            for(Map.Entry<Integer, String> entry :tempMap.entrySet()){
                tempIndexList.add(entry.getKey());
                wholeText+=entry.getValue();
            }
            if(wholeText.equals("")){
                return;
            }
            matcher = this.matcher(wholeText);
            if (matcher.find()) {
                boolean isPic = false;
                int width = 0;
                int height = 0;
                int picType = 0;
                String path = null;
                String keyText = matcher.group().substring(2,matcher.group().length()-1);
                Object value = params.get(keyText);
                String newRunText = "";
                if(value instanceof String){
                    newRunText = matcher.replaceFirst(String.valueOf(value));
                }else if(value instanceof Map){//插入图片
                    isPic = true;
                    Map pic = (Map)value;
                    width = Integer.parseInt(pic.get("width").toString());
                    height = Integer.parseInt(pic.get("height").toString());
                    picType = getPictureType(pic.get("type").toString());
                    path = pic.get("path").toString();
                }

                //模板样式
                XWPFRun tempRun = null;
                // 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                for(Integer pos : tempIndexList){
                    tempRun = para.getRuns().get(pos);
                    tempRun.setText("", 0);
                }
                if(isPic){
                    //addPicture方法的最后两个参数必须用Units.toEMU转化一下
                    //para.insertNewRun(index).addPicture(getPicStream(path), picType, "测试",Units.toEMU(width), Units.toEMU(height));
                    tempRun.addPicture(getPicStream(path), picType, "测试", Units.toEMU(width), Units.toEMU(height));
                }else{
                    //样式继承
                    if(newRunText.indexOf("\n")>-1){
                        String[] textArr = newRunText.split("\n");
                        if(textArr.length>0){
                            //设置字体信息
                            String fontFamily = tempRun.getFontFamily();
                            int fontSize = tempRun.getFontSize();
                            //logger.info("------------------"+fontSize);
                            for(int i=0;i<textArr.length;i++){
                                if(i==0){
                                    tempRun.setText(textArr[0],0);
                                }else{
                                    if(StrUtil.isNotEmpty(textArr[i])){
                                        XWPFRun newRun=para.createRun();
                                        //设置新的run的字体信息
                                        newRun.setFontFamily(fontFamily);
                                        if(fontSize==-1){
                                            newRun.setFontSize(10);
                                        }else{
                                            newRun.setFontSize(fontSize);
                                        }
                                        newRun.addBreak();
                                        newRun.setText(textArr[i], 0);
                                    }
                                }
                            }
                        }
                    }else{
                        tempRun.setText(newRunText,0);
                    }
                }
            }
            tempMap.clear();
        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private int getPictureType(String picType){
        int res = XWPFDocument.PICTURE_TYPE_PICT;
        if(picType != null){
            if(picType.equalsIgnoreCase("png")){
                res = XWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = XWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = XWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = XWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = XWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    private InputStream getPicStream(String picPath) throws Exception{
        URL url = new URL(picPath);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream is = conn.getInputStream();
        return is;
    }

    /**
     * 循环填充表格内容
     * @param xwpfDocument
     * @param params
     * @param tableIndex
     * @throws Exception
     */
    private void insertValueToTable(XWPFDocument xwpfDocument, Map<String, Object> param, List<Map<String,String>> params, int tableIndex) throws Exception {
        List<XWPFTable> tableList = xwpfDocument.getTables();
        if(tableList.size()<=tableIndex){
            throw new Exception("tableIndex对应的表格不存在");
        }
        XWPFTable table = tableList.get(tableIndex);
        List<XWPFTableRow> rows = table.getRows();
        if(rows.size()<2){
            throw new Exception("tableIndex对应表格应该为2行");
        }


        List<XWPFTableRow> TempTableRows = table.getRows();
        int forEachRowsStartIndex = 0;// 标签行indexs
        int forEachRowsEndIndex = 0;// 标签行indexs

        int tmpTableTotalRows = TempTableRows.size();

        for (int i = 0, size = tmpTableTotalRows; i < size; i++) {
            String rowText = TempTableRows.get(i).getCell(0).getText();// 获取到表格行的第一个单元格
            if (rowText.indexOf("##{foreachRowsStart}##") > -1) {
                forEachRowsStartIndex = i;
            } else if(rowText.indexOf("##{foreachRowsEnd}##") > -1) {
                forEachRowsEndIndex = i;
                break;
            }
        }


        XWPFTableRow tmpRow = null;

        /* 复制模板中循环体之前的行内容 */
        for (int i = 0; i < forEachRowsStartIndex; i++) {
            tmpRow = rows.get(i);
            replaceRowValue(param, tmpRow);
//            int size = table.getRows().size();
            table.addRow(tmpRow);
        }

        /* 循环生成模板行 */
        //循环内容的开始index
        int forEachContentIndex = forEachRowsStartIndex + 1;
        forEachRowsEndIndex = forEachRowsEndIndex > forEachContentIndex ? forEachRowsEndIndex : forEachContentIndex + 1;

        for (int i = 0, len = params.size(); i < len; i++) {
            for (int j = forEachContentIndex; j < forEachRowsEndIndex; j++) {
                tmpRow = TempTableRows.get(j); // 获取到模板行

                Map map = params.get(i);


                // 创建新的一行, 新增行的样式参考 https://ask.csdn.net/questions/654185
//                table.addRow(tmpRow);
//                System.out.println(rows.size()-1);
//                System.out.println(j);
//                System.out.println(tmpRow == table.getRow(rows.size()-1));

//                XWPFTableRow row = new XWPFTableRow(table.getCTTbl().addNewTr(), table);
//                addColumn(row, table.getCTTbl().getTrArray(j).sizeOfTcArray());

//                table.getCTTbl().addNewTr();
//                table.getCTTbl().setTrArray(getNumberOfRows()-1, row.getCtRow());
//                tableRows.add(row);

                 //在table最后一列创建一行
                 XWPFTableRow row = new XWPFTableRow(table.getCTTbl().addNewTr(), table);
                 //复制tmpRow的样式到row中
                 copyTableRowStyle(row, tmpRow);
                 //给row复制
                 copyTableRow(tmpRow, row, map);
            }
        }


        /* 复制模板中循环体之后的行内容 */
        for (int i = forEachRowsEndIndex + 1; i < tmpTableTotalRows; i++) {
            tmpRow = rows.get(i);
            replaceRowValue(param, tmpRow);
//            int size = table.getRows().size();
            table.addRow(tmpRow);
        }


        //记录最终生成的table中  forEachRowsEndIndex 的行号, 用于删除 ##{forEachRowsEndIndex}## 标签 及以上的标签
//        int finalForEachRowsEndIndex = 0;
//
//        List<XWPFTableRow> finalRows = table.getRows();
//        for (int i = 0; i < finalRows.size(); i++) {
//            String rowText = finalRows.get(i).getCell(0).getText();// 获取到表格行的第一个单元格
//            System.out.println(rowText);
//            if (rowText.indexOf("##{foreachRowsEnd}##") > -1) {
//                finalForEachRowsEndIndex = i;
//                break;
//            }
//        }
//        if(finalForEachRowsEndIndex > 0) {
//            System.out.println("finalForEachRowsEndIndex : " + finalForEachRowsEndIndex);
//            int forEarchRowOffset = forEachRowsEndIndex - forEachRowsStartIndex;
//            for (int i = forEarchRowOffset; i > 0 ; i--) {
//                table.removeRow(finalForEachRowsEndIndex - i);
//            }
//        }
//
        /* 删除模板行 */
        for (int i = tmpTableTotalRows - 1; i >= 0 ; i--) {
            table.removeRow(i);
        }


//        //模板的那一行
//        XWPFTableRow tmpRow = rows.get(1);
//        List<XWPFTableCell> tmpCells = null;
//        List<XWPFTableCell> cells = null;
//        XWPFTableCell tmpCell = null;
//        tmpCells = tmpRow.getTableCells();
//
//
//        String cellText = null;
//        String cellTextKey = null;
//        Map<String,Object> totalMap = null;
//        for (int i = 0, len = params.size(); i < len; i++) {
//            Map map = params.get(i);
//            // 创建新的一行
//            XWPFTableRow row = table.createRow();
//            copyTableRow(tmpRow, row, map);
//        }
//
//
//        tmpRow = rows.get(2);
//
//        replaceRowValue(param, tmpRow);
//        int size = table.getRows().size();
//        table.addRow(tmpRow, size);

        // 删除模版行
//        table.removeRow(1);
//        table.removeRow(2);
    }

    private void addColumn(XWPFTableRow tabRow, int sizeCol) {
        if (sizeCol > 0) {
            for (int i = 0; i < sizeCol; i++) {
                tabRow.addNewTableCell();
            }
        }
    }

    private void replaceRowValue(Map<String, Object> param, XWPFTableRow tmpRow) {
        List<XWPFTableCell> tmpCells;
        List<XWPFTableCell> cells;
        XWPFTableCell tmpCell;
        String cellText;
        String cellTextKey;
        tmpCells = tmpRow.getTableCells();
        cells = tmpRow.getTableCells();
        for (int k = 0, klen = cells.size(); k < klen; k++) {
            tmpCell = tmpCells.get(k);
            XWPFTableCell cell = cells.get(k);
            cellText = tmpCell.getText();
            if (StrUtil.isNotBlank(cellText)) {
                //转换为mapkey对应的字段
                cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
                if (param.containsKey(cellTextKey)) {
                    //删除已有占位符
                    cell.removeParagraph(0);
                    // 填充内容 并且复制模板行的属性
                    cell.setText(param.get(cellTextKey).toString());
                }
            }
        }
    }


    private void copyTableRow(XWPFTableRow tmpRow, XWPFTableRow row, Map<String, Object> map) throws Exception {
        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
        List<XWPFTableCell> cells;
        XWPFTableCell tmpCell;
        String cellText;
        String cellTextKey;// 获取模板的行高 设置为新一行的行高
        row.setHeight(tmpRow.getHeight());
        cells = row.getTableCells();
        for (int k = 0, klen = cells.size(); k < klen; k++) {
            tmpCell = tmpCells.get(k);
            XWPFTableCell cell = cells.get(k);
            cellText = tmpCell.getText();
            if (StrUtil.isNotBlank(cellText)) {
                //转换为mapkey对应的字段
                cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
                if (map.containsKey(cellTextKey)) {
                    // 填充内容 并且复制模板行的属性
                    setCellText(tmpCell,cell ,map.get(cellTextKey).toString());
                }
            }
        }
    }






    /**
     *  复制模板行的属性
     * @param tmpCell
     * @param cell
     * @param text
     * @throws Exception
     */
    private void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell,String text) throws Exception {

        CTTc cttc2 = tmpCell.getCTTc();
        CTTcPr ctPr2 = cttc2.getTcPr();
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        if (ctPr2.getTcW() != null) {
            ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
        }
        if (ctPr2.getVAlign() != null) {
            ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
        }
        if (cttc2.getPList().size() > 0) {
            CTP ctp = cttc2.getPList().get(0);
            if (ctp.getPPr() != null) {
                if (ctp.getPPr().getJc() != null) {
                    cttc.getPList().get(0).addNewPPr().addNewJc()
                            .setVal(ctp.getPPr().getJc().getVal());
                }
            }
        }
        if (ctPr2.getTcBorders() != null) {
            ctPr.setTcBorders(ctPr2.getTcBorders());
        }

        XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
        XWPFParagraph cellP = cell.getParagraphs().get(0);

        //清空原来的值
//        for (XWPFRun o : cellP.getRuns()) {
//            o.setText(null, 0);
//        }

        XWPFRun tmpR = null;
        if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {
            tmpR = tmpP.getRuns().get(0);
        }
        XWPFRun cellR = cellP.createRun();
        cellR.setText(text);
        // 复制字体信息
        if (tmpR != null) {
            if(!cellR.isBold()){
                cellR.setBold(tmpR.isBold());
            }
            cellR.setItalic(tmpR.isItalic());
            cellR.setUnderline(tmpR.getUnderline());
            cellR.setColor(tmpR.getColor());
            cellR.setTextPosition(tmpR.getTextPosition());
            if (tmpR.getFontSize() != -1) {
                cellR.setFontSize(tmpR.getFontSize());
            }
            if (tmpR.getFontFamily() != null) {
                cellR.setFontFamily(tmpR.getFontFamily());
            }
            if (tmpR.getCTR() != null) {
                if (tmpR.getCTR().isSetRPr()) {
                    CTRPr tmpRPr = tmpR.getCTR().getRPr();
                    if (tmpRPr.isSetRFonts()) {
                        CTFonts tmpFonts = tmpRPr.getRFonts();
                        CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR
                                .getCTR().getRPr() : cellR.getCTR().addNewRPr();
                        CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr
                                .getRFonts() : cellRPr.addNewRFonts();
                        cellFonts.setAscii(tmpFonts.getAscii());
                        cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
                        cellFonts.setCs(tmpFonts.getCs());
                        cellFonts.setCstheme(tmpFonts.getCstheme());
                        cellFonts.setEastAsia(tmpFonts.getEastAsia());
                        cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
                        cellFonts.setHAnsi(tmpFonts.getHAnsi());
                        cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
                    }
                }
            }

        }
        // 复制段落信息
        cellP.setAlignment(tmpP.getAlignment());
        cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
        cellP.setBorderBetween(tmpP.getBorderBetween());
        cellP.setBorderBottom(tmpP.getBorderBottom());
        cellP.setBorderLeft(tmpP.getBorderLeft());
        cellP.setBorderRight(tmpP.getBorderRight());
        cellP.setBorderTop(tmpP.getBorderTop());
        cellP.setPageBreak(tmpP.isPageBreak());
        if (tmpP.getCTP() != null) {
            if (tmpP.getCTP().getPPr() != null) {
                CTPPr tmpPPr = tmpP.getCTP().getPPr();
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP
                        .getCTP().getPPr() : cellP.getCTP().addNewPPr();
                // 复制段落间距信息
                CTSpacing tmpSpacing = tmpPPr.getSpacing();
                if (tmpSpacing != null) {
                    CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr
                            .getSpacing() : cellPPr.addNewSpacing();
                    if (tmpSpacing.getAfter() != null) {
                        cellSpacing.setAfter(tmpSpacing.getAfter());
                    }
                    if (tmpSpacing.getAfterAutospacing() != null) {
                        cellSpacing.setAfterAutospacing(tmpSpacing
                                .getAfterAutospacing());
                    }
                    if (tmpSpacing.getAfterLines() != null) {
                        cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
                    }
                    if (tmpSpacing.getBefore() != null) {
                        cellSpacing.setBefore(tmpSpacing.getBefore());
                    }
                    if (tmpSpacing.getBeforeAutospacing() != null) {
                        cellSpacing.setBeforeAutospacing(tmpSpacing
                                .getBeforeAutospacing());
                    }
                    if (tmpSpacing.getBeforeLines() != null) {
                        cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
                    }
                    if (tmpSpacing.getLine() != null) {
                        cellSpacing.setLine(tmpSpacing.getLine());
                    }
                    if (tmpSpacing.getLineRule() != null) {
                        cellSpacing.setLineRule(tmpSpacing.getLineRule());
                    }
                }
                // 复制段落缩进信息
                CTInd tmpInd = tmpPPr.getInd();
                if (tmpInd != null) {
                    CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd()
                            : cellPPr.addNewInd();
                    if (tmpInd.getFirstLine() != null) {
                        cellInd.setFirstLine(tmpInd.getFirstLine());
                    }
                    if (tmpInd.getFirstLineChars() != null) {
                        cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
                    }
                    if (tmpInd.getHanging() != null) {
                        cellInd.setHanging(tmpInd.getHanging());
                    }
                    if (tmpInd.getHangingChars() != null) {
                        cellInd.setHangingChars(tmpInd.getHangingChars());
                    }
                    if (tmpInd.getLeft() != null) {
                        cellInd.setLeft(tmpInd.getLeft());
                    }
                    if (tmpInd.getLeftChars() != null) {
                        cellInd.setLeftChars(tmpInd.getLeftChars());
                    }
                    if (tmpInd.getRight() != null) {
                        cellInd.setRight(tmpInd.getRight());
                    }
                    if (tmpInd.getRightChars() != null) {
                        cellInd.setRightChars(tmpInd.getRightChars());
                    }
                }
            }
        }
    }















    /**
     *
     * 复制RUN，从source到target
     * @param target
     * @param source
     *
     */
    public void copyRun(XWPFRun target, XWPFRun source, Boolean isCopyContent) {
        // 设置run属性
        target.getCTR().setRPr(source.getCTR().getRPr());
        if(isCopyContent) {
            // 设置文本
            target.setText(source.getText(0));
            // 处理图片
            List<XWPFPicture> pictures = source.getEmbeddedPictures();


            for (XWPFPicture picture : pictures) {
                try {
                    copyPicture(target, picture);
                } catch (InvalidFormatException e) {
                    //                logger.error("copyRun", e);
                    e.printStackTrace();
                } catch (IOException e) {
                    //                logger.error("copyRun", e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * 复制图片到target
     * @param target
     * @param picture
     * @throws IOException
     * @throws InvalidFormatException
     *
     */
    public void copyPicture(XWPFRun target, XWPFPicture picture)throws IOException, InvalidFormatException {

        String filename = picture.getPictureData().getFileName();
        InputStream pictureData = new ByteArrayInputStream(picture
                .getPictureData().getData());
        int pictureType = picture.getPictureData().getPictureType();
        int width = (int) picture.getCTPicture().getSpPr().getXfrm().getExt()
                .getCx();

        int height = (int) picture.getCTPicture().getSpPr().getXfrm().getExt()
                .getCy();

        // target.addBreak();
        try {
            target.addPicture(pictureData, pictureType, filename, width, height);
        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        // target.addBreak(BreakType.PAGE);
    }

    /**
     * 复制段落，从source到target
     * @param target
     * @param source
     *
     */
    public void copyParagraph(XWPFParagraph target, XWPFParagraph source, Boolean isCopyContent) {

        // 设置段落样式
        target.getCTP().setPPr(source.getCTP().getPPr());

        // 移除所有的run
        for (int pos = target.getRuns().size() - 1; pos >= 0; pos--) {
            target.removeRun(pos);
        }

        // copy 新的run
        for (XWPFRun s : source.getRuns()) {
            XWPFRun targetrun = target.createRun();
            copyRun(targetrun, s, isCopyContent);
        }

    }

    /**
     * 复制单元格，从source到target
     * @param target
     * @param source
     *
     */
    public void copyTableCell(XWPFTableCell target, XWPFTableCell source, Boolean isCopyContent) {
        // 列属性
        if (source.getCTTc() != null) {
            target.getCTTc().setTcPr(source.getCTTc().getTcPr());
        }
        // 删除段落
        for (int pos = 0; pos < target.getParagraphs().size(); pos++) {
            target.removeParagraph(pos);
        }
        // 添加段落
        for (XWPFParagraph sp : source.getParagraphs()) {
            XWPFParagraph targetP = target.addParagraph();
            copyParagraph(targetP, sp, isCopyContent);
        }
    }

    /**
     *
     * 复制行，从source到target
     * @param target
     * @param source
     *
     */
    public void copyTableRow(XWPFTableRow target, XWPFTableRow source, Boolean isCopyContent) {
        // 复制样式
        if (source.getCtRow() != null) {
            target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        }
        // 复制单元格
        for (int i = 0; i < source.getTableCells().size(); i++) {
            XWPFTableCell cell1 = target.getCell(i);
            XWPFTableCell cell2 = source.getCell(i);
            if (cell1 == null) {
                cell1 = target.addNewTableCell();
            }
            copyTableCell(cell1, cell2, isCopyContent);
        }
    }

    public void copyTableRowStyle(XWPFTableRow target, XWPFTableRow source) {
        copyTableRow(target, source, false);
    }

    /**
     * 复制表，从source到target
     * @param target
     * @param source
     */
    public void copyTable(XWPFTable target, XWPFTable source) {
        // 表格属性
        target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());

        // 复制行
        for (int i = 0; i < source.getRows().size(); i++) {
            XWPFTableRow row1 = target.getRow(i);
            XWPFTableRow row2 = source.getRow(i);
            if (row1 == null) {
                target.addRow(row2);
            } else {
                copyTableRow(row1, row2, true);
            }
        }
    }








    /**
     *  收尾方法
     * @param outDocPath
     * @return
     * @throws IOException
     */
    public boolean generate(String outDocPath) throws IOException{
        outputStream = new FileOutputStream(outDocPath);
        xwpfDocument.write(outputStream);
        this.close(outputStream);
        this.close(inputStream);
        return true;
    }

    /**
     *  关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
