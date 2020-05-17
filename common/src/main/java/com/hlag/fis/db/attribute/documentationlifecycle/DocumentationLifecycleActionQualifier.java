package com.hlag.fis.db.attribute.documentationlifecycle;

import com.hlag.fis.db.converter.LegacyEnumConverter;
import com.hlag.fis.db.converter.PersistableEnum;

public enum DocumentationLifecycleActionQualifier implements PersistableEnum<String> {
    NONE(" "), // Document created
	C("C"), // Document created
	D("D"), // Document deleted
	F("F"), // Document set to complete
	I("I"), // Document set to incomplete
	M("M"), // Document created by mass
	P("P"), // Document printed
	R("R"), // Document registration
	S("S"), // Document save
	AP("AP"), // Draft approval received
	AR("AR"), // ...
	AS("AS"), // ...
	CO("CO"), // Document collected
	CR("CR"), // ...
	CS("CS"), // ...
	CT("CT"), // Document collect reset
	DR("DR"), // Duplicate received
	DS("DS"), // Duplicate received reset
	DW("DW"), // Document downloaded
	ED("ED"), // E-Doc set to done
	EP("EP"), // E-Doc set to pending
	ER("ER"), // Automated processing with errors
	GR("GR"), // DG Notification received
	GS("GS"), // DG Notification received reset
	HR("HR"), // HB Instruction received
	HS("HS"), // HB Instruction received reset
	LF("LF"), // All LOFU received for the belonging container
	LR("LR"), // All LOFU received has been reset
	ML("ML"), // OBL release approved
	MR("MR"), // OBL release approved reset
	NC("NC"), // Document number changed
	NO("NO"), // Document No specified at booking
	OR("OR"), // Other relevant information received
	OS("OS"), // Other relevant information received reset
	PA("PA"), // Document payment received
	PR("PR"), // Document payment reset
	RD("RD"), // Customer informed about missing details
	RR("RR"), // Document released reset
	RS("RS"), // ...
	RL("RL"), // ...
	RM("RM"), // Customer informed about missing S/I
	RU("RU"), // Customer informed about unreadable S/I
	UI("UI"), // User interaction
	UP("UP"), // Document provided for electronic download
	VC("VC"), // BL Prevalidation completed
	VR("VR"), // BL Prevalidation completed reset
	WA("WA"), // Work allocation task assigned
	WF("WF"); // Work allocation task finished

	private String value;

	DocumentationLifecycleActionQualifier(String id) {
		this.value = id;
	}

	@Override
	public String getValue() {
		return value;
	}

    public String getNullValue() {
        return NONE.value;
    }

	public static class Converter extends LegacyEnumConverter<DocumentationLifecycleActionQualifier, String> {
		public Converter() {
			super(DocumentationLifecycleActionQualifier.class);
		}
	}
}

