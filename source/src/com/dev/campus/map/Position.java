/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus.map;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.res.Resources;

public enum Position {
	
	BAT_A1(PositionType.BUILDING, R.array.batiment_A1, 44.808673,-0.591814),
	BAT_A2(PositionType.BUILDING, R.array.batiment_A2, 44.808083,-0.591747),
	BAT_A3(PositionType.BUILDING, R.array.batiment_A3, 44.807786,-0.592221),
	BAT_A4(PositionType.BUILDING, R.array.batiment_A4, 44.807885,-0.592836),
	BAT_A5(PositionType.BUILDING, R.array.batiment_A5, 44.807242,-0.593562),
	BAT_A6(PositionType.BUILDING,R.array.batiment_A6, 44.807052,-0.594115),
	BAT_A7(PositionType.BUILDING, R.array.batiment_A7, 44.807134,-0.593906),
	BAT_A8(PositionType.BUILDING, R.array.batiment_A8, 44.806791,-0.594321),
	BAT_A9(PositionType.BUILDING, R.array.batiment_A9, 44.807731,-0.594043),
	BAT_A10(PositionType.BUILDING, R.array.batiment_A10, 44.807436,-0.594625),
	BAT_A11(PositionType.BUILDING, R.array.batiment_A11, 44.806732,-0.594904),
	BAT_A12(PositionType.BUILDING, R.array.batiment_A12, 44.807111,-0.595845),
	BAT_A13(PositionType.BUILDING, R.array.batiment_A13, 44.806648,-0.595756),
	BAT_A14(PositionType.BUILDING, R.array.batiment_A14, 44.806332,-0.59544),
	BAT_A15(PositionType.BUILDING, R.array.batiment_A15, 44.806229,-0.597403),
	BAT_A16(PositionType.BUILDING, R.array.batiment_A16, 44.806435,-0.596915),
	BAT_A17(PositionType.BUILDING, R.array.batiment_A17, 44.806192,-0.596674),
	BAT_A18(PositionType.BUILDING, R.array.batiment_A18, 44.806388,-0.596556),
	BAT_A19(PositionType.BUILDING, R.array.batiment_A19, 44.806547,-0.596376),
	BAT_A20(PositionType.BUILDING, R.array.batiment_A20, 44.806816,-0.597151),
	BAT_A21(PositionType.BUILDING, R.array.batiment_A21, 44.806608,-0.597736),
	BAT_A22(PositionType.BUILDING, R.array.batiment_A22, 44.807375,-0.599335),
	BAT_A27(PositionType.BUILDING, R.array.batiment_A27, 44.808494,-0.596636),
	BAT_A28(PositionType.BUILDING, R.array.batiment_A28, 44.807851,-0.597481),
	BAT_A29(PositionType.BUILDING, R.array.batiment_A29, 44.807935,-0.596821),
	BAT_A30(PositionType.BUILDING, R.array.batiment_A30, 44.808193,-0.596408),
	BAT_A31(PositionType.BUILDING, R.array.batiment_A31, 44.808199,-0.595912),
	BAT_A32(PositionType.BUILDING, R.array.batiment_A32, 44.809113,-0.594608),
	BAT_A33(PositionType.BUILDING, R.array.batiment_A33, 44.809541,-0.59371),
	BAT_A36(PositionType.BUILDING, R.array.batiment_A36, 44.81006,-0.593638),
	BAT_A38(PositionType.BUILDING, R.array.batiment_A38, 44.810389,-0.592135),
	BAT_A39(PositionType.BUILDING, R.array.batiment_A39, 44.809244,-0.591806),
	
