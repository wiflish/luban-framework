package com.wiflish.luban.framework.excel.core.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.wiflish.luban.framework.common.core.KeyValue;
import com.wiflish.luban.framework.excel.core.enums.ExcelColumn;
import com.wiflish.luban.framework.excel.core.handler.SelectSheetWriteHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Excel 工具类
 *
 * @author wiflish
 */
public class ExcelUtils {

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        write(response, filename, sheetName, head, data, null);
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param selectMap 下拉选择数据 Map<下拉所对应的列表名，下拉数据>
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data, List<KeyValue<ExcelColumn, List<String>>> selectMap) throws IOException {
        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 输出 Excel
        EasyExcel.write(response.getOutputStream(), head)
                .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap)) // 基于固定 sheet 实现下拉框
                .registerConverter(new LongStringConverter()) // 避免 Long 类型丢失精度
                .sheet(sheetName).doWrite(data);
        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    }

    /**
     * 写入多Sheet的Excel文件
     *
     * @param response 响应对象
     * @param filename 文件名
     * @param sheets   Sheet列表，每个元素是一个Map，包含sheetName和data
     * @throws IOException IO异常
     */
    public static <T> void writeMultiSheet(HttpServletResponse response, String filename, List<Map<String, Object>> sheets) throws IOException {
        // 设置 header 和 contentType
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");//

        // 输出 Excel
        try (var out = response.getOutputStream()) {
            ExcelWriter excelWriter = EasyExcel.write(out)
                    .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                    .registerConverter(new LongStringConverter()).build(); // 避免 Long 类型丢失精度

            for (Map<String, Object> sheet : sheets) {
                String sheetName = (String) sheet.get("sheetName");
                Class<?> head = (Class<?>) sheet.get("head");
                List<?> data = (List<?>) sheet.get("data");
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).head(head).build();
                excelWriter.write(data, writeSheet);
            }
            excelWriter.finish();
        }
    }

    public static <T> List<T> read(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

}
