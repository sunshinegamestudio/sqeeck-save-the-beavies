/*
CarGame, a game where you can drive with a car.
Copyright (C) 2010  Vortex GameStudio

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
 * This code is based on MonkeyZone -> svn/  trunk/ src/ com/ jme3/ monkeyzone/ controls/ ManualCharacterControl.java
 */

package platformgame.entities;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.BulletAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
//import com.jme3.effect.EmitterSphereShape;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import java.util.List;
import jme3tools.converters.ImageToAwt;

import platformgame.entities.CharacterPlayer;

/**
 *
 * @author Vortex
 */
public class SimpleEnemy extends Entity  {
    private CharacterPlayer player;
    private Node spatial;
    private CharacterControl characterControl;
    private Vector3f walkDirection = new Vector3f(Vector3f.ZERO);
    private Vector3f viewDirection = new Vector3f(Vector3f.UNIT_Z);
    private Vector3f tempVec = new Vector3f();


    public SimpleEnemy(CharacterPlayer player, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

        this.player = player;

        CapsuleCollisionShape capsule = new CapsuleCollisionShape(1.5f, 2f);
        characterControl = new CharacterControl(capsule, 0.01f);
        //model = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        spatial = (Node) assetManager.loadModel("Models/simple_enemy_1/simple_enemy_1.mesh.j3o");
        spatial.setLocalScale(0.5f);
        spatial.addControl(characterControl);
        characterControl.setPhysicsLocation(new Vector3f(10, 4, -14));
        //characterControl.attachDebugShape(assetManager);
        getParent().attachChild(spatial);
        getPhysicsSpace().add(characterControl);
    }

    public Node getNode()   {
        return spatial;
    }

    public void update(float tpf)    {
        walkDirection.set(Vector3f.ZERO);

        if(player.getControl().getPhysicsLocation().x > characterControl.getPhysicsLocation().x)  {
            walkDirection.x+=(5*tpf);
        }
        else    {
            walkDirection.x-=(5*tpf);
        }

        if(player.getControl().getPhysicsLocation().y > characterControl.getPhysicsLocation().y)  {
            walkDirection.y+=(5*tpf);
        }

        if(player.getControl().getPhysicsLocation().z > characterControl.getPhysicsLocation().z)  {
            walkDirection.z+=(5*tpf);
        }
        else    {
            walkDirection.z-=(5*tpf);
        }

        characterControl.setWalkDirection(walkDirection);

        //characterControl.setWalkDirection(player.getControl().getPhysicsLocation());
        //characterControl.setWalkDirection(player.getControl().getPhysicsLocation());

        //characterControl.setViewDirection(player.getNode().getWorldScale());
        //TODO: setting spatial rotation to avoid tilting
        //spatial.lookAt(player.getNode().getLocalTranslation(), Vector3f.UNIT_Y);

        System.out.println("WalkDirection: " + player.getControl().getPhysicsLocation());
        System.out.println("Char-WalkDirection: " + player.getControl().getWalkDirection());
    }
}
