package org.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.data.entities.Employee;
import org.data.services.EmployeeService;
public class IndividualEmployee {

    @InjectComponent
    private Zone birthdayZone;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Inject
    private EmployeeService employeeService;

    @Property
    private Employee employee;

    private Long employeeId;

    @Inject
    private JavaScriptSupport javaScriptSupport;
    @InjectComponent
    private Zone promotionZone;

    @OnEvent(value = "activate")
    void setup(Long employeeId) {
        employee = employeeService.getEmployeeById(employeeId);
    }

    // ActionLink to show image popup
    @OnEvent(value = "showImage")
    void showImage() {
        String imagePath = "/images/employee.jpg" + employee.getId() + ".jpg";
        javaScriptSupport.addScript("window.open('" + imagePath + "', '_blank', 'width=600,height=400');");
    }

    // EventLink to promote employee
    @OnEvent(value = "promote")
    void promoteEmployee() {
        employee.setDesignation("Manager");
        employeeService.updateEmployee(employee);
    }
    // Ajax update after promotion
    @OnEvent(value = "promote")
    Object updatePromotionZone() {
        return promotionZone.getBody();
    }

    // PageLink to logout
    @OnEvent(value = "logout")
    Object onLogout() {
        return Login.class;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    void onActivate(Long id) {
        this.employeeId = id;
        this.employee = employeeService.getEmployeeById(employeeId);
    }
    void onBirthdayHandled() {
        ajaxResponseRenderer.addRender(birthdayZone);
    }

//    public boolean isEmployeeBirthday() {
//        return employee.isBirthday();
//    }

    //    public boolean isEmployeeBirthday() {
//        return employee.isBirthday();
//    }


    Long onPassivate() {
        return employeeId;
    }
}