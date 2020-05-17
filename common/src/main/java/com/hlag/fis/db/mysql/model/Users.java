package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.db2.model.UsersOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * User entity.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Entity
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users implements PrimaryKeyIdentifier<String> {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Version
	private long version;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "HISTORY_FROM")
	private Long historyFrom;

	@Column(name = "CLIENT")
	private String client;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PNO")
	private Integer pno;

	@Column(name = "AUTOKEY_YES_NO")
	private Boolean autoKeyYesNo = true;

	@Column(name = "AUTOSAVE_YES_NO")
	private Boolean autoSaveYesNo = true;

	@Column(name = "PHONE")
	@Convert(converter = LegacyStringConverter.class)
	private String phone;

	@Column(name = "MEMO_DESTINATION")
	private String memoDestination;

	@Column(name = "FAKSIMILE")
	private String faksimile;

	@Column(name = "DATA_FROM_RACF")
	private Boolean dataFromRacf;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "LOCAL_PRINTER")
	private String localPrinter;

	@Column(name = "UTC_DIFF")
	private Integer utcDiff;

	@Column(name = "LOCATION_NO")
	private Integer locationNo;

	@Column(name = "LANGUAGE_CODE")
	private String languageCode;

	@Column(name = "LAST_LOGON_HH")
	private LocalDate lastLoginHh;

	@Column(name = "LAST_LOGON_NY")
	private LocalDate lastLoginNy;

	@Column(name = "LAST_LOGON_SI")
	private LocalDate lastLoginSi;

	@Column(name = "NEXT_FULL_LOGON_HH")
	private LocalDate nextFullLoginHh;

	@Column(name = "NEXT_FULL_LOGON_NY")
	private LocalDate nextFullLoginNy;

	@Column(name = "NEXT_FULL_LOGON_SI")
	private LocalDate nextFullLoginSi;

	@Column(name = "MAILADDRESS")
	private String mailAddress;

	@Column(name = "IMPERIAL_METRIC")
	private String imperialMetric;

	@Column(name = "LAST_LOGON")
	private Long lastLogon;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGE_LOC")
	private String changeLoc;

	@Column(name = "CHANGED_BY")
	private String changedBy;

	public Users() {
		// JPA constructor
	}

	public void update(UsersOld usersOld) {
		this.userId = usersOld.getId().getUserId();
		this.historyFrom = usersOld.getId().getHistoryFrom();
		this.client = usersOld.getClient();
		this.name = usersOld.getName();
		this.pno = usersOld.getPno();
		this.autoKeyYesNo = usersOld.getAutoKeyYesNo();
		this.autoSaveYesNo = usersOld.getAutoSaveYesNo();
		this.phone = usersOld.getPhone();
		this.memoDestination = usersOld.getMemoDestination();
		this.faksimile = usersOld.getFaksimile();
		this.dataFromRacf = usersOld.getDataFromRacf();
		this.location = usersOld.getLocation();
		this.localPrinter = usersOld.getLocalPrinter();
		this.utcDiff = usersOld.getUtcDiff();
		this.locationNo = usersOld.getLocationNo();
		this.languageCode = usersOld.getLanguageCode();
		this.lastLoginHh = usersOld.getLastLoginHh();
		this.lastLoginNy = usersOld.getLastLoginNy();
		this.lastLoginSi = usersOld.getLastLoginSi();
		this.nextFullLoginHh = usersOld.getNextFullLoginHh();
		this.nextFullLoginNy = usersOld.getNextFullLoginNy();
		this.nextFullLoginSi = usersOld.getNextFullLoginSi();
		this.mailAddress = usersOld.getMailAddress();
		this.imperialMetric = usersOld.getImperialMetric();
		this.lastLogon = usersOld.getLastLogon();
		this.changeLoc = usersOld.getChangeLoc();
		this.lastChange = usersOld.getLastChange();
		this.changedBy = usersOld.getChangedBy();
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Long getHistoryFrom() {
		return historyFrom;
	}

	public void setHistoryFrom(Long historyFrom) {
		this.historyFrom = historyFrom;
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
	public String toString() {
		return MoreObjects.toStringHelper(this)
		  .add("id", id)
		  .add("version", version)
		  .add("userId", userId)
		  .add("historyFrom", historyFrom)
		  .add("client", client)
		  .add("name", name)
		  .add("pno", pno)
		  .add("autoKeyYesNo", autoKeyYesNo)
		  .add("autoSaveYesNo", autoSaveYesNo)
		  .add("phone", phone)
		  .add("memoDestination", memoDestination)
		  .add("faksimile", faksimile)
		  .add("dataFromRacf", dataFromRacf)
		  .add("location", location)
		  .add("localPrinter", localPrinter)
		  .add("utcDiff", utcDiff)
		  .add("locationNo", locationNo)
		  .add("languageCode", languageCode)
		  .add("lastLoginHh", lastLoginHh)
		  .add("lastLoginNy", lastLoginNy)
		  .add("lastLoginSi", lastLoginSi)
		  .add("nextFullLoginHh", nextFullLoginHh)
		  .add("nextFullLoginNy", nextFullLoginNy)
		  .add("nextFullLoginSi", nextFullLoginSi)
		  .add("mailAddress", mailAddress)
		  .add("imperialMetric", imperialMetric)
		  .add("lastLogon", lastLogon)
		  .add("lastChange", lastChange)
		  .add("changeLoc", changeLoc)
		  .add("changedBy", changedBy)
		  .toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Users users = (Users) o;
		return version == users.version && Objects.equal(id, users.id) && Objects.equal(userId, users.userId) && Objects.equal(historyFrom, users.historyFrom)
		  && Objects.equal(client, users.client) && Objects.equal(name, users.name) && Objects.equal(pno, users.pno) && Objects.equal(autoKeyYesNo, users.autoKeyYesNo)
		  && Objects.equal(autoSaveYesNo, users.autoSaveYesNo) && Objects.equal(phone, users.phone) && Objects.equal(memoDestination, users.memoDestination)
		  && Objects.equal(faksimile, users.faksimile) && Objects.equal(dataFromRacf, users.dataFromRacf) && Objects.equal(location, users.location) && Objects.equal(localPrinter,
		  users.localPrinter) && Objects.equal(utcDiff, users.utcDiff) && Objects.equal(locationNo, users.locationNo) && Objects.equal(languageCode, users.languageCode)
		  && Objects.equal(lastLoginHh, users.lastLoginHh) && Objects.equal(lastLoginNy, users.lastLoginNy) && Objects.equal(lastLoginSi, users.lastLoginSi)
		  && Objects.equal(nextFullLoginHh, users.nextFullLoginHh) && Objects.equal(nextFullLoginNy, users.nextFullLoginNy) && Objects.equal(nextFullLoginSi,
		  users.nextFullLoginSi) && Objects.equal(mailAddress, users.mailAddress) && Objects.equal(imperialMetric, users.imperialMetric) && Objects.equal(lastLogon,
		  users.lastLogon) && Objects.equal(lastChange, users.lastChange) && Objects.equal(changeLoc, users.changeLoc) && Objects.equal(changedBy, users.changedBy);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id,
		  version,
		  userId,
		  historyFrom,
		  client,
		  name,
		  pno,
		  autoKeyYesNo,
		  autoSaveYesNo,
		  phone,
		  memoDestination,
		  faksimile,
		  dataFromRacf,
		  location,
		  localPrinter,
		  utcDiff,
		  locationNo,
		  languageCode,
		  lastLoginHh,
		  lastLoginNy,
		  lastLoginSi,
		  nextFullLoginHh,
		  nextFullLoginNy,
		  nextFullLoginSi,
		  mailAddress,
		  imperialMetric,
		  lastLogon,
		  lastChange,
		  changeLoc,
		  changedBy);
	}
}
