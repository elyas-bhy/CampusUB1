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

package com.dev.campus.home;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Interface of home menu items
 * @author CampusUB1 Development Team
 *
 */
public interface HomeItem {
	
	/**
	 * Returns the index of the calling HomeItem's type
	 */
	public int getViewType();
	
	/**
	 * Wrapper method that implements adapter's getView method
	 * @param inflater
	 * @param convertView
	 * @return
	 */
	public View getView(LayoutInflater inflater, View convertView);

}
