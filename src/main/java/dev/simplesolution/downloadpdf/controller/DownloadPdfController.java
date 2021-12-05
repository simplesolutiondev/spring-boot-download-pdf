package dev.simplesolution.downloadpdf.controller;

import dev.simplesolution.downloadpdf.entity.Customer;
import dev.simplesolution.downloadpdf.entity.ReceiptItem;
import dev.simplesolution.downloadpdf.service.ExportPdfService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DownloadPdfController {

    @Autowired
    private ExportPdfService exportPdfService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/downloadReceipt")
    public void downloadReceipt(HttpServletResponse response) throws IOException {
        Map<String, Object> data = createTestData();
        ByteArrayInputStream exportedData = exportPdfService.exportReceiptPdf("receipt", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
        IOUtils.copy(exportedData, response.getOutputStream());
    }

    private Map<String, Object> createTestData() {
        Map<String, Object> data = new HashMap<>();
        Customer customer = new Customer();
        customer.setCompanyName("Simple Solution");
        customer.setContactName("John Doe");
        customer.setAddress("123, Simple Street");
        customer.setEmail("contact@simplesolution.dev");
        customer.setPhone("123 456 789");
        data.put("customer", customer);

        List<ReceiptItem> receiptItems = new ArrayList<>();
        ReceiptItem receiptItem1 = new ReceiptItem();
        receiptItem1.setDescription("Test Item 1");
        receiptItem1.setQuantity(1);
        receiptItem1.setUnitPrice(100.0);
        receiptItem1.setTotal(100.0);
        receiptItems.add(receiptItem1);

        ReceiptItem receiptItem2 = new ReceiptItem();
        receiptItem2.setDescription("Test Item 2");
        receiptItem2.setQuantity(4);
        receiptItem2.setUnitPrice(500.0);
        receiptItem2.setTotal(2000.0);
        receiptItems.add(receiptItem2);

        ReceiptItem receiptItem3 = new ReceiptItem();
        receiptItem3.setDescription("Test Item 3");
        receiptItem3.setQuantity(2);
        receiptItem3.setUnitPrice(200.0);
        receiptItem3.setTotal(400.0);
        receiptItems.add(receiptItem3);

        data.put("receiptItems", receiptItems);
        return data;
    }
}
