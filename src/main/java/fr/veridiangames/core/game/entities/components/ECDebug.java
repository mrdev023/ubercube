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
 * Created by Tybau on 10/06/2016.
 */
public class ECDebug extends EComponent
{
    private boolean particleSpawn;
    private boolean particleRemove;

    public ECDebug()
    {
        super(DEBUG);
    }

    public boolean isParticleSpawn()
    {
        return particleSpawn;
    }

    public void setParticleSpawn(boolean v)
    {
        this.particleSpawn = v;
    }

    public boolean isParticleRemove() {
        return particleRemove;
    }

    public void setParticleRemove(boolean particleRemove) {
        this.particleRemove = particleRemove;
    }
}
