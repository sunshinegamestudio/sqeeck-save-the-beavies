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
import com.jme3.scene.Spatial;
import platformgame.core.PlatformGame;

/**
 *
 * @author Sunshine GameStudio
 */
public class EnemyCharacterControl extends MyBetterCharacterControl  {
    private PhysicsSpace physicsSpace;
    private Spatial player;
    private Spatial enemyPlayer;
    
    public EnemyCharacterControl(PhysicsSpace physicsSpace)    {
        super(physicsSpace);
        this.physicsSpace = physicsSpace;

    }

    public void controlInit()   {
        enemyPlayer = spatial;
        try {
            // player = PlatformGame.getApp().getPlayer();
        }
        catch(Exception e)  {
            
        }
    }
    
    public void controlUpdate(float tpf) {
        walkDirection.set(Vector3f.ZERO);

        // if(player.getControl().getPhysicsLocation().x > characterControl.getPhysicsLocation().x)  {
        if(player.getControl(PlayerCharacterControl.class).getLocation().x > enemyPlayer.getControl(EnemyCharacterControl.class).getLocation().x)  {
            walkDirection.x+=(5*tpf);
        }
        else    {
            walkDirection.x-=(5*tpf);
        }

        // if(player.getControl().getPhysicsLocation().y > characterControl.getPhysicsLocation().y)  {
        if(player.getControl(PlayerCharacterControl.class).getLocation().y > enemyPlayer.getControl(EnemyCharacterControl.class).getLocation().y)  {
            walkDirection.y+=(5*tpf);
        }

        // if(player.getControl().getPhysicsLocation().z > characterControl.getPhysicsLocation().z)  {
        if(player.getControl(PlayerCharacterControl.class).getLocation().z > enemyPlayer.getControl(EnemyCharacterControl.class).getLocation().z)  {
            walkDirection.z+=(5*tpf);
        }
        else    {
            walkDirection.z-=(5*tpf);
        }

        // characterControl.setWalkDirection(walkDirection);
        setWalkDirection(walkDirection);
    }
}
