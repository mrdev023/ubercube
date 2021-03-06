/*
 * Copyright (C) 2016 Team Ubercube
 *
 * This file is part of Ubercube.
 *
 *     Ubercube is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ubercube is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Ubercube.  If not, see http://www.gnu.org/licenses/.
 */

package fr.veridiangames.core.game.entities.components;

/**
 * Created by Marccspro on 7 f�vr. 2016.
 */
public class ECModel extends EComponent
{
	private int model;
	
	public ECModel(int model)
	{
		super(MODEL);
		super.addDependencies(RENDER);
		
		this.model = model;
	}

	public int getModel()
	{
		return model;
	}

	public void setModel(int model)
	{
		this.model = model;
	}
}
