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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "employee_position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeePosition.findAll", query = "SELECT e FROM EmployeePosition e")
    , @NamedQuery(name = "EmployeePosition.findByPositionId", query = "SELECT e FROM EmployeePosition e WHERE e.positionId = :positionId")
    , @NamedQuery(name = "EmployeePosition.findByPositionCode", query = "SELECT e FROM EmployeePosition e WHERE e.positionCode = :positionCode")
    , @NamedQuery(name = "EmployeePosition.findByMonthlyRate", query = "SELECT e FROM EmployeePosition e WHERE e.monthlyRate = :monthlyRate")
    , @NamedQuery(name = "EmployeePosition.findByPopulation", query = "SELECT e FROM EmployeePosition e WHERE e.population = :population")
    , @NamedQuery(name = "EmployeePosition.findByVacancy", query = "SELECT e FROM EmployeePosition e WHERE e.vacancy = :vacancy")
    , @NamedQuery(name = "EmployeePosition.findByPositionLevel", query = "SELECT e FROM EmployeePosition e WHERE e.positionLevel = :positionLevel")})
public class EmployeePosition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "position_id")
    private Integer positionId;
    @Column(name = "position_code")
    private String positionCode;
    @Basic(optional = false)
    @Lob
    @Column(name = "position_name")
    private String positionName;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monthly_rate")
    private Double monthlyRate;
    @Lob
    @Column(name = "scope")
    private String scope;
    @Column(name = "population")
    private Integer population;
    @Basic(optional = false)
    @Column(name = "vacancy")
    private int vacancy;
    @Column(name = "position_level")
    private String positionLevel;
    @JoinColumn(name = "department_code", referencedColumnName = "department_code")
    @ManyToOne
    private Department departmentCode;
    @OneToMany(mappedBy = "positionId")
    private Collection<Employee> employeeCollection;

    public EmployeePosition() {
    }

    public EmployeePosition(Integer positionId) {
        this.positionId = positionId;
    }

    public EmployeePosition(Integer positionId, String positionName, String description, int vacancy) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.description = description;
        this.vacancy = vacancy;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(Double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public Department getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Department departmentCode) {
        this.departmentCode = departmentCode;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (positionId != null ? positionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeePosition)) {
            return false;
        }
        EmployeePosition other = (EmployeePosition) object;
        if ((this.positionId == null && other.positionId != null) || (this.positionId != null && !this.positionId.equals(other.positionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bus_transit.system.entities.EmployeePosition[ positionId=" + positionId + " ]";
    }

}
