/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.system.entities;

import java.io.Serializable;
import java.util.Date;
import javafx.scene.control.Hyperlink;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NelsonDelaTorre
 */

@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findByEmpId", query = "SELECT e FROM Employee e WHERE e.empId = :empId")
    , @NamedQuery(name = "Employee.findByUli", query = "SELECT e FROM Employee e WHERE e.uli = :uli")
    , @NamedQuery(name = "Employee.findByFirstname", query = "SELECT e FROM Employee e WHERE e.firstname = :firstname")
    , @NamedQuery(name = "Employee.findByMiddlename", query = "SELECT e FROM Employee e WHERE e.middlename = :middlename")
    , @NamedQuery(name = "Employee.findByLastname", query = "SELECT e FROM Employee e WHERE e.lastname = :lastname")
    , @NamedQuery(name = "Employee.findByBirthdate", query = "SELECT e FROM Employee e WHERE e.birthdate = :birthdate")
    , @NamedQuery(name = "Employee.findByContactInfo", query = "SELECT e FROM Employee e WHERE e.contactInfo = :contactInfo")
    , @NamedQuery(name = "Employee.findByImmediateSupervisorCode", query = "SELECT e FROM Employee e WHERE e.immediateSupervisorCode = :immediateSupervisorCode")
    , @NamedQuery(name = "Employee.findByAge", query = "SELECT e FROM Employee e WHERE e.age = :age")
    , @NamedQuery(name = "Employee.findByGender", query = "SELECT e FROM Employee e WHERE e.gender = :gender")
    , @NamedQuery(name = "Employee.findByPositionCode", query = "SELECT e FROM Employee e WHERE e.positionCode = :positionCode")
    , @NamedQuery(name = "Employee.findBySss", query = "SELECT e FROM Employee e WHERE e.sss = :sss")
    , @NamedQuery(name = "Employee.findByBir", query = "SELECT e FROM Employee e WHERE e.bir = :bir")
    , @NamedQuery(name = "Employee.findByTax", query = "SELECT e FROM Employee e WHERE e.tax = :tax")
    , @NamedQuery(name = "Employee.findByPhilhealth", query = "SELECT e FROM Employee e WHERE e.philhealth = :philhealth")
    , @NamedQuery(name = "Employee.findByHdmf", query = "SELECT e FROM Employee e WHERE e.hdmf = :hdmf")
    , @NamedQuery(name = "Employee.findByHiringReqId", query = "SELECT e FROM Employee e WHERE e.hiringReqId = :hiringReqId")
    , @NamedQuery(name = "Employee.findByEducationRecId", query = "SELECT e FROM Employee e WHERE e.educationRecId = :educationRecId")
    , @NamedQuery(name = "Employee.findByClearancesRecId", query = "SELECT e FROM Employee e WHERE e.clearancesRecId = :clearancesRecId")
    , @NamedQuery(name = "Employee.findByEmploymentHistoryRecId", query = "SELECT e FROM Employee e WHERE e.employmentHistoryRecId = :employmentHistoryRecId")})

public class Employee extends Hyperlink implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_id")
    private Integer empId;
    @Lob
    @Column(name = "pic")
    private byte[] pic;
    @Column(name = "uli")
    private String uli;
    @Basic(optional = false)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @Column(name = "middlename")
    private String middlename;
    @Basic(optional = false)
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Lob
    @Column(name = "address")
    private String address;
    @Column(name = "contact_info")
    private String contactInfo;
    @Column(name = "immediate_supervisor_code")
    private Integer immediateSupervisorCode;
    @Column(name = "age")
    private Short age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "position_code")
    private String positionCode;
    @Column(name = "sss")
    private String sss;
    @Column(name = "bir")
    private String bir;
    @Column(name = "tax")
    private String tax;
    @Column(name = "philhealth")
    private String philhealth;
    @Column(name = "hdmf")
    private String hdmf;
    @Column(name = "hiring_req_id")
    private Integer hiringReqId;
    @Column(name = "education_rec_id")
    private Integer educationRecId;
    @Column(name = "clearances_rec_id")
    private Integer clearancesRecId;
    @Column(name = "employment_history_rec_id")
    private Integer employmentHistoryRecId;
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    @ManyToOne
    private EmployeePosition positionId;

    public Employee() {
        
    }

    public Employee(Integer empId) {
        this.empId = empId;
    }

    public Employee(Integer empId, String firstname, String middlename, String lastname) {
        this.empId = empId;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getUli() {
        return uli;
    }

    public void setUli(String uli) {
        this.uli = uli;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Integer getImmediateSupervisorCode() {
        return immediateSupervisorCode;
    }

    public void setImmediateSupervisorCode(Integer immediateSupervisorCode) {
        this.immediateSupervisorCode = immediateSupervisorCode;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getSss() {
        return sss;
    }

    public void setSss(String sss) {
        this.sss = sss;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getPhilhealth() {
        return philhealth;
    }

    public void setPhilhealth(String philhealth) {
        this.philhealth = philhealth;
    }

    public String getHdmf() {
        return hdmf;
    }

    public void setHdmf(String hdmf) {
        this.hdmf = hdmf;
    }

    public Integer getHiringReqId() {
        return hiringReqId;
    }

    public void setHiringReqId(Integer hiringReqId) {
        this.hiringReqId = hiringReqId;
    }

    public Integer getEducationRecId() {
        return educationRecId;
    }

    public void setEducationRecId(Integer educationRecId) {
        this.educationRecId = educationRecId;
    }

    public Integer getClearancesRecId() {
        return clearancesRecId;
    }

    public void setClearancesRecId(Integer clearancesRecId) {
        this.clearancesRecId = clearancesRecId;
    }

    public Integer getEmploymentHistoryRecId() {
        return employmentHistoryRecId;
    }

    public void setEmploymentHistoryRecId(Integer employmentHistoryRecId) {
        this.employmentHistoryRecId = employmentHistoryRecId;
    }

    public EmployeePosition getPositionId() {
        return positionId;
    }

    public void setPositionId(EmployeePosition positionId) {
        this.positionId = positionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bus_transit.system.entities.Employee[ empId=" + empId + " ]";
    }
    
    public void goToEmployeeInfo(){
        
    }
}
