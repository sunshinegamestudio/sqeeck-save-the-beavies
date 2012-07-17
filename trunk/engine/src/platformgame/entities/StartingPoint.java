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
import com.jme3.bullet.collision.PhysicsCollisionObject;
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

import platformgame.controls.LevelExitControl;

/**
 *
 * @author Vortex
 */
public class StartingPoint extends Entity  {
static final Quaternion ROTATE_LEFT = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
    //camera
    private Camera cam;

    //private Spatial startingpoint_geo;
    private Node startingpoint_geo;
    
    private RigidBodyControl rigidBodyControl;
    private LevelExitControl levelExitControl;
    
    public StartingPoint(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, Camera cam) {
        super(assetManager, parent, physicsSpace);

        this.cam = cam;

        //startingpoint_geo = getParent().getChild("startingpoint_1-ogremesh");
        startingpoint_geo = (Node)getParent().getChild("startingpoint_1-ogremesh");
        if(startingpoint_geo != null)    {
            BoxCollisionShape shape = new BoxCollisionShape(new Vector3f(5.0f, 5.0f, 5.0f));
            rigidBodyControl = new RigidBodyControl(shape);
            rigidBodyControl.setMass(0);

            rigidBodyControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01);
            rigidBodyControl.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
            rigidBodyControl.removeCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_01);

            getPhysicsSpace().add(rigidBodyControl);
            startingpoint_geo.addControl(rigidBodyControl);
        
            //levelExitControl = new LevelExitControl(getPhysicsSpace());
            //startingpoint_geo.addControl(levelExitControl);

            getPhysicsSpace().addTickListener(levelExitControl);
            getPhysicsSpace().addCollisionListener(levelExitControl);
        }

    }

        /*
    public Node getNode()   {
        return spatial;
    }

    public CharacterControl getControl()   {
        return characterControl;
    }

    public void update(float tpf)    {
        
    }
         * 
         */
}
