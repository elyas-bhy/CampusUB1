package com.dev.campus.map;

public enum Position {
	
	BAT_A1("m0",PositionType.BUILDING, "A1", 44.808673,-0.591814),
	BAT_A2("m1",PositionType.BUILDING, "A2", 44.808083,-0.591747),
	BAT_A3("m2",PositionType.BUILDING, "A3", 44.807786,-0.592221),
	BAT_A4("m3",PositionType.BUILDING, "A4", 44.807885,-0.592836),
	BAT_A5("m4",PositionType.BUILDING, "A5", 44.807242,-0.593562),
	BAT_A6("m5",PositionType.BUILDING, "A6", 44.807052,-0.594115),
	BAT_A7("m6",PositionType.BUILDING, "A7", 44.807134,-0.593906),
	BAT_A8("m7",PositionType.BUILDING, "A8", 44.806791,-0.594321),
	BAT_A9("m8",PositionType.BUILDING, "A9", 44.807731,-0.594043),
	BAT_A10("m9",PositionType.BUILDING, "A10", 44.807436,-0.594625),
	BAT_A11("m10",PositionType.BUILDING, "A11", 44.806732,-0.594904),
	BAT_A12("m11",PositionType.BUILDING, "A12", 44.807111,-0.595845),
	BAT_A13("m12",PositionType.BUILDING, "A13", 44.806648,-0.595756),
	BAT_A14("m13",PositionType.BUILDING, "A14", 44.806332,-0.59544),
	BAT_A16("m14",PositionType.BUILDING, "A16", 44.806435,-0.596915),
	BAT_A17("m15",PositionType.BUILDING, "A17", 44.806192,-0.596674),
	BAT_A18("m16",PositionType.BUILDING, "A18", 44.806388,-0.596556),
	BAT_A19("m17",PositionType.BUILDING, "A19", 44.806547,-0.596376),
	BAT_A20("m18",PositionType.BUILDING, "A20", 44.806816,-0.597151),
	BAT_A21("m19",PositionType.BUILDING, "A21", 44.806608,-0.597736),
	BAT_A22("m20",PositionType.BUILDING, "A22", 44.807375,-0.599335),
	BAT_A27("m21",PositionType.BUILDING, "LaBRI", 44.808494,-0.596636),
	BAT_A28("m22",PositionType.BUILDING, "CREMI", 44.807851,-0.597481),
	BAT_A29("m23",PositionType.BUILDING, "A29", 44.807935,-0.596821),
	BAT_A30("m24",PositionType.BUILDING, "A30", 44.808193,-0.596408),
	BAT_A31("m25",PositionType.BUILDING, "A31", 44.808199,-0.595912),
	BAT_A32("m26",PositionType.BUILDING, "A32", 44.809113,-0.594608),
	BAT_A33("m27",PositionType.BUILDING, "A33", 44.809541,-0.59371),
	BAT_A36("m28",PositionType.BUILDING, "A36", 44.81006,-0.593638),
	BAT_A38("m29",PositionType.BUILDING, "A38", 44.810389,-0.592135),
	BAT_A39("m30",PositionType.BUILDING, "A39", 44.809244,-0.591806),
	
	BAT_B1("m31",PositionType.BUILDING, "B1", 44.801613,-0.609342),
	BAT_B2("m32",PositionType.BUILDING, "B2", 44.802018,-0.608744),
	BAT_B3("m33",PositionType.BUILDING, "B3", 44.802157,-0.60972),
	BAT_B4("m34",PositionType.BUILDING, "B4", 44.802656,-0.609256),
	BAT_B5("m35",PositionType.BUILDING, "B5", 44.802943,-0.608813),
	BAT_B6("m36",PositionType.BUILDING, "B6", 44.803,-0.607896),
	BAT_B7("m37",PositionType.BUILDING, "B7", 44.803478,-0.609039),
	BAT_B8("m38",PositionType.BUILDING, "B8", 44.80374,-0.607437),
	BAT_B9("m39",PositionType.BUILDING, "B9", 44.804096,-0.607129),
	BAT_B13("m40",PositionType.BUILDING, "B13", 44.80313,-0.610889),
	BAT_B14("m41",PositionType.BUILDING, "B14", 44.804005,-0.609385),
	BAT_B15("m42",PositionType.BUILDING, "B15", 44.80442,-0.607834),
	BAT_B16("m43",PositionType.BUILDING, "B16", 44.804538,-0.606971),
	BAT_B17("m44",PositionType.BUILDING, "B17", 44.804372,-0.608889),
	BAT_B18("m45",PositionType.BUILDING, "B18", 44.805293,-0.606509),
	BAT_B19("m46",PositionType.BUILDING, "B19", 44.80576,-0.605922),
	
	RU1("m47",PositionType.RESTAURATION, "Restaurant universitaire 1", 44.806447,-0.603122),
	RU2("m48",PositionType.RESTAURATION, "Restaurant universitaire 2", 44.800071,-0.611348),
	RU3("m49",PositionType.RESTAURATION, "Restaurant universitaire 3", 44.791803,-0.613818),
	CAFETARIA("m50",PositionType.RESTAURATION,"Cafétaria la Soucoupe", 44.807514,-0.598852),
	FAC_KEBAB("m51",PositionType.RESTAURATION, "Fac kebab", 44.809636,-0.591653),
	
	BU("m52",PositionType.SERVICE,"Bibliothèque universitaire", 44.80649,-0.602167),
	MEDECINE_PREVENTIVE("m53",PositionType.SERVICE,"Médecine préventive", 44.800467,-0.610549);
	
	public enum PositionType {
		BUILDING("building_marker"),
		RESTAURATION("restauration_marker"),
		SERVICE("services_marker");
		
		private String mDrawableId;
		
		private PositionType(String drawableId) {
			mDrawableId = drawableId;
		}
		
		public String getDrawableId() {
			return mDrawableId;
		}
	}
	
	private String mID;
	private PositionType mType;
	private String mName;
	private double mLat;
	private double mLng;
	
	private Position(String id,PositionType type,String name, double lat, double lng) {
		mID = id;
		mType = type;
		mName = name;
		mLat = lat;
		mLng = lng;
	}

	public String getName() {
		return mName;
	}

	public double getLat() {
		return mLat;
	}

	public double getLng() {
		return mLng;
	}
	
	public PositionType getType() {
		return mType;
	}

	public String getmID() {
		return mID;
	}

}
