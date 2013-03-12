package com.dev.campus.map;

public enum Position {
	
	BAT_A1(PositionType.BUILDING, "B�timent A1", 44.808666,-0.592012),
	BAT_A2(PositionType.BUILDING, "B�timent A2", 44.808198,-0.591798),
	BAT_A3(PositionType.BUILDING, "B�timent A3", 44.808198,-0.591798),
	BAT_A4(PositionType.BUILDING, "B�timent A4", 44.807843,-0.593109),
	BAT_A5(PositionType.BUILDING, "B�timent A5", 44.807293,-0.593664),
	BAT_A6(PositionType.BUILDING, "B�timent A6", 44.80705,-0.594171),
	BAT_A7(PositionType.BUILDING, "B�timent A7", 44.807223,-0.593954),
	BAT_A8(PositionType.BUILDING, "B�timent A8", 44.806707,-0.594279),
	BAT_A9(PositionType.BUILDING, "B�timent A9", 44.807813,-0.594187),
	BAT_A10(PositionType.BUILDING, "B�timent A10", 44.807427,-0.59485),
	BAT_A11(PositionType.BUILDING, "B�timent A11", 44.806799,-0.59518),
	BAT_A12(PositionType.BUILDING, "B�timent A12", 44.807059,-0.596116),
	BAT_A13(PositionType.BUILDING, "B�timent A13", 44.806618,-0.595984),
	BAT_A14(PositionType.BUILDING, "B�timent A14", 44.806317,-0.595488),
	BAT_A16(PositionType.BUILDING, "B�timent A16", 44.806414,-0.597009),
	BAT_A17(PositionType.BUILDING, "B�timent A17", 44.806195,-0.596827),
	BAT_A18(PositionType.BUILDING, "B�timent A18", 44.806471,-0.596612),
	BAT_A19(PositionType.BUILDING, "B�timent A19", 44.80654,-0.596435),
	BAT_A20(PositionType.BUILDING, "B�timent A20", 44.806783,-0.59728),
	BAT_A21(PositionType.BUILDING, "B�timent A21", 44.80654,-0.597948),
	BAT_A22(PositionType.BUILDING, "B�timent A22", 44.807303,-0.599541),
	BAT_A27(PositionType.BUILDING, "LaBRI", 44.808452,-0.596829),
	BAT_A28(PositionType.BUILDING, "CREMI", 44.807851,-0.597481),
	BAT_A29(PositionType.BUILDING, "B�timent A29", 44.807935,-0.596821),
	BAT_A30(PositionType.BUILDING, "B�timent A30", 44.808452,-0.596829),
	BAT_A31(PositionType.BUILDING, "B�timent A31", 44.808439,-0.595805),
	BAT_A32(PositionType.BUILDING, "B�timent A32", 44.809078,-0.594772),
	BAT_A33(PositionType.BUILDING, "B�timent A33", 44.809672,-0.593367),
	BAT_A36(PositionType.BUILDING, "B�timent A36", 44.810055,-0.593747),
	BAT_A39(PositionType.BUILDING, "B�timent A39", 44.809326,-0.591789),
	
	RU(PositionType.RESTAURATION, "Restaurant universitaire 1", 44.806447,-0.603122),
	CAFETARIA(PositionType.RESTAURATION,"Caf�taria la Soucoupe", 44.807514,-0.598852),
	
	BU(PositionType.SERVICE,"Biblioth�que universitaire", 44.806555,-0.601907);
	
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
	
	private PositionType mType;
	private String mName;
	private double mLat;
	private double mLng;
	
	private Position(PositionType type,String name, double lat, double lng) {
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

}
