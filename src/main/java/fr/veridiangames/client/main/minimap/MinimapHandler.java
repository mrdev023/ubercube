package fr.veridiangames.client.main.minimap;

import fr.veridiangames.client.Ubercube;
import fr.veridiangames.client.rendering.textures.Texture;
import fr.veridiangames.client.rendering.textures.TextureLoader;
import fr.veridiangames.core.GameCore;
import fr.veridiangames.core.game.entities.player.ClientPlayer;
import fr.veridiangames.core.game.entities.player.NetworkedPlayer;
import fr.veridiangames.core.game.entities.player.Player;
import fr.veridiangames.core.game.gamemodes.Team;
import fr.veridiangames.core.maths.Vec2;
import fr.veridiangames.core.maths.Vec2i;
import fr.veridiangames.core.maths.Vec3;
import fr.veridiangames.core.utils.Color4f;

import java.util.ArrayList;
import java.util.List;

import static fr.veridiangames.client.Resource.getResource;
import static fr.veridiangames.core.maths.Mathf.atan2;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

public class MinimapHandler
{
	public static final Texture SPAWN_ICON = TextureLoader.loadTexture(getResource("textures/house_icon.png"), GL_LINEAR, false);
	public static final Texture PLAYER_ICON = TextureLoader.loadTexture(getResource("textures/player_icon.png"), GL_LINEAR, false);

	public static final Texture NORTH_ICON = TextureLoader.loadTexture(getResource("textures/North.png"), GL_LINEAR, false);
	public static final Texture SOUTH_ICON = TextureLoader.loadTexture(getResource("textures/South.png"), GL_LINEAR, false);
	public static final Texture EAST_ICON = TextureLoader.loadTexture(getResource("textures/East.png"), GL_LINEAR, false);
	public static final Texture WEST_ICON = TextureLoader.loadTexture(getResource("textures/West.png"), GL_LINEAR, false);

	private Vec2i pos;
	private Vec2i size;
	private float scale;

	private List<MinimapObject> minimapObjects;
	private List<MinimapObject> dynamicObjects;

	public MinimapHandler(GameCore core)
	{
		this.minimapObjects = new ArrayList<>();
		this.dynamicObjects = new ArrayList<>();
		this.pos = new Vec2i(35, 30);
		this.size = new Vec2i(300, 200);
		this.scale = 4;

		add(new MinimapObject(this, NORTH_ICON, new Vec3(0, 0, 60), Color4f.WHITE.copy(), MinimapObject.MinimapObjectType.RELATIVE));
		add(new MinimapObject(this, SOUTH_ICON, new Vec3(0, 0, -60), Color4f.WHITE.copy(), MinimapObject.MinimapObjectType.RELATIVE));
		add(new MinimapObject(this, EAST_ICON, new Vec3(60, 0, 0), Color4f.WHITE.copy(), MinimapObject.MinimapObjectType.RELATIVE));
		add(new MinimapObject(this, WEST_ICON, new Vec3(-60, 0, 0), Color4f.WHITE.copy(), MinimapObject.MinimapObjectType.RELATIVE));

		for (Team team : core.getGame().getGameMode().getTeams())
		{
			add(new MinimapObject(this, SPAWN_ICON, team.getSpawn(), team.getColor(), MinimapObject.MinimapObjectType.STATIC));
		}
	}

	public void update()
	{
		clearDynamics();
		Team playerTeam = GameCore.getInstance().getGame().getPlayer().getTeam();
		if (playerTeam != null && playerTeam.getPlayers() != null) {
			for (int i = 0; i < playerTeam.getPlayers().getSize(); i++) {
				int pid = playerTeam.getPlayers().getList().get(i);
				Player player = (Player) GameCore.getInstance().getGame().getEntityManager().get(pid);
				if (player == null)
					continue;
				add(new MinimapObject(this, PLAYER_ICON, player.getPosition(), playerTeam.getColor(), MinimapObject.MinimapObjectType.DYNAMIC));
			}
		}

		Vec3 p = GameCore.getInstance().getGame().getPlayer().getPosition();
		Vec2 dir = Ubercube.getInstance().getGameCore().getGame().getPlayer().getRotation().getForward().xz().normalize();
		float yRot = atan2(dir.y, dir.x);
		for (int i = 0; i < minimapObjects.size(); i++)
		{
			MinimapObject obj = minimapObjects.get(i);
			obj.update(p, yRot);
		}
	}

	public void add(MinimapObject staticObject)
	{
		this.minimapObjects.add(staticObject);
		if (staticObject.getType() == MinimapObject.MinimapObjectType.DYNAMIC)
		{
			this.dynamicObjects.add(staticObject);
		}
	}

	public void clearDynamics()
	{
		for (int i = 0; i < dynamicObjects.size(); i++)
		{
			minimapObjects.remove(dynamicObjects.get(i));
		}
		dynamicObjects.clear();
	}

	public List<MinimapObject> getMinimapObjects()
	{
		return minimapObjects;
	}

	public List<MinimapObject> getMinimapDynamicObjects()
	{
		return dynamicObjects;
	}

	public Vec2i getPos() {
		return pos;
	}

	public void setPos(Vec2i pos) {
		this.pos = pos;
	}

	public Vec2i getSize() {
		return size;
	}

	public void setSize(Vec2i size) {
		this.size = size;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}