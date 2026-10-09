package com.spo.core_app.Service;


import com.spo.core_app.Repository.OperationRepository;
import com.spo.core_app.models.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperationService {
    private OperationRepository opr;

    @Autowired
    public OperationService(OperationRepository oprepo) {
        this.opr = oprepo;

    }

    public List<Operation> fetchAllTheProcurementMainOperations() {
        return opr.findByOperationNameIn(List.of(
                "PURCHASE_REQUISITION_CREATION",
                "PURCHASE_REQUISITION_APPROVAL",
                "CREATE_PR",
                "VIEW_PR",
                "PURCHASE_ORDER_CREATION",
                "CREATE_PO",
                "PO_APPROVAL_WORKFLOW",
                "PO_CHANGE_AND_AMENDMENT",
                "CATALOG_MANAGEMENT",
                "GOODS_RECEIPT_CREATION",
                "GOODS_RECEIPT_VERIFICATION",
                "INSPECTION_AND_ACCEPTANCE",
                "RETURN_TO_SUPPLIER_RTS",
                "INVOICE_RECEIPT",
                "INVOICE_VALIDATION",
                "INVOICE_MATCHING_3_WAY",
                "INVOICE_DISPUTE_MANAGEMENT",
                "INVOICE_APPROVAL",
                "VIEW_SUPPLIERS"
        )
            );
    }

    public List<Operation> fetchOperationsByName(List<String> operationNames) {
        List<Operation> operations = new ArrayList<>();
        for (String operation : operationNames) {
            java.util.Optional<Operation> operation1 = opr.findByOperationName(operation);
            if (operation1.isEmpty()) {
                log.error("Operation with name " + operation + "does not exist");
                continue;
            }
            operations.add(operation1.get());
        }
        return operations;
    }
    public List<Operation> fetchAllTheSupplierAdminOperations() {

        return opr.findByOperationNameIn(List.of(

                "VIEW_PURCHASE_ORDERS",

                "SUBMIT_QUOTATION",

                "UPLOAD_INVOICE",

                "UPDATE_SUPPLIER_PROFILE",

                "INVITE_SUPPLIER_EMPLOYEE",

                "VIEW_COMPANY_PROFILE"

        ));
    }

}
