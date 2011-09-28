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

import platformgame.controls.CharacterPlayerControl;

/**
 *
 * @author Vortex
 */
public class CharacterPlayer extends Entity  {
static final Quaternion ROTATE_LEFT = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
    //camera
    private Camera cam;
    private boolean left = false, right = false, up = false, down = false, jump = false;
    private boolean run;
    private float runValue;

    //private Spatial spatial;
    private Node spatial;
    private CharacterControl characterControl;
    private CharacterPlayerControl characterPlayerControl;
    private RigidBodyControl rigidBodyControl;
    
    private Vector3f walkDirection = new Vector3f(Vector3f.ZERO);
    private Vector3f viewDirection = new Vector3f(Vector3f.UNIT_Z);
    private Vector3f directionLeft = new Vector3f(Vector3f.UNIT_X);
    private Quaternion directionQuat = new Quaternion();
    private Quaternion ROTATE_90 = new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
    private float rotAmountX = 0;
    private float rotAmountY = 0;
    private float walkAmount = 0;
    private float strafeAmount = 0;
    //private float speed = 10f * Globals.PHYSICS_FPS;
    private float speed = 0.001f;
    private Vector3f tempVec = new Vector3f();


    public CharacterPlayer(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, Camera cam) {
        super(assetManager, parent, physicsSpace);

        this.cam = cam;

        CapsuleCollisionShape capsule = new CapsuleCollisionShape(1.5f, 2f);
        characterControl = new CharacterControl(capsule, 0.01f);
        characterPlayerControl = new CharacterPlayerControl(getPhysicsSpace());
        //characterControl.setCollisionGroup(2);
        rigidBodyControl = new RigidBodyControl(capsule, 0.01f);
        rigidBodyControl.setKinematic(true);
        rigidBodyControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        rigidBodyControl.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        
        //model = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        spatial = (Node) assetManager.loadModel("Models/player_1/player_1.mesh.j3o");
        spatial.setLocalScale(0.5f);
        spatial.addControl(characterControl);
        spatial.addControl(characterPlayerControl);
        spatial.addControl(rigidBodyControl);
        characterControl.setPhysicsLocation(new Vector3f(10, 4, -10));
        //characterControl.attachDebugShape(assetManager);
        getParent().attachChild(spatial);
        getPhysicsSpace().add(characterControl);
        getPhysicsSpace().add(rigidBodyControl);

        // Move to CharacterPlayerControl !!!!!!!!!!!!!!!!!!!!
        getPhysicsSpace().addTickListener(characterPlayerControl);
        getPhysicsSpace().addCollisionListener(characterPlayerControl);
    }

    public Node getNode()   {
        return spatial;
    }

    public CharacterControl getControl()   {
        return characterControl;
    }

    public void update(float tpf)    {
        /*
        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
        System.out.println("Up: " + up);
        System.out.println("Down: " + down);
        System.out.println("Walkdirection: " + walkDirection);
         * 
         */

         /*
        if (up) {
            //walkDirection.add(0, 0, -1.0f);
            walkDirection.setZ(walkDirection.getZ()-0.001f);
        }
        else if(down)   {
            //walkDirection.add(0, 0, 1.0f);
            walkDirection.setZ(walkDirection.getZ()+0.001f);
        }
        else if(left)   {
            //walkDirection.add(1.0f, 0, 0);
            walkDirection.setX(walkDirection.getX()-0.001f);
        }
        else if(right)   {
            //walkDirection.add(-1.0f, 0, 0);
            walkDirection.setX(walkDirection.getX()+0.001f);
        }
        else if(jump)   {
            characterControl.jump();
        }

        characterControl.setWalkDirection(walkDirection);
          * 
          */

        walkAmount=0;
        rotAmountX=0;

        if (up) {
            //walkDirection.add(0, 0, -1.0f);
            //walkDirection.setZ(walkDirection.getZ()-0.001f);
            //walkAmount-=1;
            walkAmount-=100;
        }
        else if(down)   {
            //walkDirection.add(0, 0, 1.0f);
            //walkDirection.setZ(walkDirection.getZ()+0.001f);
            //walkAmount+=1;
            walkAmount+=100;
        }
        else if(left)   {
            //walkDirection.add(1.0f, 0, 0);
            //walkDirection.setX(walkDirection.getX()-0.001f);
            //rotAmountX+=0.01f;
            rotAmountX+=2.0f;
        }
        else if(right)   {
            //walkDirection.add(-1.0f, 0, 0);
            //walkDirection.setX(walkDirection.getX()+0.001f);
            //rotAmountX-=0.01f;
            rotAmountX-=2.0f;
        }
        else if(jump)   {
            //characterControl.jump();
        }

        //update if sync changed the directions
        if (!characterControl.getWalkDirection().equals(walkDirection) || !characterControl.getViewDirection().equals(viewDirection)) {
            walkDirection.set(characterControl.getWalkDirection());
            viewDirection.set(characterControl.getViewDirection()).normalizeLocal();
            directionLeft.set(viewDirection).normalizeLocal();
            ROTATE_90.multLocal(directionLeft);
        }

        walkDirection.set(viewDirection).multLocal(speed * walkAmount);
        walkDirection.addLocal(directionLeft.mult(speed * strafeAmount));

        if (rotAmountX != 0) {
            //rotate all vectors around the rotation amount
            directionQuat.fromAngleAxis((FastMath.PI) * tpf * rotAmountX, Vector3f.UNIT_Y);
            directionQuat.multLocal(walkDirection);
            directionQuat.multLocal(viewDirection);
            directionQuat.multLocal(directionLeft);
        }
        if (rotAmountY != 0) {
            directionQuat.fromAngleAxis((FastMath.PI) * tpf * rotAmountY, directionLeft);
            directionQuat.multLocal(viewDirection);
            if (viewDirection.getY() > 0.3f || viewDirection.getY() < -0.3f) {
                //rotate all vectors around the rotation amount
                directionQuat.fromAngleAxis((FastMath.PI) * tpf * -rotAmountY, directionLeft);
                directionQuat.multLocal(viewDirection);
            }
        }
        characterControl.setWalkDirection(walkDirection);
        characterControl.setViewDirection(viewDirection);
        //TODO: setting spatial rotation to avoid tilting
        spatial.getLocalRotation().lookAt(tempVec.set(viewDirection).multLocal(1, 0, 1), Vector3f.UNIT_Y);
        spatial.setLocalRotation(spatial.getLocalRotation());
    }

    public void up(boolean up) {
        this.up = up;
    }
    
    public void down(boolean down) {
        this.down = down;
    }
    
    public void left(boolean left) {
        this.left = left;
    }

    public void right(boolean right) {
        this.right = right;
    }

    public void jump(boolean jump) {
        this.jump = jump;
    }

    public void resetControls() {
        up = false;
        down = false;
        left = false;
        right = false;
        jump = false;
    }
}
