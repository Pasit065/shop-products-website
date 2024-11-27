
package com.web_application.main_website.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.web_application.main_website.AllProductData;
import com.web_application.main_website.DatabaseRepository;
import com.web_application.main_website.PostNewDataBaseDataException;
import com.web_application.main_website.ResourceNotFoundException;
import com.web_application.main_website.TimeManager;
import com.web_application.main_website.constant.SqliteQueries;
import com.web_application.main_website.model.ClientForm;
import com.web_application.main_website.model.Receipt;

@Controller
public class FormController {
    static String parseObjectValueHashMapToString(HashMap hashObj, String paramName) {
        return hashObj.get("client_id").toString();
    }

    // Buying product form handler section
    @GetMapping("/buy-product-form")
    public String showBuyProductForm(Model model) {
        ClientForm clientForm = new ClientForm();

        AllProductData allProductData = new AllProductData();
        allProductData.readProductsDataFileAndGetproductsJsonArrayData();

        List<String> listProducts = Arrays.asList("SIM_STANDARD", "SIM_PREMIUM", "phone_case");
        
        System.out.println(allProductData.getProductsDetail());
        model.addAttribute("allProductsDetail", allProductData.getProductsDetail());
        model.addAttribute("clientForm", clientForm);
        model.addAttribute("listProducts", listProducts);
        // The returned values must be matched to exist html file in template!!!!.
        // Returned values will define which html file matched with this route.
        return "buy-product-form";
    }

    @PostMapping("/buy-product-form/save")
    public String submitForm(Model model, @ModelAttribute("clientForm") ClientForm clientForm) {
        Connection con;
        AllProductData allProductData;
        LocalDateTime now;
        DatabaseRepository databaseRepository;
        TimeManager timeManager;
        SqliteQueries sqliteQueries;
        Receipt receipt;
        JSONObject buyProductDetails;
        Float buyProductPrice, totalPayment;
        boolean isUpsertCompleted, isInsertCompleted = true;
        String receiptTimeFormatted, failedMessage = """
                Conflict is occur here are the reason:
                Make sure that client id can be registered by only one name and surname
                you can't uses the same client id for multiple clients and you can't access multiple client id with the same name and surname
                that exists. Lastly, exception also occur because the payment haven't been done correctly.
                """;

        // Determine every object.
        timeManager = new TimeManager();
        databaseRepository = new DatabaseRepository();
        sqliteQueries = new SqliteQueries();
        allProductData = new AllProductData();

        // Get formatted time string.
        now = timeManager.getNowLocalDatetime();
        receiptTimeFormatted = timeManager.getDateTimeToFormattedDateTime(now, "yyyy-MM-dd HH:mm:ss");

        // Get all products data.
        allProductData = new AllProductData();
        allProductData.readProductsDataFileAndGetproductsJsonArrayData();

        buyProductDetails = allProductData.getProductDetailBySpecificTitle(clientForm.getProductNameBuy());

        // Connect database.
        con = databaseRepository.connectDatabase();

        // Get product name and price and assign it to receipt object.
        buyProductPrice = Float.valueOf(buyProductDetails.get("price").toString());
        // Determine receipt object.
        receipt = new Receipt(clientForm, clientForm.getProductNameBuy(), buyProductPrice);
        receipt.setReceiptTime(receiptTimeFormatted);

        // Get total payment.
        totalPayment = databaseRepository.getTotalPayment(sqliteQueries.selectToGetTotalPayment, con, clientForm.getClientId());
        clientForm.setTotalPayment(totalPayment + receipt.getPrice());
        
        // Set client status.
        clientForm.setStatusRefernceFromPayment();
        model.addAttribute("clientForm", clientForm);
        model.addAttribute("receipt", receipt);


        // Upsert clients table and insert new receipt table a row.

        isUpsertCompleted = databaseRepository.upsertClientDetailToClientsTable(sqliteQueries, con, clientForm); 
        System.out.println("is upsert completed: " + isUpsertCompleted);

        if (isUpsertCompleted) {
            isInsertCompleted = databaseRepository.insertReceiptDetailToReceiptTable(sqliteQueries, con, receipt);
            System.out.println("is insert completed: " + isInsertCompleted);
        }

        try {
			if (con != null) {
				con.close();
			}
		} catch(SQLException e) {
			databaseRepository.notifySQLException(e);
		}

        try {
            if (!isInsertCompleted || !isUpsertCompleted) {
                throw new PostNewDataBaseDataException("clients table or receipts haven't been updated.");
            }

        } catch(PostNewDataBaseDataException e) {
            model.addAttribute("failedMessage", failedMessage);
            return "buy-product-failed-page";
        }
    
        clientForm.showClientDetail();
        
        return "buy-success-page";
    }

    @GetMapping("get-client-detail-form")
    public String showGetClientDetailForm(Model model) {
        ClientForm clientForm = new ClientForm();

        model.addAttribute("clientForm", clientForm);

        return "get-client-detail-form";
    }

    // Get client detail form.
    @GetMapping("get-client-detail-form/search")
    public String submitGetClientDetailForm(Model model, @ModelAttribute("clientForm") ClientForm clientForm) {
        ClientForm newClientForm;
        Connection con;
        DatabaseRepository databaseRepository;
        SqliteQueries sqliteQueries;
        Integer totalReceipts;

        // Connect database.
        databaseRepository = new DatabaseRepository();
        sqliteQueries = new SqliteQueries();

        // Connect database.
        con = databaseRepository.connectDatabase();

        newClientForm = databaseRepository.getClientDetailFromSpecificedClientId(sqliteQueries, con, clientForm.getClientId());
        totalReceipts = databaseRepository.getTotalReceipts(sqliteQueries.selectTotalReceipt, con, clientForm.getClientId());

        System.out.println("Total receipt is: " + totalReceipts);

        try {
            if (con != null) {
                con.close();
            }
        } catch(SQLException e) {
            databaseRepository.notifySQLException(e);
        }

        model.addAttribute("clientForm", clientForm);
        model.addAttribute("totalReceipts", totalReceipts);

        try {
		    if (newClientForm.getName() == null || totalReceipts == null) {
                throw new ResourceNotFoundException("Cannot found client id.");
            }

        } catch (ResourceNotFoundException e) {
            return "client-detail-not-found-page";
        }

        model.addAttribute("clientForm", newClientForm);
        model.addAttribute("totalReceipt", totalReceipts);

        return "client-detail-found-page";
    }
}