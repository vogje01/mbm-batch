package com.hlag.fis.db.db2.model;

import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyBooleanIntegerConverter;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TZ0120")
public class UsersOld implements PrimaryKeyIdentifier<UsersIdOld> {

    @EmbeddedId
    private UsersIdOld id;

    @Column(name = "CLIENT")
    @Convert(converter = LegacyStringConverter.class)
    private String client;

    @Column(name = "AUTOKEY_YES_NO")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean autoKeyYesNo = true;

    @Column(name = "AUTOSAVE_YES_NO")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean autoSaveYesNo = true;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLoc;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    @Column(name = "NAME")
    @Convert(converter = LegacyStringConverter.class)
    private String name;

    @Column(name = "PNO")
    private Integer pno;

    @Column(name = "PHONE")
    @Convert(converter = LegacyStringConverter.class)
    private String phone;

    @Column(name = "MEMO_DESTINATION")
    @Convert(converter = LegacyStringConverter.class)
    private String memoDestination;

    @Column(name = "FAKSIMILE")
    @Convert(converter = LegacyStringConverter.class)
    private String faksimile;

    @Column(name = "DATA_FROM_RACF")
    @Convert(converter = LegacyBooleanIntegerConverter.class)
    private Boolean dataFromRacf;

    @Column(name = "LOCATION")
    @Convert(converter = LegacyStringConverter.class)
    private String location;

    @Column(name = "LOCAL_PRINTER")
    @Convert(converter = LegacyStringConverter.class)
    private String localPrinter;

    @Column(name = "UTC_DIFF")
    private Integer utcDiff;

    @Column(name = "LOCATION_NO")
    private Integer locationNo;

    @Column(name = "LANGUAGE_CODE")
    @Convert(converter = LegacyStringConverter.class)
    private String languageCode;

    @Column(name = "LAST_LOGON_HH")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate lastLoginHh;

    @Column(name = "LAST_LOGON_NY")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate lastLoginNy;

    @Column(name = "LAST_LOGON_SI")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate lastLoginSi;

    @Column(name = "NEXT_FULL_LOGON_HH")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate nextFullLoginHh;

    @Column(name = "NEXT_FULL_LOGON_NY")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate nextFullLoginNy;

    @Column(name = "NEXT_FULL_LOGON_SI")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate nextFullLoginSi;

    @Column(name = "MAILADDRESS")
    @Convert(converter = LegacyStringConverter.class)
    private String mailAddress;

    @Column(name = "IMPERIAL_METRIC")
    @Convert(converter = LegacyStringConverter.class)
    private String imperialMetric;

    @Column(name = "LAST_LOGON")
    private Long lastLogon;

    public UsersIdOld getId() {
        return id;
    }

    public void setId(UsersIdOld id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public Boolean getAutoKeyYesNo() {
        return autoKeyYesNo;
    }

    public void setAutoKeyYesNo(Boolean autoKeyYesNo) {
        this.autoKeyYesNo = autoKeyYesNo;
    }

    public Boolean getAutoSaveYesNo() {
        return autoSaveYesNo;
    }

    public void setAutoSaveYesNo(Boolean autoSaveYesNo) {
        this.autoSaveYesNo = autoSaveYesNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemoDestination() {
        return memoDestination;
    }

    public void setMemoDestination(String memoDestination) {
        this.memoDestination = memoDestination;
    }

    public String getFaksimile() {
        return faksimile;
    }

    public void setFaksimile(String faksimile) {
        this.faksimile = faksimile;
    }

    public Boolean getDataFromRacf() {
        return dataFromRacf;
    }

    public void setDataFromRacf(Boolean dataFromRacf) {
        this.dataFromRacf = dataFromRacf;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocalPrinter() {
        return localPrinter;
    }

    public void setLocalPrinter(String localPrinter) {
        this.localPrinter = localPrinter;
    }

    public Integer getUtcDiff() {
        return utcDiff;
    }

    public void setUtcDiff(Integer utcDiff) {
        this.utcDiff = utcDiff;
    }

    public Integer getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(Integer locationNo) {
        this.locationNo = locationNo;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public LocalDate getLastLoginHh() {
        return lastLoginHh;
    }

    public void setLastLoginHh(LocalDate lastLoginHh) {
        this.lastLoginHh = lastLoginHh;
    }

    public LocalDate getLastLoginNy() {
        return lastLoginNy;
    }

    public void setLastLoginNy(LocalDate lastLoginNy) {
        this.lastLoginNy = lastLoginNy;
    }

    public LocalDate getLastLoginSi() {
        return lastLoginSi;
    }

    public void setLastLoginSi(LocalDate lastLoginSi) {
        this.lastLoginSi = lastLoginSi;
    }

    public LocalDate getNextFullLoginHh() {
        return nextFullLoginHh;
    }

    public void setNextFullLoginHh(LocalDate nextFullLoginHh) {
        this.nextFullLoginHh = nextFullLoginHh;
    }

    public LocalDate getNextFullLoginNy() {
        return nextFullLoginNy;
    }

    public void setNextFullLoginNy(LocalDate nextFullLoginNy) {
        this.nextFullLoginNy = nextFullLoginNy;
    }

    public LocalDate getNextFullLoginSi() {
        return nextFullLoginSi;
    }

    public void setNextFullLoginSi(LocalDate nextFullLoginSi) {
        this.nextFullLoginSi = nextFullLoginSi;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getImperialMetric() {
        return imperialMetric;
    }

    public void setImperialMetric(String imperialMetric) {
        this.imperialMetric = imperialMetric;
    }

    public Long getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(Long lastLogon) {
        this.lastLogon = lastLogon;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangeLoc() {
        return changeLoc;
    }

    public void setChangeLoc(String changeLoc) {
        this.changeLoc = changeLoc;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersOld usersOld = (UsersOld) o;
        return Objects.equal(id, usersOld.id) &&
                Objects.equal(client, usersOld.client) &&
                Objects.equal(autoKeyYesNo, usersOld.autoKeyYesNo) &&
                Objects.equal(autoSaveYesNo, usersOld.autoSaveYesNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, autoKeyYesNo, autoSaveYesNo);
    }

    @Override
    public String toString() {
        return "UsersOld{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", autoKeyYesNo=" + autoKeyYesNo +
                ", autoSaveYesNo=" + autoSaveYesNo +
                '}';
    }
}
