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

package fr.veridiangames.core.network.packets;

import fr.veridiangames.core.GameCore;
import fr.veridiangames.core.game.entities.player.ClientPlayer;
import fr.veridiangames.core.game.entities.player.ServerPlayer;
import fr.veridiangames.core.maths.Vec3;
import fr.veridiangames.core.network.NetworkableClient;
import fr.veridiangames.core.network.NetworkableServer;
import fr.veridiangames.core.utils.DataBuffer;

import java.net.InetAddress;

/**
 * Created by Tybau on 13/06/2016.
 */
public class RespawnPacket extends Packet
{
    private int playerId;
    private Vec3 position;

    public RespawnPacket()
    {
        super(RESPAWN);
    }

    public RespawnPacket(int playerId)
    {
        super(RESPAWN);

        data.put(playerId);
        data.put(0.0f);
        data.put(0.0f);
        data.put(0.0f);

        data.flip();
    }

    public RespawnPacket(RespawnPacket packet)
    {
        super(RESPAWN);

        data.put(packet.playerId);
        data.put(packet.position.x);
        data.put(packet.position.y);
        data.put(packet.position.z);

        data.flip();
    }

    @Override
    public void read(DataBuffer buffer)
    {
        this.playerId = buffer.getInt();
        this.position = new Vec3(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
    }

    @Override
    public void process(NetworkableServer server, InetAddress address, int port)
    {
        ServerPlayer p = (ServerPlayer) server.getCore().getGame().getEntityManager().getEntities().get(playerId);
        p.setLife(100);
        p.setDead(false);

        int x = GameCore.getInstance().getGame().getData().getWorldSize() * 8;
        int y = GameCore.getInstance().getGame().getData().getWorldSize() * 8;
        int height = (int) GameCore.getInstance().getGame().getData().getWorldGen().getNoise(x, y) + 15;
        this.position = new Vec3(x, height, y);      // TODO : Modify position

        server.tcpSend(new RespawnPacket(this), p.getNetwork().getAddress(), p.getNetwork().getPort());
    }

    @Override
    public void process(NetworkableClient client, InetAddress address, int port)
    {
        ClientPlayer p = client.getCore().getGame().getPlayer();
        p.getRigidBody().getBody().killForces();
        p.setPosition(this.position);
        p.setLife(100);
        p.setDead(false);
    }
}