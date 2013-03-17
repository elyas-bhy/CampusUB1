package com.dev.campus.map;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Position {
	
	BAT_A1("m0",PositionType.BUILDING, R.array.batiment_A1, 44.808673,-0.591814),
	BAT_A2("m1",PositionType.BUILDING, R.array.batiment_A2, 44.808083,-0.591747),
	BAT_A3("m2",PositionType.BUILDING, R.array.batiment_A3, 44.807786,-0.592221),
	BAT_A4("m3",PositionType.BUILDING, R.array.batiment_A4, 44.807885,-0.592836),
	BAT_A5("m4",PositionType.BUILDING, R.array.batiment_A5, 44.807242,-0.593562),
	BAT_A6("m5",PositionType.BUILDING,R.array.batiment_A6, 44.807052,-0.594115),
	BAT_A7("m6",PositionType.BUILDING, R.array.batiment_A7, 44.807134,-0.593906),
	BAT_A8("m7",PositionType.BUILDING, R.array.batiment_A8, 44.806791,-0.594321),
	BAT_A9("m8",PositionType.BUILDING, R.array.batiment_A9, 44.807731,-0.594043),
	BAT_A10("m9",PositionType.BUILDING, R.array.batiment_A10, 44.807436,-0.594625),
	BAT_A11("m10",PositionType.BUILDING, R.array.batiment_A11, 44.806732,-0.594904),
	BAT_A12("m11",PositionType.BUILDING, R.array.batiment_A12, 44.807111,-0.595845),
	BAT_A13("m12",PositionType.BUILDING, R.array.batiment_A13, 44.806648,-0.595756),
	BAT_A14("m13",PositionType.BUILDING, R.array.batiment_A14, 44.806332,-0.59544),
	BAT_A16("m14",PositionType.BUILDING, R.array.batiment_A16, 44.806435,-0.596915),
	BAT_A17("m15",PositionType.BUILDING, R.array.batiment_A17, 44.806192,-0.596674),
	BAT_A18("m16",PositionType.BUILDING, R.array.batiment_A18, 44.806388,-0.596556),
	BAT_A19("m17",PositionType.BUILDING, R.array.batiment_A19, 44.806547,-0.596376),
	BAT_A20("m18",PositionType.BUILDING, R.array.batiment_A20, 44.806816,-0.597151),
	BAT_A21("m19",PositionType.BUILDING, R.array.batiment_A21, 44.806608,-0.597736),
	BAT_A22("m20",PositionType.BUILDING, R.array.batiment_A22, 44.807375,-0.599335),
	BAT_A27("m21",PositionType.BUILDING, R.array.batiment_A27, 44.808494,-0.596636),
	BAT_A28("m22",PositionType.BUILDING, R.array.batiment_A28, 44.807851,-0.597481),
	BAT_A29("m23",PositionType.BUILDING, R.array.batiment_A29, 44.807935,-0.596821),
	BAT_A30("m24",PositionType.BUILDING, R.array.batiment_A30, 44.808193,-0.596408),
	BAT_A31("m25",PositionType.BUILDING, R.array.batiment_A31, 44.808199,-0.595912),
	BAT_A32("m26",PositionType.BUILDING, R.array.batiment_A32, 44.809113,-0.594608),
	BAT_A33("m27",PositionType.BUILDING, R.array.batiment_A33, 44.809541,-0.59371),
	BAT_A36("m28",PositionType.BUILDING, R.array.batiment_A36, 44.81006,-0.593638),
	BAT_A38("m29",PositionType.BUILDING, R.array.batiment_A38, 44.810389,-0.592135),
	BAT_A39("m30",PositionType.BUILDING, R.array.batiment_A39, 44.809244,-0.591806),
	
	BAT_B1("m31",PositionType.BUILDING, R.array.batiment_B1, 44.801613,-0.609342),
	BAT_B2("m32",PositionType.BUILDING, R.array.batiment_B2, 44.802018,-0.608744),
	BAT_B3("m33",PositionType.BUILDING, R.array.batiment_B3, 44.802157,-0.60972),
	BAT_B4("m34",PositionType.BUILDING, R.array.batiment_B4, 44.802656,-0.609256),
	BAT_B5("m35",PositionType.BUILDING, R.array.batiment_B5, 44.802943,-0.608813),
	BAT_B6("m36",PositionType.BUILDING, R.array.batiment_B6, 44.803,-0.607896),
	BAT_B7("m37",PositionType.BUILDING, R.array.batiment_B7, 44.803478,-0.609039),
	BAT_B8("m38",PositionType.BUILDING, R.array.batiment_B8, 44.80374,-0.607437),
	BAT_B9("m39",PositionType.BUILDING, R.array.batiment_B9, 44.804096,-0.607129),
	BAT_B13("m40",PositionType.BUILDING, R.array.batiment_B13, 44.80313,-0.610889),
	BAT_B14("m41",PositionType.BUILDING, R.array.batiment_B14, 44.804005,-0.609385),
	BAT_B15("m42",PositionType.BUILDING, R.array.batiment_B15, 44.80442,-0.607834),
	BAT_B16("m43",PositionType.BUILDING, R.array.batiment_B16, 44.804538,-0.606971),
	BAT_B17("m44",PositionType.BUILDING, R.array.batiment_B17, 44.804372,-0.608889),
	BAT_B18("m45",PositionType.BUILDING, R.array.batiment_B18, 44.805293,-0.606509),
	BAT_B19("m46",PositionType.BUILDING, R.array.batiment_B19, 44.80576,-0.605922),
	
	INRIA("m47",PositionType.BUILDING, R.array.INRIA, 44.807992,-0.599852),
	SCRIME("m48",PositionType.BUILDING, R.array.SCRIME, 44.810199,-0.591985),
	
	RU1("m49",PositionType.RESTAURATION, R.array.RU1, 44.806447,-0.603122),
	RU2("m50",PositionType.RESTAURATION, R.array.RU2, 44.800071,-0.611348),
	RU3("m51",PositionType.RESTAURATION, R.array.RU3, 44.791803,-0.613818),
	CAFETARIA("m52",PositionType.RESTAURATION,R.array.cafetaria, 44.807514,-0.598852),
	FAC_KEBAB("m53",PositionType.RESTAURATION, R.array.fac_kebab, 44.809636,-0.591653),
	SUPERMARCHE_CASINO("m54",PositionType.RESTAURATION, R.array.supermarche_casino, 44.806626,-0.591406),
	LE48("m55",PositionType.RESTAURATION, R.array.le_48, 44.806856,-0.591867),
	LE_CARPE_DIEM("m56",PositionType.RESTAURATION, R.array.le_carpe_diem,44.8058,-0.59625),
	LE_CAFE_BLEU("m57",PositionType.RESTAURATION, R.array.le_cafe_bleu,44.8103,-0.591425),
	ED_WOOD_CAFE("m58",PositionType.RESTAURATION, R.array.ed_wood_cafe,44.812288,-0.590202),
	PEPPINO_PIZZA("m59",PositionType.RESTAURATION, R.array.peppino_pizza,44.81022,-0.591449),
	YAMATO("m60",PositionType.RESTAURATION, R.array.yamato,44.811933,-0.592337),
	MC_DONALDS("m61",PositionType.RESTAURATION, R.array.mc_donalds,44.800387,-0.59577),
	
	BU("m62",PositionType.SERVICE,R.array.BU, 44.80649,-0.602167),
	MEDECINE_PREVENTIVE("m63",PositionType.SERVICE,R.array.medecine_preventive, 44.800467,-0.610549),
	CLINIQUE_BETHANIE("m64",PositionType.SERVICE,R.array.clinique_béthanie, 44.805819,-0.599405),
	CROUS("m65",PositionType.SERVICE,R.array.CROUS, 44.806974,-0.603782);
	
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
	
	private String mId;
	private PositionType mType;
	private int mName;
	private double mLat;
	private double mLng;
	
	private Position(String id,PositionType type,int name, double lat, double lng) {
		mId = id;
		mType = type;
		mName = name;
		mLat = lat;
		mLng = lng;
	}

	public String getName() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getStringArray(mName)[0];
	}
	
	public String[] getSuggestions() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getStringArray(mName);
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

	public String getId() {
		return mId;
	}

}
