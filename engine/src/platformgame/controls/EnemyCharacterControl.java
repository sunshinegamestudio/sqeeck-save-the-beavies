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
public class EnemyCharacterControl extends BetterCharacterControl  {
    private PhysicsSpace physicsSpace;
    
    public EnemyCharacterControl(PhysicsSpace physicsSpace)    {
        this.physicsSpace = physicsSpace;

    }

    public void controlUpdate() {
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
    }
}
