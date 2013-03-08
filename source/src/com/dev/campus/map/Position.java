package com.dev.campus.map;

public enum Position {
	
	CENTRE_CAMPUS("Centre",44.80736,-0.596572),
	
	BAT_A1("Batiment A1",44.808666,-0.592012),
	BAT_A2("Batiment A2",44.808198,-0.591798),
	BAT_A3("Batiment A3",44.808198,-0.591798),
	BAT_A4("Batiment A4",44.807843,-0.593109),
	BAT_A5("Batiment A5",44.807293,-0.593664),
	BAT_A6("Batiment A6",44.80705,-0.594171),
	BAT_A7("Batiment A7",44.807223,-0.593954),
	BAT_A8("Batiment A8",44.806707,-0.594279),
	BAT_A9("Batiment A9",44.807813,-0.594187),
	BAT_A10("Batiment A10",44.807427,-0.59485),
	BAT_A11("Batiment A11",44.806799,-0.59518),
	BAT_A12("Batiment A12",44.807059,-0.596116),
	BAT_A13("Batiment A13",44.806618,-0.595984),
	BAT_A14("Batiment A14",44.806317,-0.595488),
	BAT_A16("Batiment A16",44.806414,-0.597009),
	BAT_A17("Batiment A17",44.806195,-0.596827),
	BAT_A18("Batiment A18",44.806471,-0.596612),
	BAT_A19("Batiment A19",44.80654,-0.596435),
	BAT_A20("Batiment A20",44.806783,-0.59728),
	BAT_A21("Batiment A21",44.80654,-0.597948),
	BAT_A22("Batiment A22",44.807303,-0.599541),
	BAT_A27("Batiment A27",44.808452,-0.596829),
	BAT_A28("Cremi",44.807851,-0.597481),
	BAT_A29("Batiment A29",44.807935,-0.596821),
	BAT_A30("Batiment A30",44.808452,-0.596829),
	BAT_A31("Batiment A31",44.808439,-0.595805),
	BAT_A32("Batiment A32",44.809078,-0.594772),
	BAT_A33("Batiment A33",44.809672,-0.593367),
	BAT_A36("Batiment A36",44.810055,-0.593747),
	BAT_A39("Batiment A39",44.809326,-0.591789),
	;
	
	private String mPosName;
	private double mLat;
	private double mLng;
	
	private Position(String name, double lat, double lng) {
		mPosName = name;
		mLat = lat;
		mLng = lng;
	}

	public String getPosName() {
		return mPosName;
	}

	public double getLat() {
		return mLat;
	}

	public double getLng() {
		return mLng;
	}

}