	BAT_B1(PositionType.BUILDING, R.array.batiment_B1, 44.801613,-0.609342),
	BAT_B2(PositionType.BUILDING, R.array.batiment_B2, 44.802018,-0.608744),
	BAT_B3(PositionType.BUILDING, R.array.batiment_B3, 44.802157,-0.60972),
	BAT_B4(PositionType.BUILDING, R.array.batiment_B4, 44.802656,-0.609256),
	BAT_B5(PositionType.BUILDING, R.array.batiment_B5, 44.802943,-0.608813),
	BAT_B6(PositionType.BUILDING, R.array.batiment_B6, 44.803,-0.607896),
	BAT_B7(PositionType.BUILDING, R.array.batiment_B7, 44.803478,-0.609039),
	BAT_B8(PositionType.BUILDING, R.array.batiment_B8, 44.80374,-0.607437),
	BAT_B9(PositionType.BUILDING, R.array.batiment_B9, 44.804096,-0.607129),
	BAT_B13(PositionType.BUILDING, R.array.batiment_B13, 44.80313,-0.610889),
	BAT_B14(PositionType.BUILDING, R.array.batiment_B14, 44.804005,-0.609385),
	BAT_B15(PositionType.BUILDING, R.array.batiment_B15, 44.80442,-0.607834),
	BAT_B16(PositionType.BUILDING, R.array.batiment_B16, 44.804538,-0.606971),
	BAT_B17(PositionType.BUILDING, R.array.batiment_B17, 44.804372,-0.608889),
	BAT_B18(PositionType.BUILDING, R.array.batiment_B18, 44.805293,-0.606509),
	BAT_B19(PositionType.BUILDING, R.array.batiment_B19, 44.80576,-0.605922),
	
	INRIA(PositionType.BUILDING, R.array.INRIA, 44.807992,-0.599852),
	SCRIME(PositionType.BUILDING, R.array.SCRIME, 44.810199,-0.591985),
	
	RU1(PositionType.RESTAURANT, R.array.RU1, 44.806447,-0.603122),
	RU2(PositionType.RESTAURANT, R.array.RU2, 44.800071,-0.611348),
	RU3(PositionType.RESTAURANT, R.array.RU3, 44.791803,-0.613818),
	LE48(PositionType.RESTAURANT, R.array.le_48, 44.806856,-0.591867),
	YAMATO(PositionType.RESTAURANT, R.array.yamato,44.811933,-0.592337),
	CAFETERIA(PositionType.RESTAURANT,R.array.cafeteria, 44.807514,-0.598852),
	FAC_KEBAB(PositionType.RESTAURANT, R.array.fac_kebab, 44.809636,-0.591653),
	MC_DONALDS(PositionType.RESTAURANT, R.array.mc_donalds,44.800387,-0.59577),
	LE_CAFE_BLEU(PositionType.RESTAURANT, R.array.le_cafe_bleu,44.8103,-0.591425),
	ED_WOOD_CAFE(PositionType.RESTAURANT, R.array.ed_wood_cafe,44.812288,-0.590202),
	PEPPINO_PIZZA(PositionType.RESTAURANT, R.array.peppino_pizza,44.81022,-0.591449),
	LE_CARPE_DIEM(PositionType.RESTAURANT, R.array.le_carpe_diem,44.8058,-0.59625),
	SUPERMARCHE_CASINO(PositionType.RESTAURANT, R.array.supermarche_casino, 44.806626,-0.591406),
	KIOSQUE_A_PIZZA(PositionType.RESTAURANT, R.array.kiosque_pizza, 44.806024,-0.592301),
	BOITE_A_PIZZA(PositionType.RESTAURANT, R.array.boite_pizza, 44.803342,-0.593326),
	
	BU(PositionType.SERVICE, R.array.BU, 44.80649,-0.602167),
	CROUS(PositionType.SERVICE, R.array.CROUS, 44.806974,-0.603782),
	CLINIQUE_BETHANIE(PositionType.SERVICE, R.array.clinique_béthanie, 44.805819,-0.599405),
	MEDECINE_PREVENTIVE(PositionType.SERVICE, R.array.medecine_preventive, 44.800467,-0.610549);
	
	
	public enum PositionType {
		BUILDING(R.drawable.building_marker),
		RESTAURANT(R.drawable.restaurant_marker),
		SERVICE(R.drawable.services_marker);
		
		private int mDrawableId;
		
		private PositionType(int drawableId) {
			mDrawableId = drawableId;
		}
		
		public int getDrawableId() {
			return mDrawableId;
		}
	}
	
	private PositionType mType;
	private int mNames;
	private double mLat;
	private double mLng;
	
	private Position(PositionType type, int names, double lat, double lng) {
		mType = type;
		mNames = names;
		mLat = lat;
		mLng = lng;
	}

	public String getName() {
		// First item is the most relevant name
		return getSuggestions()[0];
	}
	
	public String[] getSuggestions() {
		Resources res = CampusUB1App.getInstance().getResources();
		return res.getStringArray(mNames);
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
		// workaround to be able to compare IDs
		// with Google Maps Markers
		return "m" + ordinal();
	}

}
