package org.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.data.entities.Employee;
import org.pages.WishBanner;

@Import(stylesheet = "EmployeeBirthday.css")
public class EmployeeBirthday {
    @Property
    @Parameter(required = true)
    private Employee employee;


    @InjectComponent
    private WishBanner wishBanner;

    void setup(Employee emp) {
        this.employee = emp;
    }
}
