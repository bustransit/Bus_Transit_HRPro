/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.system.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author NelsonDelaTorre
 */

@Entity
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
    , @NamedQuery(name = "Department.findByDepartmentCode", query = "SELECT d FROM Department d WHERE d.departmentCode = :departmentCode")
    , @NamedQuery(name = "Department.findByDepartmentName", query = "SELECT d FROM Department d WHERE d.departmentName = :departmentName")})
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "department_code")
    private String departmentCode;
    @Basic(optional = false)
    @Column(name = "department_name")
    private String departmentName;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Lob
    @Column(name = "responsibilities")
    private String responsibilities;
    @OneToMany(mappedBy = "departmentCode")
    private Collection<EmployeePosition> employeePositionCollection;

    public Department() {
    }

    public Department(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Department(String departmentCode, String departmentName, String description, String responsibilities) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.description = description;
        this.responsibilities = responsibilities;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    @XmlTransient
    public Collection<EmployeePosition> getEmployeePositionCollection() {
        return employeePositionCollection;
    }

    public void setEmployeePositionCollection(Collection<EmployeePosition> employeePositionCollection) {
        this.employeePositionCollection = employeePositionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentCode != null ? departmentCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentCode == null && other.departmentCode != null) || (this.departmentCode != null && !this.departmentCode.equals(other.departmentCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bus_transit.system.entities.Department[ departmentCode=" + departmentCode + " ]";
    }

}
