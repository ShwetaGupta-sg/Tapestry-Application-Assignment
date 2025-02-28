package org.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.data.entities.Employee;

@Import(stylesheet = "WishBanner.css")
public class WishBanner {

        @Property
        private String color;
    @Property
    private Employee employee;

    void setup(Employee employee) {
        this.employee = employee;
        color = employee.getGender().equalsIgnoreCase("female") ? "pink" : "blue";
    }
}
