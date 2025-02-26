package org.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.data.entities.Employee;
import org.data.services.EmployeeService;

public class DeleteEmployee {
    @Inject
    private EmployeeService employeeService;
    @Inject
    PageRenderLinkSource pageRenderLinkSource;
    @Property
    private Long employeeId;

    @Component(id = "deleteEmployeeForm")
    private Form deleteEmployeeForm;

    void onActivate(Long employeeId) {
        this.employeeId = employeeId;
    }

    Long onPassivate() {
        return employeeId;
    }
    void setupRender() {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalStateException("Invalid Employee ID");
        }
    }

    void onValidateFromDeleteEmployeeForm() {
        if (employeeId == null || employeeId <= 0) {
            deleteEmployeeForm.recordError("Invalid employee ID.");
        }
    }

    Object onSuccessFromDeleteEmployeeForm() {
        employeeService.deleteEmployee(employeeId);
        return pageRenderLinkSource.createPageRenderLink(EmployeeList.class); // Redirect after successful delete
    }

}
