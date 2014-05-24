/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package platformgame.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author Sunshine GameStudio
 */
public class PlayerCharacterControl extends BetterCharacterControl  {
    private PhysicsSpace physicsSpace;
    
    public PlayerCharacterControl(PhysicsSpace physicsSpace)    {
        this.physicsSpace = physicsSpace;

    }
}
